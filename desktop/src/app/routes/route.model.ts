import {Timetable} from "../shared/timetable.model";
import {ScheduleModel} from "../stops/stop-detail/schedule.model";

/**
 * This class defines a model for Routes in TraMS which consist of an id, routeNumber and agency.
 */
export class Route {

  private routeNumber: string;
  private company: string;
  private startStop: string;
  private endStop: string;
  private stops: string[];
  private timetables: Timetable[];
  private schedules: ScheduleModel[];
  private nightRoute: boolean;

  constructor( routeNumber: string, startStop: string, endStop: string, stops: string[], company: string) {
    this.routeNumber = routeNumber;
    this.startStop = startStop;
    this.endStop = endStop;
    this.stops = stops;
    this.company = company;
  }

  addTimetable ( timetable: Timetable ) {
    if ( !this.timetables ) {
      this.timetables = [];
    }
    this.timetables.push(timetable);
  }

  addSchedule ( schedule: ScheduleModel ) {
    if ( !this.schedules ) {
      this.schedules = [];
    }
    this.schedules.push(schedule);
  }

  /**
   * Get the route number.
   * @return the route number as a String.
   */
  getRouteNumber(): string {
    return this.routeNumber;
  }

  /**
   * Get the start stop for this route.
   * @return the start stop name as a String.
   */
  getStartStop(): string {
    return this.startStop;
  }

  /**
   * Set the start stop for this route.
   * @param startStop the start stop name as a String.
   */
  setStartStop( startStop: string ) {
    this.startStop = startStop;
  }

  /**
   * Get the stops for this route (excluding start and end stop).
   * @return the stop names as a String array.
   */
  getStops(): string[] {
    return this.stops;
  }

  /**
   * Get the end stop for this route.
   * @return the end stop name as a String.
   */
  getEndStop(): string {
    return this.endStop;
  }

  /**
   * Get the timetables for this route.
   * @return the timetables currently for this route as a Timetable array.
   */
  getTimetables(): Timetable[] {
    return this.timetables;
  }

  /**
   * Set the schedules for this route as part of the loading feature.
   * @param schedules the schedules as a ScheduleModel array.
   */
  setSchedules( schedules: ScheduleModel[] ) {
    this.schedules = schedules;
  }

  /**
   * Get the schedules for this route.
   * @return the schedules as a ScheduleModel array.
   */
  getSchedules(): ScheduleModel[] {
    return this.schedules;
  }

  /**
   * Check if timetables exist for this route.
   * @return a boolean which is true iff timetables exist for this route.
   */
  doTimetablesExist(): boolean {
    return this.timetables.length > 0;
  }

  /**
   * Check if we are dealing with a night route.
   * @return a boolean which is true iff it is a night route.
   */
  isNightRoute(): boolean {
    return this.nightRoute;
  }

  /**
   * Set whether or not we are dealing with a night route.
   * @param nightRoute a boolean which is true iff it is a night route.
   */
  setNightRoute(nightRoute: boolean) {
    this.nightRoute = nightRoute;
  }

  /**
   * Check if the specified timetable position has frequency patterns.
   * @param timetableIndex the index in the timetable array that should be checked for frequency patterns.
   */
  doFrequencyPatternsExist(timetableIndex: number) {
    return this.timetables[timetableIndex].frequencyPatterns.length > 0;
  }

  /**
   * Get the number of tours for a particular timetable and frequency pattern.
   * @param timetableIndex the index in the timetable array to retrieve.
   * @param frequencyPatternIndex the index in the frequency pattern array to retrieve.
   */
  getNumberTours(timetableIndex: number, frequencyPatternIndex: number) {
    return this.timetables[timetableIndex].frequencyPatterns[frequencyPatternIndex].numTours;
  }

  /**
   * Get the company running this route.
   * @return the company name as a String.
   */
  getCompany(): string {
    return this.company;
  }

}
