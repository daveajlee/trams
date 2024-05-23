import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimetableviewerComponent } from './timetableviewer.component';

describe('TimetableviewerComponent', () => {
  let component: TimetableviewerComponent;
  let fixture: ComponentFixture<TimetableviewerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TimetableviewerComponent]
    });
    fixture = TestBed.createComponent(TimetableviewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
