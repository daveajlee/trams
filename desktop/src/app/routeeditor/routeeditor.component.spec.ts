import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RouteeditorComponent } from './routeeditor.component';

describe('RouteeditorComponent', () => {
  let component: RouteeditorComponent;
  let fixture: ComponentFixture<RouteeditorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RouteeditorComponent]
    });
    fixture = TestBed.createComponent(RouteeditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
