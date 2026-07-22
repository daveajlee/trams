package de.davelee.trams.server.request;

import org.springframework.web.multipart.MultipartFile;

/**
 * This class represents a request to import data from a zip file into TraMS.
 * The data can either be in the GTFS or CSV format and must contain the format in the request.
 * Optionally a list of routes, valid from and valid to dates can be provided.
 */
public class ImportZipRequest {

    private MultipartFile zipFile;

    private String routesToImport;

    private String fileFormat;

    private String validFromDate;

    private String validToDate;

    public MultipartFile getZipFile() {
        return zipFile;
    }

    public void setZipFile(MultipartFile zipFile) {
        this.zipFile = zipFile;
    }

    public String getRoutesToImport() {
        return routesToImport;
    }

    public void setRoutesToImport(String routesToImport) {
        this.routesToImport = routesToImport;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(String validFromDate) {
        this.validFromDate = validFromDate;
    }

    public String getValidToDate() {
        return validToDate;
    }

    public void setValidToDate(String validToDate) {
        this.validToDate = validToDate;
    }
}
