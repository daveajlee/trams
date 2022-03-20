import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScenariolistComponent } from './scenariolist.component';

describe('ScenariolistComponent', () => {
  let component: ScenariolistComponent;
  let fixture: ComponentFixture<ScenariolistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScenariolistComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ScenariolistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
