import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Dish } from '../_classes/dish';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TokenStorageService } from './token-storage.service';
import { Order } from '../_classes/order';
import { Feedback } from '../_classes/feedback';
import { environment } from '../../environments/environment'
@Injectable({
  providedIn: 'root'
})
export class CafeteriaService {
  private cafeteriaUrl: string;
  private cafeteriaId: number;
  private head: HttpHeaders;

  constructor(private http: HttpClient, private tokenService: TokenStorageService) {
    this.cafeteriaUrl = environment.API_URL + '/cafeteria';
    const token = this.tokenService.getToken();
    if (token != null) {
        console.log(token);
    }
    else {
        console.log('no token found');
    }
    this.head = new HttpHeaders().set('access-control-allow-origin', this.cafeteriaUrl);

  }
  addDish(dish: Dish): Observable<Dish> {

    return this.http.post<Dish>(this.cafeteriaUrl + '/add/' + this.tokenService.getUsername(), dish,
    { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });

  }
  getPlacedOrder(restaurantname: string): Observable<Order[]> {
    return this.http.get<Order[]>(this.cafeteriaUrl + '/restWisePlacedOrder/' + restaurantname,
    { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
  }

  getAcceptedOrder(restaurantname: string): Observable<Order[]> {
    return this.http.get<Order[]>(this.cafeteriaUrl + '/restWiseAcceptedOrder/' + restaurantname,
    { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
  }

  getCookingOrder(restaurantname: string): Observable<Order[]> {
    return this.http.get<Order[]>(this.cafeteriaUrl + '/restWiseCookingOrder/' + restaurantname,
    { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
  }

  getReadyOrder(restaurantname: string): Observable<Order[]> {
    return this.http.get<Order[]>(this.cafeteriaUrl + '/restWiseReadyOrder/' + restaurantname,
    { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
  }

  changeorderStatus(Id: string, status: string): Observable<Order> {
    return this.http.put<Order>(this.cafeteriaUrl + '/changeStatus/' + Id, status,
    { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
  }
  getFeedbacks(): Observable<Feedback[]>
  {
    return this.http.get<Feedback[]>(this.cafeteriaUrl + '/getfeedback',
    { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
  }
}
