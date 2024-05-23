import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { RoutesComponent } from './routes/routes.component';
import { StopsComponent } from './stops/stops.component';
import {AppRoutingModule} from './app-routing.module';
import {DropdownDirective} from './shared/dropdown.directive';
import {HttpClientModule} from '@angular/common/http';
import {StopsService} from './stops/stops.service';
import { StopDetailComponent } from './stops/stop-detail/stop-detail.component';
import {RoutesService} from './routes/routes.service';
import { ClockComponent } from './clock/clock.component';
import { UploadComponent } from './upload/upload.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import { VehiclesComponent } from './vehicles/vehicles.component';
import {VehiclesService} from './vehicles/vehicles.service';
import { VehicleDetailComponent } from './vehicles/vehicle-detail/vehicle-detail.component';
import { ManagementComponent } from './management/management.component';
import { ScenariolistComponent } from './scenariolist/scenariolist.component';
import {GameService} from './shared/game.service';
import { ScenariomapComponent } from './scenariomap/scenariomap.component';
import { ScenarioinfoComponent } from './scenarioinfo/scenarioinfo.component';
import { RoutecreatorComponent } from './routecreator/routecreator.component';
import { TimetablecreatorComponent } from './timetablecreator/timetablecreator.component';
import { MessagesComponent } from './messages/messages.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { DriversComponent } from './drivers/drivers.component';
import { DriverDetailComponent } from './drivers/driver-detail/driver-detail.component';
import { OptionsComponent } from './options/options.component';
import { DrivercreatorComponent } from './drivercreator/drivercreator.component';
import { VehicleshowroomComponent } from './vehicleshowroom/vehicleshowroom.component';
import { AllocationsComponent } from './allocations/allocations.component';
import { AllocationslistComponent } from './allocationslist/allocationslist.component';
import { LivesituationComponent } from './livesituation/livesituation.component';
import { ScheduleInformationComponent } from './schedule-information/schedule-information.component';
import { NewgameComponent } from './newgame/newgame.component';
import {LoadService} from "./shared/load.service";
import {SaveService} from "./shared/save.service";
import { SwitchlocalComponent } from './switchlocal/switchlocal.component';
import { RouteeditorComponent } from './routeeditor/routeeditor.component';
import { TimetableviewerComponent } from './timetableviewer/timetableviewer.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    RoutesComponent,
    StopsComponent,
    DropdownDirective,
    StopDetailComponent,
    ClockComponent,
    UploadComponent,
    VehiclesComponent,
    VehicleDetailComponent,
    ManagementComponent,
    ScenariolistComponent,
    ScenariomapComponent,
    ScenarioinfoComponent,
    RoutecreatorComponent,
    TimetablecreatorComponent,
    MessagesComponent,
    DriversComponent,
    DriverDetailComponent,
    OptionsComponent,
    DrivercreatorComponent,
    VehicleshowroomComponent,
    AllocationsComponent,
    AllocationslistComponent,
    LivesituationComponent,
    ScheduleInformationComponent,
    NewgameComponent,
    SwitchlocalComponent,
    RouteeditorComponent,
    TimetableviewerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FontAwesomeModule,
    FormsModule,
    BrowserAnimationsModule,
    MatProgressBarModule,
    ReactiveFormsModule
  ],
  providers: [StopsService, RoutesService, VehiclesService, GameService, LoadService, SaveService],
  bootstrap: [AppComponent]
})
export class AppModule { }
