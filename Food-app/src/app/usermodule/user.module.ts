import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserCartComponent } from './user-cart/user-cart.component';
import { UserHomeComponent } from './user-home/user-home.component';
import { UserRoutingModule } from './user-routing.module';



@NgModule({
  declarations: [UserCartComponent, UserHomeComponent],
  imports: [
    CommonModule,
    UserRoutingModule
  ]
})
export class UserModule { }
