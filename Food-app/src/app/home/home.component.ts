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

  constructor(private http: HttpClient, private publicservice: PublicService,
              private cartService: CartService, private tokenStorageService: TokenStorageService) {

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
      const x = (document.getElementById('quantity_' + i + j + k) as HTMLInputElement).value;
      console.log(dish, x);
      const cartObj = new CartItem();
      cartObj.dish = dish;
      cartObj.quantity = Number(x);
      let msg = this.cartService.addElementToCart(cartObj);
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
    else {
      this.showError = true;
      this.errmsg = 'Log In to add dish to the card';
      setTimeout(() => { this.errmsg = ''; this.showError = false; }, 5000);
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
