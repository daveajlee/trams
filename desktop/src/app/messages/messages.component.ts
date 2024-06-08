import { Component } from '@angular/core';
import { faInbox } from '@fortawesome/free-solid-svg-icons';
import { faEnvelopeCircleCheck } from '@fortawesome/free-solid-svg-icons';
import { faShareFromSquare } from '@fortawesome/free-solid-svg-icons';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import {Message} from "./message.model";
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.css']
})
export class MessagesComponent {
  faInbox = faInbox;
  faEnvelopeCircleCheck = faEnvelopeCircleCheck;
  faSnareFromSquare = faShareFromSquare;
  faTrash = faTrash;
  private displayMessages: Message[];

  private selectedFolder: string;

  constructor(private gameService: GameService, public router: Router) {
    this.displayMessages = [];
    this.onInboxSelect();
    this.selectedFolder = "INBOX";
    console.log(this.selectedFolder);
  }

  onInboxSelect(): void {
    this.displayMessages = this.gameService.getGame().filterMessagesByFolder("INBOX");
    this.selectedFolder = "INBOX";
  }

  onOutboxSelect(): void {
    this.selectedFolder = "OUTBOX";
    this.displayMessages = this.gameService.getGame().filterMessagesByFolder("OUTBOX");
  }

  onSentSelect(): void {
    this.selectedFolder = "SENT ITEMS";
    this.displayMessages = this.gameService.getGame().filterMessagesByFolder("SENT ITEMS");
  }

  onTrashSelect(): void {
    this.selectedFolder = "TRASH";
    this.displayMessages = this.gameService.getGame().filterMessagesByFolder("TRASH");
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

  /**
   * Get the folder that is currently selected.
   * @return the folder that is currently selected as a String.
   */
  getSelectedFolder(): string {
    return this.selectedFolder;
  }

  /**
   * Get the messages that should be displayed based on the selected folder.
   * @return the messages as an Array of Messages.
   */
  getDisplayMessages(): Message[] {
    return this.displayMessages;
  }

}
