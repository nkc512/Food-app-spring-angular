import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CafeteriaHomeComponent } from './cafeteria-home.component';

describe('CafeteriaHomeComponent', () => {
  let component: CafeteriaHomeComponent;
  let fixture: ComponentFixture<CafeteriaHomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CafeteriaHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CafeteriaHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
