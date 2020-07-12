import { Component, OnInit } from '@angular/core';
import { CartService } from '../../_services/cart.service'
import { CartItem } from 'src/app/_classes/cart-item';
import { Order } from 'src/app/_classes/order';
import{ TokenStorageService} from '../../_services/token-storage.service';
import { UserService } from 'src/app/_services/user.service';
@Component({
  selector: 'app-user-cart',
  templateUrl: './user-cart.component.html',
  styleUrls: ['./user-cart.component.css']
})
export class UserCartComponent implements OnInit {
  cartarray:CartItem[]=[];
  payable:any=0;
  showError: boolean;
  errmsg: string;
  showRestaurant: boolean;
  showsuccess: boolean;
  successmsg: string;
  showRecipt: boolean;
  recipt: Order;
  constructor(private cartservice: CartService, private tokenservice: TokenStorageService, private userService:UserService) {
    // this.payable=0;
    this.showRecipt=false;
   }
  
  ngOnInit(): void {
    // let that=this;
    this.payable=0;
    // console.log(this.payable);
    let that=this;
    this.cartservice.getCart().subscribe(function (data){
      if(data!=null){
      that.cartarray=data; 
      that.payable=0;
      that.cartarray.forEach(function (element) {
        that.payable=that.payable+(element.qnty*element.dish.price);
      });
      
      // console.log(that.payable);              
      }
    });
  }
  
  order(){
     var neworder=new Order();
     neworder.user_id=this.tokenservice.getUser().id;
     neworder.restaurantName=this.cartarray[0].dish.restaurantName;
     neworder.items=this.cartarray;
     neworder.payableAmount=this.payable;
     neworder.status="placed";
     console.log(neworder);
     this.userService.placeOrder(neworder).subscribe(res=>{
             console.log(res);
             console.log(res.createdAt);
             this.cartservice.updatecart([]);
             this.showsuccess=true;
             this.successmsg="Order is successfully placed with order id : "+res.order_id;
             setTimeout(() => {this.successmsg="";this.showsuccess = false;}, 5000);
             this.showRecipt=true;
             this.recipt=res;
     },
     err =>{
             console.log(err);
             this.showError=true;
             this.errmsg="Error Occured while placing order.";
             setTimeout(() => {this.errmsg="";this.showError = false;}, 5000);
    },
    ()=>{}
     )
  }


  deleteElementFormCart(i){
    // this.cartarray.splice(i, 1);
    this.cartservice.deleteElement(i);
    // this.cartarray=this.cartservice.getCart();
  }

  updateQnty(){
    console.log(this.cartarray);
    this.cartservice.updatecart(this.cartarray);
  }

}
