import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CafeteriaFeedbackComponent } from './cafeteria-feedback.component';

describe('CafeteriaFeedbackComponent', () => {
  let component: CafeteriaFeedbackComponent;
  let fixture: ComponentFixture<CafeteriaFeedbackComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CafeteriaFeedbackComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CafeteriaFeedbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
