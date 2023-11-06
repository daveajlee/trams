import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllocationslistComponent } from './allocationslist.component';

describe('AllocationslistComponent', () => {
  let component: AllocationslistComponent;
  let fixture: ComponentFixture<AllocationslistComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AllocationslistComponent]
    });
    fixture = TestBed.createComponent(AllocationslistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
