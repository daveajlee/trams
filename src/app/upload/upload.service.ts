import {HttpClient} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Upload, upload} from './upload';

@Injectable({ providedIn: 'root' })
/**
 * This class provides the html connection for uploading the file to the server.
 */
export class UploadService {
  constructor(private http: HttpClient) {}

  /**
   * Upload the specified file with the route numbers that should be imported to the server.
   * @param file the file that should be sent to the server
   * @param routesToImport the list of route numbers to import which can be empty if all routes should be imported
   */
  upload(file: File, routesToImport: string): Observable<Upload> {
    const data = new FormData();
    data.append('zipFile', file);
    data.append('routesToImport', routesToImport);
    return this.http
      .post('http://localhost:8080/trams-operations/uploadGTFSFile', data, {
        reportProgress: true,
        observe: 'events',
      })
      .pipe(upload());
  }

}
