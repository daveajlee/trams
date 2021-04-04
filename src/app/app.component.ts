import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'trams-frontend';

  showOutlet: boolean;

  onActivate(event: any): void {
    this.showOutlet = true;
  }

  onDeactivate(event: any): void {
    this.showOutlet = false;
  }

}
