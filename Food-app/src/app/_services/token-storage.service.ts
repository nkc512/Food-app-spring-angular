import { Injectable } from '@angular/core';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  USER_ID: number;
  USER_USERNAME: string;
  USER_ROLES: string[];

  constructor() { }

  signOut() {
    this.USER_ID = 0;
    this.USER_USERNAME = null;
    this.USER_ROLES = [];
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

    //console.log(user);
  }

  public getUser() {
    const jsonparse = JSON.parse(sessionStorage.getItem(USER_KEY));
    this.USER_ID = jsonparse.id;
    this.USER_USERNAME = jsonparse.username;
    this.USER_ROLES = jsonparse.roles;
    return jsonparse;
  }
  public getUserId(): number {
    return this.USER_ID;
  }
  public getUsername(): string {
    return this.USER_USERNAME;
  }
  public getUserRole(): string[] {
    return this.USER_ROLES;
  }
}
