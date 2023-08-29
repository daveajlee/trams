import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimetablecreatorComponent } from './timetablecreator.component';

describe('TimetablecreatorComponent', () => {
  let component: TimetablecreatorComponent;
  let fixture: ComponentFixture<TimetablecreatorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TimetablecreatorComponent]
    });
    fixture = TestBed.createComponent(TimetablecreatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
