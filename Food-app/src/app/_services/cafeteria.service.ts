import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Dish } from '../_classes/dish';
import { HttpClient } from '@angular/common/http';
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class CafeteriaService {
  private cafeteriaUrl: string;
  cafeteriaId: number;

  constructor(private http: HttpClient, private tokenStorageService: TokenStorageService) {
    this.cafeteriaUrl = 'http://localhost:8080/api/test/cafeteria/';
  }
  addDish(dish: Dish): Observable<Dish> {
    //console.log('add Dish service called', this.tokenStorageService.getUsername());

    return this.http.post<Dish>(this.cafeteriaUrl + 'add/' + this.tokenStorageService.getUsername(), dish);

  }

}
