import { CartItem } from './../../_classes/cart-item';
import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { Router } from '@angular/router';
import { UserService } from 'src/app/_services/user.service';
import { Payment} from 'src/app/_classes/payment';
import { Order } from 'src/app/_classes/order';
@Component({
  selector: 'app-user-payment',
  templateUrl: './user-payment.component.html',
  styleUrls: ['./user-payment.component.css']
})
export class UserPaymentComponent implements OnInit {

newPayment: Payment =new Payment();
prevPayment:Payment =new Payment();
errMsg:string;
failAlertClose: boolean;
orders: Order[] = [];
order: Order= new Order();
prevOrder:Order= new Order();


  constructor( private tokenService: TokenStorageService, private router: Router,
    private userService: UserService) {
      
    if (!this.tokenService.getUserRole().includes('ROLE_USER')) {
      this.router.navigate(['/accessalert']);
    }
  }
  ngOnInit(): void {
  }
makeNewPayment(payment:Payment){
//
    this.newPayment=payment;
    this.newPayment.orderId=this.userService.OrderIdData;
    this.newPayment.restaurantName=this.userService.RestNameData;
    console.log(payment.orderId);
    this.newPayment.amount=this.userService.getAmountData();
    this.userService.makePayment(this.newPayment).subscribe(data => {
      this.newPayment=data;
      console.log(this.newPayment);
      console.log(data);
      console.log(data.orderId);
    //return this.newPayment;
    });

    this.userService.getOrders().subscribe(data =>{
      this.orders=data;
      console.log(this.orders);
      this.orders.forEach(element => {
        if (element.order_id==this.newPayment.orderId)
        {
          this.order=element;
          console.log(this.order);
        }
    }); 
    });


  }

  getPaymentData(){
    this.prevPayment=new Payment();
    this.userService.getPreviousPayment().subscribe((data )=> {
      //setTimeout(() => {  }, 2000);
      this.prevPayment=data;
      console.log(this.prevPayment);
      this.userService.getOrders().subscribe(res =>{
        this.orders=res;
        console.log(this.orders);
        this.orders.forEach(element => {
          console.log(this.prevPayment.orderId);
          if (element.order_id==this.prevPayment.orderId)
          {
            this.prevOrder=element;
            console.log(this.prevOrder);
          }
      
      });
    });   
  });

}

}
