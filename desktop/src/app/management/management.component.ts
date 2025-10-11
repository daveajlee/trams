import { Component, OnInit } from '@angular/core';
import {GameService} from '../shared/game.service';
import {Router} from '@angular/router';
import {TipService} from "../shared/tip.service";
import {TimeHelper} from "../shared/time.helper";
import {ServerService} from "../shared/server.service";
import {HeaderComponent} from '../header/header.component';

@Component({
  selector: 'app-management',
  templateUrl: './management.component.html',
  imports: [
    HeaderComponent
  ],
  styleUrls: ['./management.component.css']
})
export class ManagementComponent implements OnInit {

  private dateTimeFromServer: string = "";
  private balanceFromServer: number = 0;
  private satisfactionFromServer: number = 0;
  private doRoutesExist: boolean = false;
  private doVehiclesExist: boolean = false;
  private doAllocationsExist: boolean = false;

  constructor(private gameService: GameService, public router: Router, private tipService: TipService,
              private serverService: ServerService) {
    if ( !this.gameService.isOfflineMode() ) {
      this.serverService.getCurrentDateTime().then((dateTime) => {
        this.dateTimeFromServer = dateTime;
      } )
      this.serverService.getBalance().then((balance) => {
        this.balanceFromServer = balance;
      } )
      this.serverService.getPassengerSatisfaction().then((satisfaction) => {
        this.satisfactionFromServer = satisfaction;
      } )
      this.serverService.getRoutes().then((routes) => {
        if ( routes ) {
          this.doRoutesExist = routes.routeResponses.length > 0;
        } else {
          this.doRoutesExist = false;
        }
      })
      this.serverService.getVehicles().then((vehicles) => {
        if ( vehicles ) {
          this.doVehiclesExist = vehicles.vehicleResponses.length > 0;
          this.doAllocationsExist = false;
          for ( let vehicle of vehicles.vehicleResponses ) {
            if ( vehicle.allocatedRoute ) {
              this.doAllocationsExist = true;
            }
          }
        } else {
          this.doVehiclesExist = false;
          this.doAllocationsExist = false;
        }

      })
    }
  }

  /**
   * Retrieve the current date which can either be from the local game or the server if in online mode.
   * @return the current date and time in the format to display to the user.
   */
  getCurrentDate(): string {
    if ( this.gameService.isOfflineMode() && this.gameService.getGame()) {
      return this.gameService.getGame()!.getCurrentDateTime().toLocaleString('en-gb', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit' });
    } else {
      if ( this.dateTimeFromServer ) {
        let date = TimeHelper.formatStringAsDateObject(this.dateTimeFromServer);
        return date.toLocaleString('en-gb', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit' });
      }
    }
    return "";
  }

  getBalance(): string {
    if ( this.gameService.isOfflineMode() && this.gameService.getGame() ) {
      return '' + this.gameService.getGame()!.getBalance();
    } else {
      if ( this.balanceFromServer ) {
        return '' + this.balanceFromServer;
      }
    }
    return "";
  }

  getPassengerSatisfaction(): number {
    if ( this.gameService.isOfflineMode() && this.gameService.getGame() ) {
      return this.gameService.getGame()!.getPassengerSatisfaction();
    } else {
      if ( this.satisfactionFromServer ) {
        return this.satisfactionFromServer;
      }
    }
    return 0;
  }

  ngOnInit(): void {
  }

  onLocationMap(): void {
    this.router.navigate(['scenariomap']);
  }

  onViewInformation(): void {
    this.router.navigate(['scenarioinfo']);
  }

  onViewStops(): void {
    this.router.navigate(['stops'])
  }

  onLoadLiveSituation(): void {
    this.router.navigate(['livesituation'])
  }

  onCreateRoute(): void {
    this.router.navigate(['routecreator']);
  }

  onViewRoutes(): void {
    this.router.navigate(['routes']);
  }

  onUploadData(): void {
    this.router.navigate(['upload']);
  }

  onViewMessages(): void {
    this.router.navigate(['messages']);
  }

  onEmployDriver(): void {
    this.router.navigate(['drivercreator']);
  }

  onViewDrivers(): void {
    this.router.navigate(['drivers']);
  }

  onPurchaseVehicle(): void {
    this.router.navigate(['vehicleshowroom']);
  }

  onViewDepot(): void {
    this.router.navigate(['vehicles']);
  }

  onChangeAllocation(): void {
    this.router.navigate(['allocations']);
  }

  onViewAllocations(): void {
    this.router.navigate(['allocationsList']);
  }

  onResign(): void {
    // If we are in offline mode then simply confirm and return to start page.
    if ( this.gameService.isOfflineMode() && this.gameService.getGame() ) {
      if(confirm("Are you sure you want to resign from " + this.gameService.getGame()!.getCompanyName() + "? This will end " +
          "your game and any changes you have made will not be saved.")) {
        // Currently it is enough to redirect to the homepage since we do not save data in local storage yet.
        this.router.navigate([''])
      }
    } else {
      // If we are in online mode then confirm, delete from server and return to start page.
      if(confirm("Are you sure you want to resign from " + this.serverService.getCompanyName() + "? This will end " +
          "your game and any changes you have made will not be saved.")) {
        // Delete the company
        this.serverService.deleteLoadedCompany().then(() => {
          this.router.navigate([''])
        })
      }
    }
  }

  noRoutesExist(): boolean {
    if ( this.gameService.isOfflineMode() && this.gameService.getGame() ) {
      return !this.gameService.getGame()!.doRoutesExist();
    } else {
      return !this.doRoutesExist;
    }
  }

  noVehiclesExist(): boolean {
    if ( this.gameService.isOfflineMode() && this.gameService.getGame() ) {
      return this.gameService.getGame()!.doRoutesExist() && !this.gameService.getGame()!.doVehiclesExist();
    } else {
      return this.doRoutesExist && !this.doVehiclesExist;
    }

  }

  noAllocationsExist(): boolean {
    if ( this.gameService.isOfflineMode() && this.gameService.getGame() ) {
      return this.gameService.getGame()!.doRoutesExist() && this.gameService.getGame()!.doVehiclesExist()
          && !this.gameService.getGame()!.doAllocationsExist();
    } else {
      return this.doRoutesExist && this.doVehiclesExist && !this.doAllocationsExist;
    }

  }

  showRandomTip(): string {
    return this.tipService.getRandomTip();
  }

}
