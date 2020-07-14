import { Component, OnInit } from '@angular/core';
import { Dish } from '../_classes/dish';
import { Router } from '@angular/router';
import { PublicService } from '../_services/public.service';
import { Observable } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { CartItem } from '../_classes/cart-item';
import { CartService } from '../_services/cart.service';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  restaurantArray: string[] = [];
  restaurantmsg: any;

  showsuccess: boolean;
  showError: boolean;
  errmsg: string;
  successmsg: string;
  isLoggedIn: boolean;
  cartarray: CartItem[] = [];
  flag = true;
  head: any;
  baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient, private publicservice: PublicService,
              private cartService: CartService, private tokenStorageService: TokenStorageService) {
    this.head = new HttpHeaders().set('access-control-allow-origin', this.baseUrl);
    this.showError = false;
    this.showsuccess = false;
    this.errmsg = '';
    this.successmsg = '';
  }

  disharray: Dish[] = [];

  categorySort: any;



  ngOnInit() {

    this.callGetDistinctRestaurant();
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    const that = this;
    this.cartService.getCart().subscribe(function (data) {
      if (data != null) {
        that.cartarray = data;
      }
    });


  }

  categorySortFunction() {
    this.categorySort = this.disharray.reduce(function (r, a) {
      r[a.category] = r[a.category] || [];
      r[a.category].push(a);
      return r;
    }, Object.create(null));
    console.log(this.categorySort);

  }

  get key() {
    return Object.keys(this.categorySort);
  }

  callGetDistinctRestaurant() {
    this.publicservice.getDistinctRestaurant().subscribe(
      response => {
        console.log(response);
        this.restaurantArray = response;
        this.restaurantTab(this.restaurantArray[0]);
        // this.restaurantmsg=this.restaurantArray[0];
      },
      err => {
        console.log(err);
      },
      () => {
        // this.CatagorySort();
      }
    );
  }

  addCart(dish: Dish, i, j, k) {

    if (this.isLoggedIn) {
      this.checkSameRestaurant(dish.restaurantName);
      console.log(this.flag);
      if (!this.flag) {
        this.showError = true;
        this.errmsg = 'Cart should contain dishes from same restaurant.';
        setTimeout(() => { this.errmsg = ''; this.showError = false; }, 5000);
      }
      else {
        const x = (document.getElementById('quantity_' + i + j + k) as HTMLInputElement).value;
        console.log(dish, x);
        const cartObj = new CartItem();
        cartObj.dish = dish;
        cartObj.quantity = Number(x);
        const msg = this.cartService.addElementToCart(cartObj);
        console.log(msg);
        if (msg == 'Added to cart Successfully') {
          this.showsuccess = true;
          this.successmsg = 'Dish: ' + dish.dishName + ' Quantity: ' + x + ' is added to cart successfully.';
          setTimeout(() => { this.successmsg = ''; this.showsuccess = false; }, 5000);
        }
        else {
          console.log(msg);
          this.showError = true;
          this.errmsg = msg;
          setTimeout(() => { this.errmsg = ''; this.showError = false; }, 5000);
        }
      }
    }
    else {
      this.showError = true;
      this.errmsg = 'Log In to add dish to the cart';
      setTimeout(() => { this.errmsg = ''; this.showError = false; }, 5000);
    }
  }
  checkSameRestaurant(name) {
    this.flag = true;
    if (this.cartarray.length > 0) {
      console.log('check is called', this.cartarray[0].dish.restaurantName);
      const rest1 = this.cartarray[0].dish.restaurantName;
      if (name != rest1) {
        this.flag = false;
      }
    }
  }
  callGetRestaurantDishes(restaurant: string) {
    this.publicservice.getRestaurantDishes(restaurant).subscribe(
      response => {
        console.log(response);
        this.disharray = response;
      },
      err => {
        console.log(err);
      },
      () => {
        this.categorySortFunction();
      }
    );
  }

  restaurantTab(restaurant: any) {
    console.log(restaurant);
    this.restaurantmsg = restaurant;
    this.callGetRestaurantDishes(restaurant);
  }
}
