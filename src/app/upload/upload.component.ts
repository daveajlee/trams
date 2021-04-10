import {Component, OnDestroy, OnInit} from '@angular/core';
import {UploadService} from './upload.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
/**
 * This class implements the upload functionality where files can be uploaded to the server for further processing.
 */
export class UploadComponent implements OnInit, OnDestroy {

  file: File | null = null;

  // upload$: Observable<Upload> = EMPTY;

  /**
   * Create a new upload component which manages the upload process of the file to the server.
   * @param uploadService which enables the uploading of a file to the server.
   */
  constructor(private uploadService: UploadService) { }

  onFileInput(files: FileList | null): void {
    if (files) {
      this.file = files.item(0);
    }
  }

  /**
   * On submission of the form, we start the upload process.
   * @param routesToImport a text which contains the route numbers to import which can be empty if all routes should be imported.
   * @param fileFormat the format of the file that has been sent - either gtfs or csv.
   * @param validFromDate the date as a string from which the data is valid
   * @param validToDate the date as a string until when the data is valid
   */
  onSubmit(routesToImport: string, fileFormat: string, validFromDate: string, validToDate: string): void {
    if (this.file) {
      this.uploadService.upload(this.file, routesToImport, fileFormat, validFromDate, validToDate);
    }
  }

  /**
   * This method enables or disables the valid from and valid to date fields if the file format changes.
   * @param fileFormat the format of the file which is currently selected.
   */
  onFileFormatChange( fileFormat: string ): void {
    (document.getElementById('routesImport') as HTMLInputElement).disabled = ( fileFormat === 'Comma Separated Value (CSV)');
    (document.getElementById('validFromDate') as HTMLInputElement).disabled = ( fileFormat === 'General Transit Feed Specification (GTFS)');
    (document.getElementById('validToDate') as HTMLInputElement).disabled = ( fileFormat === 'General Transit Feed Specification (GTFS)');
  }

  /**
   * Commands to do when the component is initialised which is currently nothing.
   */
  ngOnInit(): void {
  }

  /**
   * Commands to do when the component is destroyed which is currently nothing.
   */
  ngOnDestroy(): void {
  }

}
