import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Dish } from '../_classes/dish';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { TokenStorageService } from './token-storage.service';
import { environment } from '../../environments/environment'
@Injectable({
  providedIn: 'root'
})

export class DishService {

  head: HttpHeaders;

  private baseUrl = environment.API_URL;

  constructor(private http: HttpClient, private tokenservice: TokenStorageService) {
    const token = this.tokenservice.getToken();
    if (token != null) {
      console.log(token);
    }
    else {
      console.log('no token found');
    }
    this.head = new HttpHeaders().set('access-control-allow-origin', this.baseUrl);
  }


  createDishReq(dishObj: Dish): Observable<Dish> {
    //console.log('createDishReq', this.baseUrl + '/cafeteria/dishes/', dishObj,
    //  { headers: this.head.append('Authorization', 'Bearer ' + this.tokenservice.getToken()) });

    return this.http.post<Dish>(this.baseUrl + '/cafeteria/dishes', dishObj,
      { headers: this.head.append('Authorization', 'Bearer ' + this.tokenservice.getToken()) });
  }

  getDishByRestaurant(restaurantname: string): Observable<Dish[]> {
    return this.http.get<Dish[]>(this.baseUrl + '/cafeteria/getRestaurantDishes/' + restaurantname);
  }

  getAllDishes(): Observable<Dish[]> {

    return this.http.get<Dish[]>(this.baseUrl + '/cafeteria/allDishes');
  }

  deletDish(dish: Dish): Observable<any> {
    return this.http.delete(this.baseUrl + '/cafeteria/dishes/' + dish.id,
      { headers: this.head.append('Authorization', 'Bearer ' + this.tokenservice.getToken()) });
  }

  updateDish(dish: Dish): Observable<any> {
    return this.http.put(this.baseUrl + '/cafeteria/dishes/' + dish.id, dish,
      { headers: this.head.append('Authorization', 'Bearer ' + this.tokenservice.getToken()) });
  }
}
