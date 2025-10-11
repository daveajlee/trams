import { TestBed } from '@angular/core/testing';
import { App } from './app';

/**
 * Define tests for the start page.
 */
describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        App
      ],
    }).compileComponents();
  });

  /**
   * Test that the app can be created.
   */
  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  /**
   * Test that the app can render the start page correctly.
   */
  it('should render welcome to', () => {
    const fixture = TestBed.createComponent(App);
    fixture.detectChanges();
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Welcome to');
  });
});
