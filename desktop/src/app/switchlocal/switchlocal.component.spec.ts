import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SwitchlocalComponent } from './switchlocal.component';

describe('SwitchlocalComponent', () => {
  let component: SwitchlocalComponent;
  let fixture: ComponentFixture<SwitchlocalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SwitchlocalComponent]
    });
    fixture = TestBed.createComponent(SwitchlocalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
