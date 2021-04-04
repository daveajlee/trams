import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RoutesComponent} from './routes/routes.component';
import {StopsComponent} from './stops/stops.component';
import {StopDetailComponent} from './stops/stop-detail/stop-detail.component';
import {UploadComponent} from './upload/upload.component';

/**
 * Define the links which work in this application.
 */
const appRoutes: Routes = [
  { path: 'routes', component: RoutesComponent },
  { path: 'stops', component: StopsComponent, children: [
      { path: ':id', component: StopDetailComponent }
    ] },
  { path: 'upload', component: UploadComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {



}
