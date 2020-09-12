import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Dish } from '../_classes/dish';
import { HttpClient } from '@angular/common/http';
import { ContactUs } from '../_classes/contact-us';
import { environment } from '../../environments/environment'
@Injectable({
  providedIn: 'root'
})
export class PublicService {

  startTime = '';
  closeTime = '';
  announcements = '';
  private baseUrl: string;

  constructor(private http: HttpClient) {
    this.baseUrl = environment.API_URL + '/test';
  }
  // getAllDish(): Observable<Dish[]> {
  //   return this.http.get<Dish[]>(this.publicUrl);
  // }
  // getCafeteriaDishs(cafeteriaId: number): Observable<Dish[]> {
  //   return this.http.get<Dish[]>(this.publicUrl + cafeteriaId);
  // }
  // getCafeteriaDishFind(cafeteriaId: number, searchval: string): Observable<Dish[]> {
  //   return this.http.get<Dish[]>(this.publicUrl + cafeteriaId + '/' + searchval);
  // }
  // getDish( searchval: string): Observable<Dish[]> {
  //   return this.http.get<Dish[]>(this.publicUrl + searchval);
  // }

  getDistinctRestaurant(): Observable<string[]> {
    return this.http.get<string[]>(this.baseUrl + '/allRestaurants');
  }


  getRestaurantDishes(restaurant: string): Observable<Dish[]> {
    return this.http.get<Dish[]>(this.baseUrl + '/getRestaurantDishes/' + restaurant);
  }

  getDishAllRestaurant(searchval: string): Observable<Dish[]> {
    return this.http.get<Dish[]>(this.baseUrl + '/getDishes/' + searchval);
  }
  getDishSelectedRestaurant(searchval: string, restaurant: string): Observable<Dish[]>
  {
    return this.http.get<Dish[]>(this.baseUrl + '/getDishes/' + searchval + '/restaurant/' + restaurant);
  }
  postContactUs(contactUs: ContactUs): Observable<any>
  {
    return this.http.post(this.baseUrl + '/contactUs',contactUs);
  }
}
