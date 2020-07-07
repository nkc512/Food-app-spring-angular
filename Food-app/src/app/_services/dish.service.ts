import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Dish } from '../_classes/dish';
import { HttpHeaders, HttpClient } from '@angular/common/http';
 
@Injectable({
  providedIn: 'root'
})
export class DishService {

  head: HttpHeaders;

  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {
    this.head = new HttpHeaders().set('access-control-allow-origin', this.baseUrl);
   }

  
  createDishReq(dishObj: Dish): Observable<Dish>{
    return this.http.post<Dish>(this.baseUrl + '/cafeteria/dishes/', dishObj);
  }

  getAllDishes(): Observable<Dish[]> {
    return this.http.get<Dish[]>(this.baseUrl + '/cafeteria/allDishes');
  }

  deletDish(dish:Dish):Observable<any>{
    return this.http.delete(this.baseUrl + '/cafeteria/dishes/' + dish.id)
  }

  updateDish(dish:Dish):Observable<any>{
    return this.http.put(this.baseUrl + '/cafeteria/dishes/' + dish.id, dish)
  }
}
