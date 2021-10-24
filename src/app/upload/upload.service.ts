import {HttpClient} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Router} from '@angular/router';

@Injectable({ providedIn: 'root' })
/**
 * This class provides the html connection for uploading the file to the server.
 */
export class UploadService {
  constructor(public router: Router, private http: HttpClient) {}

  /**
   * Upload the specified file with the route numbers that should be imported to the server.
   * @param file the file that should be sent to the server
   * @param routesToImport the list of route numbers to import which can be empty if all routes should be imported
   * @param fileFormat the format of the file that has been sent - either gtfs or csv.
   * @param validFromDate the date as a string from which the data is valid
   * @param validToDate the date as a string until when the data is valid
   */
  upload(file: File, routesToImport: string, fileFormat: string, validFromDate: string, validToDate: string): void {
    const data = new FormData();
    data.append('zipFile', file);
    data.append('routesToImport', routesToImport);
    data.append('fileFormat', fileFormat);
    data.append('validFromDate', validFromDate);
    data.append('validToDate', validToDate);
    this.http
      .post('http://localhost:8084/trams-operations/upload/', data)
        .subscribe(
            () => {
              this.router.navigate(['routes']);
            });
  }

}
