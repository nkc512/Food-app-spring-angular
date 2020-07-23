import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserCartComponent } from './user-cart/user-cart.component';
import { UserHomeComponent } from './user-home/user-home.component';
import { UserRoutingModule } from './user-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { UserPaymentComponent } from './user-payment/user-payment.component';


@NgModule({
  declarations: [UserCartComponent, UserHomeComponent, UserPaymentComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    UserRoutingModule,
    FormsModule
  ]
})
export class UserModule { }
