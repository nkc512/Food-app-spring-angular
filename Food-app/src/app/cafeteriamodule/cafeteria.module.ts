import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CafeteriaHomeComponent } from './cafeteria-home/cafeteria-home.component';
import { CafeteriaNotificationComponent } from './cafeteria-notification/cafeteria-notification.component';
import { CafeteriaAddProductComponent } from './cafeteria-add-product/cafeteria-add-product.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [CafeteriaHomeComponent, CafeteriaNotificationComponent, CafeteriaAddProductComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ]
})
export class CafeteriaModule { }
