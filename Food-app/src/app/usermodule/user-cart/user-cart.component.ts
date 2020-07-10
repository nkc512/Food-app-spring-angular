import { Component, OnInit } from '@angular/core';
import { CartService } from '../../_services/cart.service'
import { CartItem } from 'src/app/_classes/cart-item';
@Component({
  selector: 'app-user-cart',
  templateUrl: './user-cart.component.html',
  styleUrls: ['./user-cart.component.css']
})
export class UserCartComponent implements OnInit {
  cartarray:CartItem[]=[];
  payable:any=0;
  constructor(private cartservice: CartService) {
    // this.payable=0;
   }
  
  ngOnInit(): void {
    // let that=this;
    this.payable=0;
    console.log(this.payable);
    let that=this;
    this.cartservice.getCart().subscribe(function (data){
      if(data!=null){
      that.cartarray=data; 
      that.cartarray.forEach(function (element) {
        that.payable=that.payable+(element.qnty*element.dish.price);
      });
      
      console.log(that.payable);              
      }
    });
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
