import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminAddProductComponent } from './admin-add-product/admin-add-product.component';
import { AdminNotificationComponent } from './admin-notification/admin-notification.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminRoutingModule } from './admin-routing.module';



@NgModule({
  declarations: [AdminAddProductComponent, AdminNotificationComponent, AdminHomeComponent],
  imports: [
    CommonModule,
    AdminRoutingModule
  ]
})
export class AdminModule { }
