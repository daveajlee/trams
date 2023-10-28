import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrivercreatorComponent } from './drivercreator.component';

describe('DrivercreatorComponent', () => {
  let component: DrivercreatorComponent;
  let fixture: ComponentFixture<DrivercreatorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DrivercreatorComponent]
    });
    fixture = TestBed.createComponent(DrivercreatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
