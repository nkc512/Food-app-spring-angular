import { Component, OnInit } from '@angular/core';
import { CartService } from '../../_services/cart.service';
import { CartItem } from 'src/app/_classes/cart-item';
import { Order } from 'src/app/_classes/order';
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
  cartarray: CartItem[] = [];
  payable = 0;
  failAlertClose: boolean;
  errmsg: string;
  showRestaurant: boolean;
  successAlertClosed: boolean;
  successmsg: string;
  showRecipt: boolean;
  recipt: Order;
  cart: Cart;
  cartEmpty = true;
  val: string;
  errMsg = '';
  checkoutcartEmpty: boolean;
  checkoutcartitemarray: CartItem[] = [];
  checkoutpayable: number;
  checkoutcart: CartwithDish;

  constructor(private cartservice: CartService, private tokenService: TokenStorageService, private router: Router,
    private userService: UserService) {
    this.showRecipt = false;
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
    this.fetchPreviousCartData();


    console.log('cartarray', this.cartarray);
    console.log('checkoutcart', this.checkoutcart);

  }
  fetchPreviousCartData() {
    this.userService.getCart().subscribe(data => {
      this.checkoutcart = data;
      this.checkoutcartitemarray = this.checkoutcart.cartItems;
      this.checkoutcartEmpty = false;
      this.checkoutpayable = this.checkoutcart.totalCost;

    },
      err => {
        this.checkoutcartEmpty = true;
        console.log(err);
      },
      () => {
      });
  }


  order() {
    const neworder = new Order();
    neworder.user_id = this.tokenService.getUser().id;
    neworder.restaurantName = this.checkoutcartitemarray[0].dish.restaurantName;
    neworder.items = this.checkoutcartitemarray;
    neworder.payableAmount = this.checkoutpayable;
    neworder.status = 'Placed';
    console.log(neworder);
    this.userService.placeOrder(neworder).subscribe(res => {
      console.log(res);
      console.log(res.createdAt);
      this.cartservice.updatecart([]);
      this.successAlertClosed = true;
      this.successmsg = 'Order is successfully placed with order id : ' + res.order_id;
      setTimeout(() => { this.successmsg = ''; this.successAlertClosed = false; }, 5000);
      this.showRecipt = true;
      this.recipt = res;
    },
      err => {
        console.log(err);
        this.failAlertClose = true;
        this.errmsg = 'Error Occured while placing order.';
        setTimeout(() => { this.errmsg = ''; this.failAlertClose = false; }, 5000);
      },
      () => { }
    );
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
    if (this.cartarray.length === 1) {
      this.payable = 0;
      this.cartEmpty = true;
    }
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
          this.fetchPreviousCartData();
          this.cartarray = [];
          this.cartEmpty = true;
          this.payable = 0;
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
  cancelCart() {
    this.userService.cancelCart()
      .subscribe(
        res => {
          console.log(res);
          this.successmsg = 'Cart deleted successfully';
          this.successAlertClosed = true;
          this.userService.getCart().subscribe(data => {
            this.checkoutcart = data;
          });
          setTimeout(() => { this.successAlertClosed = false; this.successmsg = ''; }, 2000);
          window.location.reload();
        },
        err => {
          console.log(err);
          this.errMsg = 'Could not delete cart';
          this.failAlertClose = true;
          setTimeout(() => { this.failAlertClose = false; this.errMsg = ''; }, 2000);
        },
        () => {
        }
      );
  }

  completeTransaction() {
    console.log('send the data for payment');
  }
}
