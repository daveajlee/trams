import { Component } from '@angular/core';
import { faInbox } from '@fortawesome/free-solid-svg-icons';
import { faEnvelopeCircleCheck } from '@fortawesome/free-solid-svg-icons';
import { faShareFromSquare } from '@fortawesome/free-solid-svg-icons';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import {Message} from "./message.model";
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";
import {ServerService} from "../shared/server.service";

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

  constructor(private gameService: GameService, public router: Router, private serverService: ServerService) {
    this.displayMessages = [];
    this.onInboxSelect();
    this.selectedFolder = "INBOX";
    console.log(this.selectedFolder);
  }

  onInboxSelect(): void {
    if ( this.gameService.isOfflineMode() ) {
      this.displayMessages = this.gameService.getGame().filterMessagesByFolder("INBOX");
    } else {
      this.serverService.getMessagesByFolder("INBOX").then((messages) => {
        this.displayMessages = messages;
      });
    }
    this.selectedFolder = "INBOX";
  }

  onOutboxSelect(): void {
    if ( this.gameService.isOfflineMode() ) {
      this.displayMessages = this.gameService.getGame().filterMessagesByFolder("OUTBOX");
    } else {
      this.serverService.getMessagesByFolder("OUTBOX").then((messages) => {
        this.displayMessages = messages;
      });
    }
    this.selectedFolder = "OUTBOX";
  }

  onSentSelect(): void {
    if ( this.gameService.isOfflineMode() ) {
      this.displayMessages = this.gameService.getGame().filterMessagesByFolder("SENT ITEMS");
    } else {
      this.serverService.getMessagesByFolder("SENT ITEMS").then((messages) => {
        this.displayMessages = messages;
      });
    }
    this.selectedFolder = "SENT ITEMS";
  }

  onTrashSelect(): void {
    if ( this.gameService.isOfflineMode() ) {
      this.displayMessages = this.gameService.getGame().filterMessagesByFolder("TRASH");
    } else {
      this.serverService.getMessagesByFolder("TRASH").then((messages) => {
        this.displayMessages = messages;
      });
    }
    this.selectedFolder = "TRASH";
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
