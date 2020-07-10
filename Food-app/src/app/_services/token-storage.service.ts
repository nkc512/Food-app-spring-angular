import { Injectable } from '@angular/core';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  USER_ID: number;
  USER_USERNAME: string;

  constructor() { }

  signOut() {
    window.sessionStorage.clear();
  }

  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user) {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
    console.log(user);
  }

  public getUser() {
    const jsonparse = JSON.parse(sessionStorage.getItem(USER_KEY));
    this.USER_ID = jsonparse.id;
    this.USER_USERNAME = jsonparse.username;
    return jsonparse;
  }
  public getUserId(): number {
    return this.USER_ID;
  }
  public getUsername(): string {
    return this.USER_USERNAME;
  }
}
