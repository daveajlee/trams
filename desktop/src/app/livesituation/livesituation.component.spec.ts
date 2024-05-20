import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LivesituationComponent } from './livesituation.component';

describe('LivesituationComponent', () => {
  let component: LivesituationComponent;
  let fixture: ComponentFixture<LivesituationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LivesituationComponent]
    });
    fixture = TestBed.createComponent(LivesituationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
