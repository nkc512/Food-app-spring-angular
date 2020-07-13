import { Component, OnInit } from '@angular/core';
import { CartService } from '../../_services/cart.service';
import { CartItem } from 'src/app/_classes/cart-item';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { Router } from '@angular/router';
import { UserService } from 'src/app/_services/user.service';
import { Cart } from 'src/app/_classes/cart';
import { range } from 'rxjs';
import { CartwithDish } from 'src/app/_classes/cartwith-dish';
@Component({
  selector: 'app-user-cart',
  templateUrl: './user-cart.component.html',
  styleUrls: ['./user-cart.component.css']
})
export class UserCartComponent implements OnInit {
  cart: Cart;
  cartarray: CartItem[];
  cartEmpty = true;
  payable: number;
  val: string;
  errMsg = '';
  failAlertClose: boolean;
  successmsg: string;
  successAlertClosed: boolean;
  checkoutcartEmpty: boolean;
  checkoutcartitemarray: CartItem[] = [];
  checkoutpayable: number;
  checkoutcart: CartwithDish;

  constructor(private cartservice: CartService, private tokenService: TokenStorageService, private router: Router,
    private userService: UserService) {
    this.successAlertClosed = false;
    if (!this.tokenService.getUserRole().includes('ROLE_USER')) {
      this.router.navigate(['/accessalert']);
    }
    this.payable = 0;

  }

  ngOnInit(): void {
    this.payable = 0;
    this.cartservice.getCart().subscribe(data => {
      {
        this.cartarray = data;
      }
    });

    if (this.cartarray != null) {
      this.calculatePrice(this.cartarray);
      this.cartEmpty = false;
    }
    this.userService.getCart().subscribe(data => {
      this.checkoutcart = data;
    });
    if (this.checkoutcart != null) {
      this.checkoutcartEmpty = false;
      this.checkoutcartitemarray = this.checkoutcart.cartItems;
      this.checkoutpayable = this.checkoutcart.totalCost;
    }
    console.log('cartarray', this.cartarray);
    console.log('checkoutcart', this.checkoutcart);

  }
  calculatePrice(cartarray: CartItem[]) {
    this.payable = 0;
    let i;
    if (cartarray != null) {
      for (i = 0; i < cartarray.length; i++) {
        this.payable = this.payable + (cartarray[i].quantity * cartarray[i].dish.price);
      }
    }
  }



  deleteElementFormCart(i) {
    this.cartservice.deleteElement(i);
  }

  updateQuantity() {
    // console.log(this.cartarray);
    this.cartservice.updatecart(this.cartarray);
  }

  save() {
    this.cart = new Cart();

    /*
    for (let i = 0; i < this.cartarray.length; i++) {
      this.cart.cartItem.push(this.cartarray[i].dish.id);
    }*/
    this.cart.id = this.tokenService.getUsername();
    this.cart.cartItems = [];
    for (const cartitem of this.cartarray) {
      this.cart.cartItems.push({ id: cartitem.dish.id, quantity: cartitem.quantity });
    }
    console.log('cart in save function', this.cart);
    this.userService.saveCart(this.cart)
      .subscribe(
        res => {
          console.log(res);
          this.successmsg = 'Cart saved for transaction';
          this.successAlertClosed = true;
          this.userService.getCart().subscribe(data => {
            this.checkoutcart = data;
          });
          if (this.checkoutcart != null) {
            this.checkoutcartEmpty = false;
            this.checkoutcartitemarray = this.checkoutcart.cartItems;
            console.log(this.checkoutcartitemarray);
            this.checkoutpayable = this.checkoutcart.totalCost;
          }
          setTimeout(() => { this.successAlertClosed = false; this.successmsg = ''; }, 2000);
        },
        err => {
          console.log(err);
          this.errMsg = 'Could not save cart';
          this.failAlertClose = true;
          setTimeout(() => { this.failAlertClose = false; this.errMsg = ''; }, 2000);
        },
        () => {
          console.log('HTTP request completed.');
        }
      );
  }
  completeTransaction() {
    console.log('send the data for payment');
  }
}
