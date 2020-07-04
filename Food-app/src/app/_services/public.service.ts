import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Dish } from '../_classes/dish';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PublicService {

  startTime = '';
  closeTime = '';
  announcements = '';
  private publicUrl: string;

  constructor(private http: HttpClient) {
    this.publicUrl = 'http://localhost:8080/api/test/';
  }
  getAllDish(): Observable<Dish[]> {
    return this.http.get<Dish[]>(this.publicUrl);
  }
  getCafeteriaDishs(cafeteriaId: number): Observable<Dish[]> {
    return this.http.get<Dish[]>(this.publicUrl + cafeteriaId);
  }
  getCafeteriaDishFind(cafeteriaId: number, searchval: string): Observable<Dish[]> {
    return this.http.get<Dish[]>(this.publicUrl + cafeteriaId + '/' + searchval);
  }
  getDish( searchval: string): Observable<Dish[]> {
    return this.http.get<Dish[]>(this.publicUrl + searchval);
  }
}
