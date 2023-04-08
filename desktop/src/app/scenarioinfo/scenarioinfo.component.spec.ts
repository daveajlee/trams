import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScenarioinfoComponent } from './scenarioinfo.component';

describe('ScenarioinfoComponent', () => {
  let component: ScenarioinfoComponent;
  let fixture: ComponentFixture<ScenarioinfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScenarioinfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ScenarioinfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
