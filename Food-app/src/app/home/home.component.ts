import { Component, OnInit } from '@angular/core';
import { Dish } from '../_classes/dish';
import { Router } from '@angular/router';
import { PublicService } from '../_services/public.service';
import { Observable } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  // inputSearch: string;
  // dishes: Dish[];
  // constructor(private router: Router, private publicService: PublicService) { }

  // ngOnInit(): void {
  //   this.publicService.getAllDish().subscribe((dish: Dish[]) => {
  //     this.dishes = dish;
  //   });
  // }
  // searchFood()
  // {
  //   this.publicService.getDish(this.inputSearch);
  // }
  // addToCart(dishId)
  // {
  //   // TODO
  // }
  head : HttpHeaders;
  baseUrl : string= 'http://localhost:8080';
  restaurantArray: String[]=[];
  restaurantmsg: any;

  constructor(private http: HttpClient, private publicservice: PublicService) {
    
    this.head = new HttpHeaders().set('access-control-allow-origin', this.baseUrl);
   }

  disharray: Dish[]=[];

  categorySort: any;



  ngOnInit() {
    // this.categorySort = _.groupBy(this.disharray, function(dish) {
    //   return dish.category;
    // });
    // console.log(this.categorySort,this.categorySort[0])
    
    // this.callGetAllDishes();
    this.callGetDistinctRestaurant();
  
   


  }
   
  CatagorySort(){
    this.categorySort = this.disharray.reduce(function (r, a) {
      r[a.category] = r[a.category] || [];
      r[a.category].push(a);
      return r;
  }, Object.create(null));
  console.log(this.categorySort);

  }

  get key(){
    return Object.keys(this.categorySort);
  }

  getAllDishes(): Observable<Dish[]> {
    return this.http.get<Dish[]>(this.baseUrl + '/dishdata/allDishes', { headers: this.head });
  }

 

  callGetDistinctRestaurant(){
    this.publicservice.getDistinctRestaurant().subscribe(
    response => {
        console.log(response);
        this.restaurantArray=response;
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

  // callGetAllDishes(){
  // this.getAllDishes().subscribe(
  // response => {
  //     console.log(response);
  //     this.disharray=response;
  // },
  // err => {
  //   console.log(err);
  // },
  // () => {
  //   this.CatagorySort();
  // }
  // );
  // }


  CallGetRestaurantDishes(restaurant:String){
    this.publicservice.getRestaurantDishes(restaurant).subscribe(
      response => {
          console.log(response);
          this.disharray=response;
      },
      err => {
        console.log(err);
      },
      () => {
        this.CatagorySort();
      }
      );
  }

  restaurantTab(restaurant:any){
    console.log(restaurant);
    this.restaurantmsg=restaurant;
    this.CallGetRestaurantDishes(restaurant);
  }
}
