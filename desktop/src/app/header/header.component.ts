import { Component, OnInit } from '@angular/core';
import packageJson from '../../../package.json';
import {Router} from "@angular/router";
import {LoadService} from "../shared/load.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
/**
 * This class implements the functionality for the header component which currently only ensures that the navigation is collapsed.
 */
export class HeaderComponent implements OnInit {

  public version: string = packageJson.version;

  selectedHelpTopic: string;

  collapsed = true;

  loadService: LoadService;

  /**
   * Construct a new HeaderComponent and do nothing.
   */
  constructor(public router: Router, private loadService2: LoadService) {
      this.loadService = loadService2;
  }

  /**
   * When constructing the object, on initialisation do nothing.
   */
  ngOnInit(): void {
    this.selectedHelpTopic = "Welcome";
  }

  /**
   * Retrieve the list of help topics.
   */
  getHelpTopics(): string[] {
    return [
        "Welcome",
        "Getting Started",
        "Create New Game",
        "Add/Edit/Delete Route",
        "Purchase/Sell Vehicle",
        "Load Game",
        "Allocate Vehicles to Routes",
        "Control Screen",
        "Make Contact with Vehicle",
        "Vehicle Info Screen",
        "Web Site"
    ];
  }

  /**
   * Clicking on the new game button redirects to the new game screen.
   */
  onNewGameClick(): void {
      this.router.navigate(['newgame']);
  }

  /**
   * Load the first of the selected files into the game memory.
   * @param files the selected files
   */
  async onLoadGameClick(files: FileList | null): Promise<void> {
      if (files) {
          // Currently we only support tcs files.
          if ( files.item(0).name.endsWith(".tcs") ) {
              console.log('We process this in the tcs file');
              await this.loadService.onLoadTcsFile(files.item(0));
              await this.router.navigate(['management']);
          } else {
              alert('This file type is not supported. Please choose another file.');
          }
      }
  }

    /**
     * Save the current game to a json file.
     */
  async onSaveGameClick(): Promise<void> {
      const result = await window.showSaveFilePicker({
          types: [
              {
                  description: 'JSON Files',
                  accept: {
                      'application/json': ['.json'],
                  },
              },
          ],
        });
  }

  /**
   * Retrieve
   */
  getHelpContent(selectedHelpTopic: string): string {
      if ( selectedHelpTopic === "Welcome" ) {
          return "<h2 style=\"text-align:center;\">Welcome to TraMS Help!</h2>\n" +
              "<p>Welcome to TraMS, a brand new transport management game, where you ensure that your own public transport company picks up passengers on-time. If your vehicles are delayed in traffic then you need to take action to get them back on schedule before your passengers get frustrated and leave for another transport operator. Can your transport operator stand the test of time or will your passengers take your company off the road/rails?</p>\n" +
              "\n" +
              "<p>TraMS was originally developed by David A J Lee and is now open source software.</p>";
      } else if ( selectedHelpTopic === "Getting Started" ) {
          return "<h2 style=\"text-align:center;\">Getting Started</h2>\n" +
              "<p>\n" +
              "When you load TraMS, the TraMS Welcome Screen is displayed. There are three options on this screen:</p>\n" +
              "<ul>\n" +
              "<li><p>You can create a New Game by clicking on the New Game picture. If you choose this option, please move to the \"Create New Game\" help topic.</p></li>\n" +
              "<li><p>You can load an Existing Game by clicking on the Load Game picture. If you choose this option, you will be prompted to choose the location of the saved game. Please then move to the \"Load Existing Game\" help topic.</p></li>\n" +
              "<li><p>You can also exit TraMS by clicking on the Exit Game picture at the bottom of the screen.</p></li>\n" +
              "</ul>";
      } else if ( selectedHelpTopic === "Create New Game" ) {
          return "<h2 style=\"text-align:center;\">New Game</h2>\n" +
              "<p>\n" +
              "Creating a new game in TraMS is possible by clicking on \"New Game\" from the TraMS Welcome Screen or clicking File > New Game from the menu bar. The New Game screen (shown above) will be displayed. On this screen, you should enter your name in the box beside player name. In the 2nd Preview Edition, the Landuff Scenario is the only scenario available. Consequently, click the \"Create Game\" button to continue. Alternatively, you can go back to the Welcome Screen by clicking on the \"Back to Welcome Screen\" button.\n" +
              "</p>\n" +
              "\n" +
              "<p>The scenario screen gives you information about initial funding and targets for the scenario. Please read the information carefully and then click \"Continue\" to move to the management screen.</p>";
      } else if ( selectedHelpTopic === "Add/Edit/Delete Route" ) {
          return "<h3>Add Route</h3>\n" +
              "<p>From the Management Screen, you can click \"Create Route\" on the Routes panel to create a new route. You need to enter the following information on the Create Route screen which appears:\n" +
              "<span style=\"font-style:italic;\">Route Number:</span> You must enter a route number - this can either be a number (e.g. 1) or text (e.g. 1B).\n" +
              "<span style=\"font-style:italic;\">Stops:</span> You may choose up to 5 stops which this route will serve by clicking on the down arrow and choosing from the pop-up list.\n" +
              "<span style=\"font-style:italic;\">Route Timetable:</span> You must create 1 or more timetables for this route - each timetable has an exclusive period of validity.\n" +
              "\n" +
              "Each timetable must have the following information:\n" +
              "<span style=\"font-style:italic;\">Name:</span> Give a descriptive name for this timetable so that you will recognise it later.\n" +
              "<span style=\"font-style:italic;\">Service Pattern(s):</span> You must create 1 or more service patterns for this route. Service patterns determine the frequency and hours of operation of the route on certain days.\n" +
              "\n" +
              "Each service pattern must have the following information:\n" +
              "<span style=\"font-style:italic;\">Name:</span> Give a descriptive name for this service pattern so that you will recognise it later.\n" +
              "<span style=\"font-style:italic;\">Days of Operation:</span> Tick the box beside the days when this service will operate.\n" +
              "<span style=\"font-style:italic;\">Between:</span> Choose the stops which the service will run between with this frequency.\n" +
              "<span style=\"font-style:italic;\">From/To/Every:</span> Choose the times that the route will operate and the frequency it will operate at.\n" +
              "\n" +
              "Once you have added at least one timetable and one service pattern, the \"Create Route\" button will be available. After clicking on the \"Create Route\" button, the route will be saved and you will be returned to the Management Screen. If you wish to be returned to the Management Screen without saving, click on the \"Return to Previous Screen\" button.<br/><br/>\n" +
              "<h3>View Route Info</h3>\n" +
              "\n" +
              "<p>To access the View Route Info screen, click on \"View Route Info\" from the Management Screen. You can view the route timetable for a particular day by clicking on the day from the box. To change the route, click on the relevant route number from the right-hand side.</p>\n" +
              "\n" +
              "<h3>Amend Route</h3>\n" +
              "\n" +
              "<p>Click on \"Amend Route\" from the View Route Info screen to make amendments to a route. Follow the guidance given for stops, timetables and screen patterns as above.</p>\n" +
              "\n" +
              "<h3>Delete Route</h3>\n" +
              "<p>You cannot delete routes in the 2nd Preview Edition. However, routes will be disabled if all timetables for that route have expired.</p>\n\n\n\n";
      } else if ( selectedHelpTopic === "Purchase/Sell Vehicle") {
          return "<h2 style=\"text-align:center;\">Purchase/Sell Vehicle</h2>\n" +
              "\n" +
              "<h3>Purchase Vehicle</h3>\n" +
              "<p>From the Management Screen, you can click \"Purchase Vehicle\" in the Vehicles panel to buy a new vehicle. You can choose the type of the vehicle by clicking on \"Next Vehicle Type\" and \"Previous Vehicle Type\" buttons as applicable. You can also choose the quantity. If the total price exceeds your balance then you cannot buy the vehicle! Click \"Purchase Vehicles\" to purchase the vehicles and return to the Management Screen. Please note that vehicles take 72 hours to arrive after purchase.<br/>\n" +
              "</p>\n" +
              "\n" +
              "<h3>Sell Vehicle</h3>\n" +
              "<p>From the Management Screen, click on \"View Depot\" in the Vehicles panel. You can then get information on a vehicle by clicking on the vehicle id. If you wish to sell that vehicle, click \"Sell Vehicle\". An appropriate sum of money will be added to your balance and the vehicle will be removed immediately.</p>";
      } else if ( selectedHelpTopic === "Load Game" ) {
          return "<h2 style=\"text-align:center;\">Load Game</h2>\n" +
              "<p>\n" +
              "You can load a game in TraMS, by clicking on \"Load Game\" from the TraMS Welcome Screen or clicking File > Load Game from the menu bar. You will then be prompted to choose the location of the saved game as shown in the screen above. The file must have a .tms file extension. Click once on the name of the game and then click Open.<br/><br/>\n" +
              "If you see the message below when you load a TraMS saved game, please check that the game was saved in TraMS. If it was, it may have been an earlier version of TraMS. Please check the TraMS website at <a href=\"https://www.davelee.de/trams\">https://www.davelee.de/trams</a> as a convertor may be available.</p>";
      } else if ( selectedHelpTopic === "Allocate Vehicles to Routes") {
          return "<h3>Allocate Vehicles</h3>\n" +
              "<p>On the Allocation Screen, you will see two columns - the left-hand column contains route schedules which have not had vehicles assigned to them and the right-hand column contains vehicles which have no route schedules assigned to them. To allocate a vehicle to a route schedule, click on the vehicle you would like to use (from the right-hand list) and the route you would like to assign it to (the left-hand list) and click the \"Allocate\" button.\n" +
              "</p>\n" +
              "\n" +
              "<h3>Deallocate Vehicles</h3>\n" +
              "<p>If you would like to change an allocation, simply click on the allocation you wish to change from the Allocations list and click the \"Deallocate\" button. You are then free to reassign either that vehicle or route as a new allocation (see \"Allocate Vehicles\" above).</p>\n" +
              "\n" +
              "<h3>Finished Allocating?</h3>\n" +
              "<p>Click \"Save Allocations\" to confirm the allocations and you will be returned to the Management Screen.</p>";
      } else if ( selectedHelpTopic === "Control Screen") {
          return "<h2 style=\"text-align:center;\">Control Screen</h2>\n" +
              "\n" +
              "<h3>Vehicle Status</h3>\n" +
              "<p>The Control Screen is the main screen within TraMS. From this screen, you can monitor the status of any vehicle currently running. To choose a route to monitor, click on that route from the list at the top of the screen. From here, you can also monitor the time. Remember your vehicles are more likely to run late during the morning and evening rush hours! Four route schedules are shown as triangles pointing in the direction that they are travelling with the stops on that route shown as columns. A red triangle indicates that the vehicle is running late whilst a green triangle indicates the vehicle is running to schedule. You can get more information on a route schedule by clicking on the triangle - see the \"Vehicle Information Screen\" help topic for more information. If the selected route has more than four route schedules, click on the \"Next Vehicles\" button to view the other route schedules.</p>\n" +
              "\n" +
              "<h3>Changing Vehicles/Routes</h3>\n" +
              "<p>You can click on the Management tab to visit the Management Screen to make changes to routes and purchase/sell vehicles. You can also click the Messages tab to read important messages. You should refer to the individual help topics if you need more information.</p>\n" +
              "\n" +
              "<h3>Passenger Satisfaction</h3>\n" +
              "<p>Remember to watch your passenger satisfaction bar at the bottom! This will drop from 100% according to how many vehicles are currently running late. You may want to re-time your vehicles to increase passenger satisfaction. If it falls too low, you will be sacked!</p>\n" +
              "\n" +
              "<h3>Pause Simulation</h3>\n" +
              "<p>You can pause the simulation at any time by clicking the \"||\" button. When you are ready to resume, click on the \"|>\" button. You can also speed up or slow down the simulation using the appropriate buttons.</p>";
      } else if ( selectedHelpTopic === "Make Contact with Vehicle") {
          return "<h2 style=\"text-align:center;\">Make Contact With Vehicle</h2>\n" +
              "\n" +
              "<h3>Terminating Early</h3>\n" +
              "<p>There are two main options on the Make Contact screen. The first option is to request that the vehicle terminates at a stop earlier than the normal terminus. To use this option, choose the Stop where you want the vehicle to terminate and then click on the \"Terminate at Stop\" button. This will reduce the vehicle's delay but cause a temporary decrease in Passenger Satisfaction (since some passengers did not receive a service). This option is generally more effective than the Out of Service option. Once the vehicles reaches that stop, it will start its next service from that stop and not service any of the stops before it.\n" +
              "</p>\n" +
              "\n" +
              "<h3>Out of Service</h3>\n" +
              "<p>Alternatively, you can choose to put the vehicle out of service for a few stops. The vehicle will go about 50% quicker on that part of the route which will reduce your vehicle delay but also impact on passenger satisfaction. To use this option, choose the Stop where you want the vehicle to re-enter service and click on the \"Out of Service until Stop\" button.</p>\n" +
              "\n" +
              "<h3>Finished Contact?</h3>\n" +
              "<p>When you are finished making contact with the vehicle, please click \"End Contact\" and the Control Screen will appear on screen.</p>";
      } else if ( selectedHelpTopic === "Vehicle Info Screen" ) {
          return "<h2 style=\"text-align:center;\">Vehicle Info Screen</h2>\n" +
              "\n" +
              "<h3>Vehicle Information</h3>\n" +
              "<p>After clicking on a box from the Control Screen, the Vehicle Information Screen appears (as shown above). The following information is shown:<br/>\n" +
              "<span style=\"font-style:italic;\">Timetable Id:</span> Route number and route schedule number which this vehicle is running.<br/>\n" +
              "<span style=\"font-style:italic;\">Vehicle Id:</span> The ID of the vehicle running the service.<br/>\n" +
              "<span style=\"font-style:italic;\">Location:</span> Where the vehicle currently is.<br/>\n" +
              "<span style=\"font-style:italic;\">Destination:</span> The direction (i.e. terminus) which the vehicle is heading towards.<br/>\n" +
              "<span style=\"font-style:italic;\">Delay:</span> The number of minutes late that the vehicle is running.<br/>\n" +
              "If the vehicle is running late or you want to make some changes to that vehicle, click \"Make Contact\" to view the Make Contact screen and refer to the \"Make Contact with Vehicle\" help topic. If you don't want to make contact, click \"Close Window\" to return to the control screen.<br/>\n" +
              "</p>";
      } else if ( selectedHelpTopic === "Web Site" ) {
          return "<h2 style=\"text-align:center;\">TraMS Web Site</h2>\n" +
              "<p>\n" +
              "The TraMS web site is the online extension for the TraMS simulation game. Visit the TraMS website at <a href=\"https://www.davelee.de/trams\">https://www.davelee.de/en/trams</a> for saved games and new versions of TraMS.</p>";
      }
      return selectedHelpTopic;
  }

}
