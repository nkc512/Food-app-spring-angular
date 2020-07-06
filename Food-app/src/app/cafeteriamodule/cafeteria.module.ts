import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CafeteriaHomeComponent } from './cafeteria-home/cafeteria-home.component';
import { CafeteriaNotificationComponent } from './cafeteria-notification/cafeteria-notification.component';
import { CafeteriaAddProductComponent } from './cafeteria-add-product/cafeteria-add-product.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CafeteriaRoutingModule } from './cafeteria-routing.module';
import { CafeteriaProfileComponent } from './cafeteria-profile/cafeteria-profile.component';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [CafeteriaHomeComponent, CafeteriaNotificationComponent, CafeteriaAddProductComponent, CafeteriaProfileComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CafeteriaRoutingModule,
    FormsModule
  ],
  exports:[
    FormsModule
  ]
})
export class CafeteriaModule { }
