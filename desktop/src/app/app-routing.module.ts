import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RoutesComponent} from './routes/routes.component';
import {ScenariolistComponent} from './scenariolist/scenariolist.component';
import { ScenariomapComponent } from './scenariomap/scenariomap.component';
import { ScenarioinfoComponent } from './scenarioinfo/scenarioinfo.component';
import {StopsComponent} from './stops/stops.component';
import {StopDetailComponent} from './stops/stop-detail/stop-detail.component';
import {UploadComponent} from './upload/upload.component';
import {VehiclesComponent} from './vehicles/vehicles.component';
import {VehicleDetailComponent} from './vehicles/vehicle-detail/vehicle-detail.component';
import {ManagementComponent} from './management/management.component';
import {RoutecreatorComponent} from "./routecreator/routecreator.component";
import {TimetablecreatorComponent} from "./timetablecreator/timetablecreator.component";
import {MessagesComponent} from "./messages/messages.component";
import {DriversComponent} from "./drivers/drivers.component";

/**
 * Define the links which work in this application.
 */
const appRoutes: Routes = [
    { path: 'drivers', component: DriversComponent },
    { path: 'management', component: ManagementComponent },
    { path: 'messages', component: MessagesComponent },
    { path: 'routes', component: RoutesComponent },
    { path: 'routecreator', component: RoutecreatorComponent },
    { path: 'timetablecreator', component: TimetablecreatorComponent},
    { path: 'scenariolist', component: ScenariolistComponent },
    { path: 'scenarioinfo', component: ScenarioinfoComponent },
    { path: 'scenariomap', component: ScenariomapComponent },
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
