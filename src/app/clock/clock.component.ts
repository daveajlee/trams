import {Component, OnDestroy, OnInit} from '@angular/core';

@Component({
  selector: 'app-clock',
  templateUrl: './clock.component.html',
  styleUrls: ['./clock.component.css']
})
/**
 * This class implements the functionality for the clock and ensures that it is updated on a regular basis.
 */
export class ClockComponent implements OnInit, OnDestroy {
  hours: string;
  minutes: string;
  seconds: string;
  private timerId = null;

  /**
   * When constructing the object, the time should be set to the current time and marked that the time was last refreshed at this time.
   */
  ngOnInit(): void {
    this.setCurrentTime();
    this.timerId = this.updateTime();
  }

  /**
   * Remove the interval of updating the time when the object is destroyed.
   */
  ngOnDestroy(): void {
    clearInterval(this.timerId);
  }

  /**
   * Private helper method to set the current time in the format HH:mm
   */
  private setCurrentTime(): void {
    const time = new Date(Date.now());
    this.hours = this.leftPadZero(time.getHours());
    this.minutes = this.leftPadZero(time.getMinutes());
  }

  /**
   * Private helper method to automatically update the time every 30 seconds.
   */
  private updateTime(): void {
    setInterval(() => {
      this.setCurrentTime();
    }, 30000);
  }

  /**
   * Private helper method to add a 0 for numbers less than 10 in order to ensure two-digit numbers.
   * @param value a number which should be formatted into a two-digit number with leading 0 if needed.
   */
  private leftPadZero(value: number): string {
    return value < 10 ? `0${value}` : value.toString();
  }
}
