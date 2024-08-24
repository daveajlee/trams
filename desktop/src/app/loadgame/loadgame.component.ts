import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {ServerService} from "../shared/server.service";
import {CompanyResponse} from "../management/company.response";

@Component({
  selector: 'app-loadgame',
  templateUrl: './loadgame.component.html',
  styleUrl: './loadgame.component.css'
})
export class LoadgameComponent {

  playerName: string;
  companies: CompanyResponse[];

  /**
   * Create a new load game component to load games when using online mode.
   * @param router the router object to navigate to other screens where necessary.
   * @param serverService the service managing the http calls.
   */
  constructor(public router: Router, private serverService: ServerService) {

  }

  /**
   * Get the list of companies that are available for the supplied player name.
   */
  async getCompanies(): Promise<void> {
    let companiesResponse = (await this.serverService.getCompanies(this.playerName));
    if ( companiesResponse ) {
      this.companies = companiesResponse.companyResponseList;
    }
  }

  /**
   * Delete the company with the supplied name.
   */
  async onDeleteCompany(name: string) {
    await this.serverService.deleteCompany(name, this.playerName);
    for ( let i = 0; i < this.companies.length; i++ ) {
      if ( this.companies[i].name === name ) {
        this.companies.splice(i, 1);
      }
    }
  }

  /**
   * Load the company with the supplied name.
   */
  async onLoadCompany(name: string) {
    await this.serverService.loadCompany(name, this.playerName);
    await this.router.navigate(['management'])
  }

  /**
   * Return the user to the start screen.
   */
  backToManagementScreen(): void {
    this.router.navigate(['']);
  }

}
