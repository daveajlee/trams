import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {DatePipe} from '@angular/common';
import {LoadService} from "./shared/load.service";
import {GameService} from "./shared/game.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [DatePipe]
})
export class AppComponent {

  private file: File | null = null;
  private showOutlet: boolean;

  constructor(public router: Router, private datePipe: DatePipe, private loadService: LoadService,
              private gameService: GameService) {
  }

  /**
   * Load the first of the selected files into the game memory.
   * @param files the selected files
   */
  async onFileInput(files: FileList | null): Promise<void> {
    if (files) {
      // Currently we only support tcs files.
      if ( files.item(0).name.endsWith(".tcs") ) {
        console.log('We process this in the tcs file');
        await this.loadService.onLoadTcsFile(files.item(0));
        await this.router.navigate(['management']);
      } else if ( files.item(0).name.endsWith(".json") ) {
        await this.loadService.onLoadJSONFile(files.item(0));
        await this.router.navigate(['management']);
      } else {
        alert('This file type is not supported. Please choose another file.');
      }
    }
  }
  onActivate(event: any): void {
    this.showOutlet = true;
  }

  onDeactivate(event: any): void {
    this.showOutlet = false;
  }

  isShowOutlet(): boolean {
    return this.showOutlet;
  }

  isOfflineMode(): boolean {
    return this.gameService.isOfflineMode();
  }

  /**
   * Clicking on the new game button redirects to the new game screen.
   */
  onNewGameClick(): void {
    this.router.navigate(['newgame']);
  }

  /**
   * Clicking on the load game button redirects to the load game screen.
   */
  onLoadGameClick(): void {
    this.router.navigate(['loadgame'])
  }

}
