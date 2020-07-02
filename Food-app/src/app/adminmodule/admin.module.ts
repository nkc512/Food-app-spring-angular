import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminAddProductComponent } from './admin-add-product/admin-add-product.component';
import { AdminNotificationComponent } from './admin-notification/admin-notification.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminRoutingModule } from './admin-routing.module';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [AdminAddProductComponent, AdminNotificationComponent, AdminHomeComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AdminRoutingModule
  ]
})
export class AdminModule { }
