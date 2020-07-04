import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CafeteriaProfileComponent } from './cafeteria-profile.component';

describe('CafeteriaProfileComponent', () => {
  let component: CafeteriaProfileComponent;
  let fixture: ComponentFixture<CafeteriaProfileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CafeteriaProfileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CafeteriaProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
