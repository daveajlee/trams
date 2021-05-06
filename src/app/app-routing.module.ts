import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RoutesComponent} from './routes/routes.component';
import {StopsComponent} from './stops/stops.component';
import {StopDetailComponent} from './stops/stop-detail/stop-detail.component';
import {UploadComponent} from './upload/upload.component';
import {VehiclesComponent} from './vehicles/vehicles.component';
import {VehicleDetailComponent} from './vehicles/vehicle-detail/vehicle-detail.component';

/**
 * Define the links which work in this application.
 */
const appRoutes: Routes = [
  { path: 'routes', component: RoutesComponent },
  { path: 'stops', component: StopsComponent, children: [
      { path: ':id', component: StopDetailComponent }
    ] },
  { path: 'upload', component: UploadComponent },
  { path: 'vehicles', component: VehiclesComponent, children: [
      { path: ':id', component: VehicleDetailComponent}
    ]}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {



}
