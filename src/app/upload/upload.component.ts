import {Component, OnDestroy, OnInit} from '@angular/core';
import {EMPTY, Observable} from 'rxjs';
import {UploadService} from './upload.service';
import {Upload} from './upload';

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

  upload$: Observable<Upload> = EMPTY;

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
   */
  onSubmit(routesToImport: string): void {
    if (this.file) {
      this.upload$ = this.uploadService.upload(this.file, routesToImport);
    }
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
