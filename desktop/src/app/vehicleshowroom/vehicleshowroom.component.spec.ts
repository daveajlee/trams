import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VehicleshowroomComponent } from './vehicleshowroom.component';

describe('VehicleshowroomComponent', () => {
  let component: VehicleshowroomComponent;
  let fixture: ComponentFixture<VehicleshowroomComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VehicleshowroomComponent]
    });
    fixture = TestBed.createComponent(VehicleshowroomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
