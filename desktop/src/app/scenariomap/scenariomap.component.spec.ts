import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScenariomapComponent } from './scenariomap.component';

describe('ScenariomapComponent', () => {
  let component: ScenariomapComponent;
  let fixture: ComponentFixture<ScenariomapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScenariomapComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ScenariomapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
