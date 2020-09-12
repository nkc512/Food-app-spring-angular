import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
const TEST_URL = environment.API_URL + '/test';

@Injectable({
  providedIn: 'root'
})
export class UsertestService {

  constructor(private http: HttpClient) { }

  getPublicContent(): Observable<any> {
    return this.http.get(TEST_URL + '/all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.http.get(TEST_URL + '/user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(TEST_URL + '/mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(TEST_URL + '/admin', { responseType: 'text' });
  }
}
