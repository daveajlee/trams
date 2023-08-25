import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoutecreatorComponent } from './routecreator.component';

describe('RoutecreatorComponent', () => {
  let component: RoutecreatorComponent;
  let fixture: ComponentFixture<RoutecreatorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RoutecreatorComponent]
    });
    fixture = TestBed.createComponent(RoutecreatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
