import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScheduleInformationComponent } from './schedule-information.component';

describe('ScheduleInformationComponent', () => {
  let component: ScheduleInformationComponent;
  let fixture: ComponentFixture<ScheduleInformationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ScheduleInformationComponent]
    });
    fixture = TestBed.createComponent(ScheduleInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
