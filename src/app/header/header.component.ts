import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
/**
 * This class implements the functionality for the header component which currently only ensures that the navigation is collapsed.
 */
export class HeaderComponent implements OnInit {

  collapsed = true;

  /**
   * Construct a new HeaderComponent and do nothing.
   */
  constructor() { }

  /**
   * When constructing the object, on initialisation do nothing.
   */
  ngOnInit(): void {
  }

}
