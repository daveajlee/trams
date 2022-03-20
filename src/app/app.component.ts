import { Component } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  file: File | null = null;
  title = 'trams-frontend';
  companyName: string;
  playerName: string;

  showOutlet: boolean;

  constructor(public router: Router) {}

  onFileInput(files: FileList | null): void {
    if (files) {
      this.file = files.item(0);
    }
  }
  onActivate(event: any): void {
    this.showOutlet = true;
  }

  onDeactivate(event: any): void {
    this.showOutlet = false;
  }

  /**
   * On submission of the start game form, we create a game.
   */
  onStartSubmit(): void {
    this.router.navigate(['scenariolist'], { queryParams: { company: this.companyName, playerName: this.playerName } });
  }

  /**
   * On submission of the load game form, we load the game.
   */
  onLoadSubmit(): void {
    console.log('Loading game from file ' + this.file);
  }

}
