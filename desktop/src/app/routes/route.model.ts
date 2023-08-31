import {Timetable} from "../shared/timetable.model";

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

  addTimetable ( timetable: Timetable ) {
    if ( this.timetables ) {
      this.timetables.push(timetable);
    } else {
      this.timetables = [];
      this.timetables.push(timetable);
    }
  }

}
