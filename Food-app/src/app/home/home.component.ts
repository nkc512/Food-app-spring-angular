import { Component, OnInit } from '@angular/core';
import { Dish } from '../_classes/dish';
import { Router } from '@angular/router';
import { PublicService } from '../_services/public.service';
import { Observable } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { CartItem } from '../_classes/cart-item';
import { CartService } from '../_services/cart.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  restaurantselected: string;
  restaurantArray: string[] = [];
  restaurantmsg: any;
  searchval: string;
  showsuccess: boolean;
  showError: boolean;
  errmsg: string;
  successmsg: string;
  isLoggedIn: boolean;
  cartarray: CartItem[] = [];
  flag = true;
  head: any;
  searchdishArray: Dish[] = [];
  baseUrl = 'http://localhost:8080';
  searchForm = new FormGroup({
    restaurantControl: new FormControl(''),
    searchdata: new FormControl(''),
  });
  countries = ['USA', 'Canada', 'Uk']
  constructor(private http: HttpClient, private publicservice: PublicService,
    private cartService: CartService, private tokenStorageService: TokenStorageService) {
    this.head = new HttpHeaders().set('access-control-allow-origin', this.baseUrl);
    this.showError = false;
    this.showsuccess = false;
    this.errmsg = '';
    this.successmsg = '';
    this.searchval = '';
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

  addCart(dish: Dish, j, k) {

    if (this.isLoggedIn) {
      this.checkSameRestaurant(dish.restaurantName);
      console.log(this.flag);
      if (!this.flag) {
        this.showError = true;
        this.errmsg = 'Cart should contain dishes from same restaurant.';
        setTimeout(() => { this.errmsg = ''; this.showError = false; }, 1500);
      }
      else {
        const x = (document.getElementById('quantity_' + j + k) as HTMLInputElement).value;
        console.log(dish, x);
        const cartObj = new CartItem();
        cartObj.dish = dish;
        cartObj.quantity = Number(x);
        const msg = this.cartService.addElementToCart(cartObj);
        console.log(msg);
        if (msg == 'Added to cart Successfully') {
          this.showsuccess = true;
          this.successmsg = 'Dish: ' + dish.dishName + ' Quantity: ' + x + ' is added to cart successfully.';
          setTimeout(() => { this.successmsg = ''; this.showsuccess = false; }, 1500);
        }
        else {
          console.log(msg);
          this.showError = true;
          this.errmsg = msg;
          setTimeout(() => { this.errmsg = ''; this.showError = false; }, 1500);
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
  selectChangeHandler(event: any) {
    this.restaurantselected = event.target.value;
  }
  searchfunction() {
    this.searchval = this.searchForm.get('searchdata').value;
    if (this.searchForm.get('searchdata').value.length === 0) {
      console.log('no search value found');
    }
    else {
      if (typeof (this.restaurantselected) === 'undefined' || this.restaurantselected.length === 0 || this.restaurantselected === 'all') {
        this.publicservice.getDishAllRestaurant(this.searchval).subscribe(data => {
          this.searchdishArray = data;
        },
          err => {
            console.log('No dishes found with your query');
          },
          () => {
            console.log('no restaurant search finished');
            setTimeout(() => {  }, 2000);
          });
      }
      else {
        this.publicservice.getDishSelectedRestaurant(this.searchval, this.restaurantselected).subscribe(data => {
          this.searchdishArray = data;
        },
          err => {
            console.log('No dishes found with your query');
          },
          () => {
            console.log('search finished');
            setTimeout(() => {  }, 2000);
          });
      }
    }
    console.log(this.restaurantselected, this.searchForm.get('searchdata').value);
    console.log('search called');
  }
}
