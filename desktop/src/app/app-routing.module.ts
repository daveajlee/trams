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
import {DriverDetailComponent} from "./drivers/driver-detail/driver-detail.component";
import {OptionsComponent} from "./options/options.component";
import {DrivercreatorComponent} from "./drivercreator/drivercreator.component";
import {VehicleshowroomComponent} from "./vehicleshowroom/vehicleshowroom.component";
import {AllocationsComponent} from "./allocations/allocations.component";
import {AllocationslistComponent} from "./allocationslist/allocationslist.component";
import {LivesituationComponent} from "./livesituation/livesituation.component";
import {ScheduleInformationComponent} from "./schedule-information/schedule-information.component";
import {NewgameComponent} from "./newgame/newgame.component";
import {RouteeditorComponent} from "./routeeditor/routeeditor.component";
import {TimetableviewerComponent} from "./timetableviewer/timetableviewer.component";

/**
 * Define the links which work in this application.
 */
const appRoutes: Routes = [
    { path: 'allocations', component: AllocationsComponent },
    { path: 'allocationsList', component: AllocationslistComponent },
    { path: 'drivers', component: DriversComponent, children: [
        { path: ':id', component: DriverDetailComponent}
    ] },
    { path: 'livesituation', component: LivesituationComponent,
        children: [{path: ':routeScheduleId', component: ScheduleInformationComponent}]},
    { path: 'management', component: ManagementComponent },
    { path: 'messages', component: MessagesComponent },
    { path: 'newgame', component: NewgameComponent },
    { path: 'options', component: OptionsComponent },
    { path: 'routes', component: RoutesComponent },
    { path: 'routecreator', component: RoutecreatorComponent },
    { path: 'routeeditor/:routeNumber', component: RouteeditorComponent},
    { path: 'timetablecreator', component: TimetablecreatorComponent},
    { path: 'timetableviewer/:routeNumber', component: TimetableviewerComponent },
    { path: 'drivercreator', component: DrivercreatorComponent },
    { path: 'scenariolist', component: ScenariolistComponent },
    { path: 'scenarioinfo', component: ScenarioinfoComponent },
    { path: 'scenariomap', component: ScenariomapComponent },
    { path: 'stops', component: StopsComponent, children: [
      { path: ':id', component: StopDetailComponent }
      ] },
    { path: 'upload', component: UploadComponent },
    { path: 'vehicles', component: VehiclesComponent, children: [
      { path: ':id', component: VehicleDetailComponent}
    ]},
    { path: 'vehicleshowroom', component: VehicleshowroomComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {



}
