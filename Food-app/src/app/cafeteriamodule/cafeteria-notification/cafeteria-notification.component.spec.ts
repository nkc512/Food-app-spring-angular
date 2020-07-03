import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CafeteriaNotificationComponent } from './cafeteria-notification.component';

describe('CafeteriaNotificationComponent', () => {
  let component: CafeteriaNotificationComponent;
  let fixture: ComponentFixture<CafeteriaNotificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CafeteriaNotificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CafeteriaNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
