import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Credentials } from '../_classes/credentials';
import { User } from '../_classes/user';
import { environment } from '../../environments/environment'

const AUTH_API = environment.API_URL + '/auth';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(credentials: Credentials): Observable<any> {
    return this.http.post(AUTH_API + '/signin', {
      username: credentials.username,
      password: credentials.password
    }, httpOptions);
  }

  register(user: User): Observable<any> {
    return this.http.post(AUTH_API + '/signup', {
      username: user.username,
      email: user.email,
      password: user.password
    }, httpOptions);
  }

  forgotPassword(username){
    return this.http.get(AUTH_API + '/forgot-password/'+username, httpOptions);
  }
}
