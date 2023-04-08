import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';

/**
 * Define tests for the start page.
 */
describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        AppComponent
      ],
    }).compileComponents();
  });

  /**
   * Test that the app can be created.
   */
  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  /**
   * Test that the app has the correct title.
   */
  it(`should have as title 'trams-frontend'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('trams-frontend');
  });

  /**
   * Test that the app can render the start page correctly.
   */
  it('should render welcome to', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Welcome to');
  });
});
