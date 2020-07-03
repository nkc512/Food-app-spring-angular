import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CafeteriaAddProductComponent } from './cafeteria-add-product.component';

describe('CafeteriaAddProductComponent', () => {
  let component: CafeteriaAddProductComponent;
  let fixture: ComponentFixture<CafeteriaAddProductComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CafeteriaAddProductComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CafeteriaAddProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
