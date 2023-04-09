package de.davelee.trams.operations.service;

import de.davelee.trams.operations.model.OperatingDays;
import de.davelee.trams.operations.model.StopTime;
import de.davelee.trams.operations.repository.StopTimeRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * This class tests the StopTimeService class and ensures that it works successfully. Mocks are used for the database layer.
 * @author Dave Lee
 */
@SpringBootTest
public class StopTimeServiceTest {

    @InjectMocks
    private StopTimeService stopTimeService;

    @Mock
    private StopTimeRepository stopTimeRepository;

    /**
     * Verify that stop times can be retrieved from the database correctly.
     */
    @Test
    public void testService ( ) {
        //Test data
        Mockito.when(stopTimeRepository.findByCompanyAndStopName("Mustermann Bus GmbH", "Lakeside")).thenReturn(Lists.newArrayList(
                createStopTime(LocalTime.of(16,11), LocalTime.of(16,12), "101", 1),
                createStopTime(LocalTime.of(16,41), LocalTime.of(16,42), "102", 2),
                createStopTime(LocalTime.of(17,21), LocalTime.of(17,22), "103", 3),
                createStopTime(LocalTime.of(20,21), LocalTime.of(20,22), "104", 4),
                createStopTime(LocalTime.of(21,16), LocalTime.of(21,17), "105", 5),
                createStopTime(LocalTime.of(22,11), LocalTime.of(22,12), "106", 6),
                createStopTime(LocalTime.of(23,21), LocalTime.of(23,22), "107", 7),
                createStopTime(LocalTime.of(0,21), LocalTime.of(0,22), "108", 8)
                ));
        //Test case 1: 3 Departures in the next 2 hours before 22:00
        List<StopTime> stopTimeTestList1 = stopTimeService.getDepartures("Lakeside", "Mustermann Bus GmbH", "16:00");
        assertEquals(3, stopTimeTestList1.size());
        assertEquals(1, stopTimeTestList1.get(0).getId());
        assertEquals(2, stopTimeTestList1.get(1).getId());
        assertEquals(3, stopTimeTestList1.get(2).getId());
        //Test case 2: 1 Departure before 22:00 and 1 after
        List<StopTime> stopTimeTestList2 = stopTimeService.getDepartures("Lakeside", "Mustermann Bus GmbH", "21:00");
        assertEquals(2, stopTimeTestList2.size());
        assertEquals(5, stopTimeTestList2.get(0).getId());
        assertEquals(6, stopTimeTestList2.get(1).getId());
        //Test case 3: 2 Departures before 22:00 and 0 after
        List<StopTime> stopTimeTestList3 = stopTimeService.getDepartures("Lakeside", "Mustermann Bus GmbH", "20:05");
        assertEquals(2, stopTimeTestList3.size());
        assertEquals(4, stopTimeTestList3.get(0).getId());
        assertEquals(5, stopTimeTestList3.get(1).getId());
        //Test caae 4: 2 Departures between 23:00 and 01:00 on separate days.
        List<StopTime> stopTimeTestList4 = stopTimeService.getDepartures("Lakeside", "Mustermann Bus GmbH","23:00");
        assertEquals(2, stopTimeTestList4.size());
        assertEquals(7, stopTimeTestList4.get(0).getId());
        assertEquals(8, stopTimeTestList4.get(1).getId());
        //Test case 5: 3 Arrivals before 22:00
        List<StopTime> stopTimeTestArrivalList1 = stopTimeService.getArrivals("Lakeside", "Mustermann Bus GmbH", "16:00");
        assertEquals(3, stopTimeTestArrivalList1.size());
        assertEquals(1, stopTimeTestArrivalList1.get(0).getId());
        assertEquals(2, stopTimeTestArrivalList1.get(1).getId());
        assertEquals(3, stopTimeTestArrivalList1.get(2).getId());
        //Test case 6: 1 Arrivals before 22:00 and 1 after
        List<StopTime> stopTimeTestArrivalList2 = stopTimeService.getArrivals("Lakeside", "Mustermann Bus GmbH", "21:00");
        assertEquals(2, stopTimeTestArrivalList2.size());
        assertEquals(5, stopTimeTestArrivalList2.get(0).getId());
        assertEquals(6, stopTimeTestArrivalList2.get(1).getId());
        //Test case 7: 2 Arrivals before 22:00 and 0 after
        List<StopTime> stopTimeTestArrivalList3 = stopTimeService.getArrivals("Lakeside", "Mustermann Bus GmbH", "20:05");
        assertEquals(2, stopTimeTestArrivalList3.size());
        assertEquals(4, stopTimeTestArrivalList3.get(0).getId());
        assertEquals(5, stopTimeTestArrivalList3.get(1).getId());
        //Test caae 8: 2 Arrivals between 23:00 and 01:00 on separate days.
        List<StopTime> stopTimeTestArrivalList4 = stopTimeService.getArrivals("Lakeside", "Mustermann Bus GmbH", "23:00");
        assertEquals(2, stopTimeTestArrivalList4.size());
        assertEquals(7, stopTimeTestArrivalList4.get(0).getId());
        assertEquals(8, stopTimeTestArrivalList4.get(1).getId());
        //Test case: test all departures for this date.
        List<StopTime> stopTimeDepartureDateList = stopTimeService.getDeparturesByDate("Lakeside", "Mustermann Bus GmbH", "2021-04-10");
        assertEquals(8, stopTimeDepartureDateList.size());
        assertEquals(8, stopTimeDepartureDateList.get(0).getId());
        assertEquals(7, stopTimeDepartureDateList.get(stopTimeDepartureDateList.size()-1).getId());
    }

    /**
     * Verify that the logic of 3 departures after 22:00 works successfully.
     */
    @Test
    public void testStopTimesAfter2200 ( ) {
        //Test data
        Mockito.when(stopTimeRepository.findByCompanyAndStopName("Mustermann Bus GmbH", "Lakeside")).thenReturn(Lists.newArrayList(
                createStopTime(LocalTime.of(22,11), LocalTime.of(22,12), "106", 1),
                createStopTime(LocalTime.of(23,21), LocalTime.of(23,22), "107", 2),
                createStopTime(LocalTime.of(23,58), LocalTime.of(23,59), "108", 3)
        ));
        //Test case 1: 3 Departures in the next 2 hours after 22:00
        List<StopTime> stopTimeTestList1 = stopTimeService.getDepartures("Lakeside", "Mustermann Bus GmbH", "22:01");
        assertEquals(3, stopTimeTestList1.size());
        assertEquals(1, stopTimeTestList1.get(0).getId());
        assertEquals(2, stopTimeTestList1.get(1).getId());
        assertEquals(3, stopTimeTestList1.get(2).getId());
    }

    /**
     * Verify that the logic of 3 departures after 22:00 works successfully.
     */
    @Test
    public void testStopTimesAfterDuplicate ( ) {
        //Test data
        Mockito.when(stopTimeRepository.findByCompanyAndStopName("Mustermann Bus GmbH", "Lakeside")).thenReturn(Lists.newArrayList(
                createStopTime(LocalTime.of(10,21), LocalTime.of(10,22), "107", 2),
                createStopTime(LocalTime.of(10,21), LocalTime.of(10,22), "107", 2),
                createStopTime(LocalTime.of(10,58), LocalTime.of(10,59), "108", 3)

        ));
        //Test case 1: Duplicate departures to be removed
        List<StopTime> stopTimeTestList1 = stopTimeService.getDepartures("Lakeside", "Mustermann Bus GmbH","10:01");
        assertEquals(2, stopTimeTestList1.size());
        assertEquals(2, stopTimeTestList1.get(0).getId());
        assertEquals(3, stopTimeTestList1.get(1).getId());
    }

    /**
     * Verify that disrupted services are not returned.
     */
    @Test
    public void testDisruptedStopTimes ( ) {
        StopTime stopTime1 = createStopTime(LocalTime.of(10,21), LocalTime.of(10,22), "111", 1);
        stopTime1.getOperatingDays().setDisruptedOperatingDays(Lists.newArrayList(LocalDate.of(2021,03,12)));
        assertTrue(stopTime1.getOperatingDays().checkIfOperatingDay(LocalDate.of(2021,03,01)));
        assertFalse(stopTime1.getOperatingDays().checkIfOperatingDay(LocalDate.of(2021,03,12)));
    }

    /**
     * Verify that it is possible to add stop times.
     */
    @Test
    public void testAddStopTimes ( ) {
        //Test data.
        List<StopTime> stopTimeList = List.of(createStopTime(LocalTime.of(8,15), LocalTime.of(8,17), "1", 1));
        //Run actual test - in positive case.
        Mockito.when(stopTimeRepository.save(any())).thenReturn(createStopTime(LocalTime.of(8,15), LocalTime.of(8,17), "1", 1));
        assertTrue(stopTimeService.addStopTimes(stopTimeList));
        //Run actual test - in negative case.
        Mockito.when(stopTimeRepository.save(any())).thenReturn(null);
        assertFalse(stopTimeService.addStopTimes(stopTimeList));
    }

    /**
     * Verify that counting stop times works successfully.
     */
    @Test
    public void testCountStopTimes ( ) {
        Mockito.when(stopTimeRepository.countByCompanyAndStopNameAndRouteNumber("Mustermann GmbH", "City Centre", "1A")).thenReturn(1L);
        assertEquals(1, stopTimeService.countStopTimes("Mustermann GmbH", "City Centre", "1A"));
    }

    /**
     * Private helper method to create test stop time data.
     * @param arrivalTime a <code>LocalTime</code> object containing the desired arrival time.
     * @param departureTime a <code>LocalTime</code> object containing the desired departure time.
     * @param journeyNumber a <code>String</code> containing the journey name.
     * @param count a <code>int</code> with the id to use.
     * @return a <code>StopTime</code> object which contains all data filled for a test StopTimeModel object.
     */
    private StopTime createStopTime (final LocalTime arrivalTime, final LocalTime departureTime, final String journeyNumber, final int count ) {
        return StopTime.builder()
                .arrivalTime(arrivalTime)
                .departureTime(departureTime)
                .destination("Greenfield")
                .id(count)
                .journeyNumber(journeyNumber)
                .operatingDays(OperatingDays.builder()
                        .operatingDays(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))
                        .specialOperatingDays(Arrays.asList(LocalDate.of(2020,12,25)))
                        .build())
                .routeNumber("405A")
                .stopName("Lakeside")
                .validFromDate(LocalDate.of(2020,12,12))
                .validToDate(LocalDate.of(2021,12,11))
                .build();
    }
}
