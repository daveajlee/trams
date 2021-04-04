import {
  HttpEvent,
  HttpEventType,
  HttpProgressEvent,
  HttpResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { distinctUntilChanged, scan } from 'rxjs/operators';

/**
 * This function checks if the supplied event is a response event and returns true iff this is a response event.
 * @param event the event which should be checked.
 */
function isHttpResponse<T>(event: HttpEvent<T>): event is HttpResponse<T> {
  return event.type === HttpEventType.Response;
}

/**
 * This function checks if the supplied event is a progress event and returns true iff this is a progress event.
 * @param event the event which should be checked.
 */
function isHttpProgressEvent(
  event: HttpEvent<unknown>
): event is HttpProgressEvent {
  return (
    event.type === HttpEventType.DownloadProgress ||
    event.type === HttpEventType.UploadProgress
  );
}

/**
 * A simple interface to provide the status of the upload.
 */
export interface Upload {
  progress: number;
  state: 'PENDING' | 'IN_PROGRESS' | 'DONE';
}

/**
 * Measure the actual progress of the upload.
 */
export function upload(): (
  source: Observable<HttpEvent<unknown>>
) => Observable<Upload> {
  const initialState: Upload = { state: 'PENDING', progress: 0 };
  const reduceState = (upload: Upload, event: HttpEvent<unknown>): Upload => {
    if (isHttpProgressEvent(event)) {
      return {
        progress: event.total
          ? Math.round((100 * event.loaded) / event.total)
          : upload.progress,
        state: 'IN_PROGRESS',
      };
    }
    if (isHttpResponse(event)) {
      return {
        progress: 100,
        state: 'DONE',
      };
    }
    return upload;
  };
  return (source) =>
    source.pipe(
      scan(reduceState, initialState),
      distinctUntilChanged(
        (a, b) => a.state === b.state && a.progress === b.progress
      )
    );
}
