import {Timetable} from "../shared/timetable.model";
import {ScheduleModel} from "../stops/stop-detail/schedule.model";

/**
 * This class defines a model for Routes in TraMS which consist of an id, routeNumber and agency.
 */
export class Route {

  public id: string;
  public routeNumber: string;
  public company: string;
  public startStop: string;
  public endStop: string;
  public stops: string[];
  public timetables: Timetable[];
  public schedules: ScheduleModel[];

  addTimetable ( timetable: Timetable ) {
    if ( this.timetables ) {
      this.timetables.push(timetable);
    } else {
      this.timetables = [];
      this.timetables.push(timetable);
    }
  }

}
