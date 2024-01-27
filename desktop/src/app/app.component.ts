import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [DatePipe]
})
export class AppComponent {

  file: File | null = null;
  title = 'trams-frontend';
  companyName: string;
  playerName: string;
  difficultyLevel: string;
  startingDate: string;
  showOutlet: boolean;
  currentDate = new Date();

  constructor(public router: Router, private datePipe: DatePipe) {
    this.difficultyLevel = 'Easy';
    this.startingDate = this.datePipe.transform(this.currentDate, 'yyyy-MM-dd');
  }

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
    this.router.navigate(['scenariolist'], { queryParams: { company: this.companyName,
        playerName: this.playerName, startingDate: this.startingDate, difficultyLevel: this.difficultyLevel } });
  }

  /**
   * On submission of the load game form, we load the game.
   */
  async onLoadSubmit(): Promise<void> {
    var fileContent = await this.readFileContent(this.file);
    var xmlDoc;
    if (window.DOMParser) {
      let parser = new DOMParser();
      xmlDoc = parser.parseFromString(fileContent, "text/xml");
      console.log(xmlDoc);
    }
    console.log('Loading game from file ' + this.file);
  }

  /**
   * Read the contents of the file.
   */
  readFileContent(file: File): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      if (!file) {
        resolve('');
      }

      const reader = new FileReader();

      reader.onload = (e) => {
        const text = reader.result.toString();
        resolve(text);

      };

      reader.readAsText(file);
    });
  }

}
