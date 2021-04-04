import {Directive, ElementRef, HostBinding, HostListener} from '@angular/core';

@Directive({
  selector: '[appDropdown]'
})
/**
 * This class enables the dropdown functionality in the header.
 */
export class DropdownDirective {
  @HostBinding('class.open') isOpen = false;
  @HostListener('document:click', ['$event']) toggleOpen(event: Event): void {
    this.isOpen = this.elRef.nativeElement.contains(event.target) ? !this.isOpen : false;
  }

  /**
   * Construct a new DropdownDirective and do nothing.
   * @param elRef an element ref which is not currently read.
   */
  constructor(private elRef: ElementRef) {}
}
