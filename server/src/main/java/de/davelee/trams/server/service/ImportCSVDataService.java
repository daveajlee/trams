package de.davelee.trams.server.service;

import de.davelee.trams.server.constant.OperatingDaysAbbreviations;
import de.davelee.trams.server.model.OperatingDays;
import de.davelee.trams.server.model.Route;
import de.davelee.trams.server.model.Stop;
import de.davelee.trams.server.model.StopTime;
import de.davelee.trams.server.repository.RouteRepository;
import de.davelee.trams.server.repository.StopRepository;
import de.davelee.trams.server.repository.StopTimeRepository;
import de.davelee.trams.server.utils.DateUtils;
import de.davelee.trams.server.utils.RouteUtils;
import de.davelee.trams.server.utils.StopUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.util.*;

/**
 * This class provides a service for importing CSV files which match the following specification:
 * @author Dave Lee
 */
@Service
public class ImportCSVDataService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private StopTimeRepository stopTimeRepository;

    private int stopTimeCounter;

    /**
     * Read a csv folder and extract all departures from the supplied file and store them in the mongo db.
     * @param directory a <code>String</code> containing the directory to load the data from.
     * @param validFromDate a <code>String</code> containing the valid from date in format yyyy-MM-dd
     * @param validToDate a <code>String</code> containing the valid to date in format yyyy-MM-dd
     * @return a <code>boolean</code> which is true iff the file could be read successfully.
     */
    public boolean readCSVFile(final String directory, final String validFromDate, final String validToDate) {
        //Set counter for stopTime objects to ensure a valid identifier.
        stopTimeCounter = 0;

        //Check that the directory exists - otherwise return false.
        if ( !directory.startsWith("/") && ImportCSVDataService.class.getClassLoader().getResource(directory) == null ) {
            return false;
        }

        //Get all csv files in the directory.
        final File directoryPath = directory.startsWith("/") ? new File(directory) :
                                        new File(ImportCSVDataService.class.getClassLoader().getResource(directory).getFile());
        File[] directoryFiles = directoryPath.listFiles((dir, name) -> name.endsWith(".csv"));

        //If there are no csv files in the directory then return false.
        if ( directoryFiles == null || directoryFiles.length == 0 ) {
            return false;
        }

        //Determine operator name based on name of zip file.
        String[] directorySplitPath = directory.split(FileSystems.getDefault().getSeparator());
        String operatorName = directorySplitPath[directorySplitPath.length-1].replace("-", " ");
        operatorName = WordUtils.capitalizeFully(operatorName);

        //Loop through all of the files and begin to process them in helper methods.
        for ( File csvFile : directoryFiles ) {
            if (!loadCSVFile(csvFile.getAbsolutePath(), operatorName, validFromDate, validToDate)) {
                return false;
            }
        }

        //If all csv files could be processed successfully, then return true.
        return true;

    }

    /**
     * This method attempts to load the supplied csv file, read all data and upload it to the database. If it is
     * successful and data goes into the database, then it returns true. Otherwise it returns false.
     * @param csvFilePath a <code>String</code> containing the path to the csv file to load.
     * @param operatorName a <code>String</code> containing the name of the operator.
     * @param validFromDate a <code>String</code> containing the valid from date in format yyyy-MM-dd
     * @param validToDate a <code>String</code> containing the valid to date in format yyyy-MM-dd
     * @return a <code>boolean</code> which is true iff the csv file could be read and processed successfully.
     */
    private boolean loadCSVFile ( final String csvFilePath, final String operatorName, final String validFromDate,
                                  final String validToDate ) {
        try {
            Reader reader = new FileReader(csvFilePath);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';').withTrim());
            String destination = ""; ArrayList<OperatingDays> operatingDays = new ArrayList<>();
            LocalDateTime validFromLocalDate = DateUtils.convertBackwardDateToLocalDateTime(validFromDate);
            LocalDateTime validToLocalDate = DateUtils.convertBackwardDateToLocalDateTime(validToDate);
            ArrayList<String> routeNumberList = new ArrayList<>();
            Map<String, String> footnotes = new HashMap<>();
            for(CSVRecord record : csvParser.getRecords()) {
                if ( record.get(0).startsWith("Route:") ) {
                    for ( int i = 1; i < record.size(); i++ ) {
                        if ( record.get(i).isEmpty() ) continue; //Do not add empty data.
                        importRoute(record.get(i), operatorName);
                        routeNumberList.add(record.get(i));
                    }
                }
                else if ( record.get(0).contains(" <> ")) {
                    destination = record.get(0).split(" <> ")[1];
                }
                else if ( record.get(0).contains("Days of Operation")) {
                    for ( int i = 1; i < record.size(); i++ ) {
                        operatingDays.add(getOperatingDays(record.get(i), validFromLocalDate, validToLocalDate));
                    }
                }
                else if ( record.get(0).isEmpty() || record.get(0).startsWith("Circulation:")) {
                    continue;
                }
                else if ( record.get(0).contains(" = ") ) {
                    String[] footnoteSplit = record.get(0).split("=");
                    footnotes.put(footnoteSplit[0].trim(), footnoteSplit[1].trim());
                }
                else {
                    if (!StopUtils.hasStopAlreadyBeenImported(record.get(0), operatorName, stopRepository)) {
                        importStop(record.get(0), operatorName);
                    }
                    for ( int i = 1; i < record.size(); i++ ) {
                        if ( record.get(i).isEmpty() ) continue;
                        StopTime stopTime = null;
                        if ( record.get(i).length() > 5 ) {
                            stopTime = StopTime.builder()
                                    .company(operatorName)
                                    .departureTime(DateUtils.convertTimeToLocalTime(record.get(i).substring(0,5)))
                                    .arrivalTime(DateUtils.convertTimeToLocalTime(record.get(i).substring(0,5)))
                                    .stopName(record.get(0))
                                    .destination(destination)
                                    .routeNumber(routeNumberList.get(i - 1))
                                    .validFromDate(validFromLocalDate)
                                    .validToDate(validToLocalDate)
                                    .operatingDays(operatingDays.get(i - 1))
                                    .journeyNumber("" + i)
                                    .footnote(footnotes.get(record.get(i).substring(6)))
                                    .build();
                        } else {
                            stopTime = StopTime.builder()
                                    .company(operatorName)
                                    .departureTime(DateUtils.convertTimeToLocalTime(record.get(i)))
                                    .arrivalTime(DateUtils.convertTimeToLocalTime(record.get(i)))
                                    .stopName(record.get(0))
                                    .destination(destination)
                                    .routeNumber(routeNumberList.get(i - 1))
                                    .validFromDate(validFromLocalDate)
                                    .validToDate(validToLocalDate)
                                    .operatingDays(operatingDays.get(i - 1))
                                    .journeyNumber("" + i)
                                    .build();
                        }
                        if ( stopTime != null ) {
                            stopTimeRepository.insert(stopTime);
                            stopTimeCounter++;
                        }
                    }
                }
            }
            return true;
        } catch ( IOException exception ) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * This is a private helper method to import the supplied route to the database.
     * @param routeNumber a <code>String</code> object with the route numnber that should be imported.
     * @param operatorName a <code>String</code> object which contains the name of the operator of this route.
     */
    private void importRoute (final String routeNumber, final String operatorName ) {
        if ( !RouteUtils.hasRouteAlreadyBeenImported(routeNumber, operatorName, routeRepository) ) {
            Route route = Route.builder()
                    .routeNumber(routeNumber)
                    .id(UUID.randomUUID().toString())
                    .company(operatorName)
                    .build();
            routeRepository.insert(route);
        }
    }

    /**
     * This is a private helper method to import the supplied stop to the database.
     * @param stopName a <code>String</code> object containing the name of the stop to add.
     * @param company a <code>String</code> object containing the name of the company serving the stop.
     */
    private void importStop ( final String stopName, final String company ) {
        Stop stop = Stop.builder()
                .id(UUID.randomUUID().toString())
                .name(stopName)
                .company(company)
                .build();
        stopRepository.insert(stop);
    }

    /**
     * This helper method converts the operating days of a service into a list of operating days.
     * @param operatingDaysStr a <code>String</code> object containing the comma-separated list of operating days.
     * @param validFromDate a <code>LocalDateTime</code> object containing the valid from date for this service.
     * @param validToDate a <code>LocalDateTime</code> object containing the valid to date for this service.
     * @return a <code>OperatingDays</code> object containing the operating days of this service.
     */
    private OperatingDays getOperatingDays (final String operatingDaysStr, final LocalDateTime validFromDate,
                                            final LocalDateTime validToDate) {
        //Create empty list
        OperatingDays operatingDays = new OperatingDays();
        //Split comma separated list
        String[] operatingDaysCommaList = operatingDaysStr.split(",");
        //Go through comma separated list
        for ( String operatingDay : operatingDaysCommaList ) {
            if (StringUtils.isBlank(operatingDay) ) continue;
            OperatingDaysAbbreviations operatingDaysAbbreviations = OperatingDaysAbbreviations.valueOf(operatingDay);
            operatingDays.setOperatingDays(operatingDaysAbbreviations.getOperatingDaysOfWeek());
            operatingDays.setSpecialOperatingDays(operatingDaysAbbreviations.getSpecialOperatingDays(validFromDate, validToDate));
        }
        //Return complete list of operating days.
        return operatingDays;
    }



}
