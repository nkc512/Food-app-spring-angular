import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cart } from '../_classes/cart';
import { TokenStorageService } from './token-storage.service';
import { CartwithDish } from '../_classes/cartwith-dish';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable()
export class UserService {

    private usersUrl: string;
    private head: HttpHeaders;
    constructor(private http: HttpClient, private tokenService: TokenStorageService) {
        this.usersUrl = 'http://localhost:8080/user/';
        const token = this.tokenService.getToken();
        if (token != null) {
            console.log(token);
        }
        else {
            console.log('no token found');
        }
        this.head = new HttpHeaders().set('access-control-allow-origin', this.usersUrl);
    }

    public saveCart(cart: Cart): Observable<any> {
        return this.http.post<Cart>(this.usersUrl + 'createcart/', cart,
            { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
    }
    public getCart(): Observable<CartwithDish> {
        console.log('getcart');
        return this.http.get<CartwithDish>(this.usersUrl + 'getcart/',
            { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
    }
}
