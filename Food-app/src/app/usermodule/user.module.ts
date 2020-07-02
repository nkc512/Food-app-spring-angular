import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserCartComponent } from './user-cart/user-cart.component';
import { UserHomeComponent } from './user-home/user-home.component';
import { UserRoutingModule } from './user-routing.module';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [UserCartComponent, UserHomeComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    UserRoutingModule
  ]
})
export class UserModule { }
