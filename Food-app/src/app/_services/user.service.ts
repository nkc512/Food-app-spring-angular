import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from '../_classes/order';

@Injectable()
export class UserService {

    private usersUrl: string;

    constructor(private http: HttpClient) {
        this.usersUrl = 'http://localhost:8080/api/user';
    }

    placeOrder(orderdata: Order): Observable<Order> {
        return this.http.post<Order>(this.usersUrl + '/order' , orderdata);
    }
    /*
    public getUser1(Id: string): User { // For userDummy data
        return this.userDummy.find(user => {
            return user.id == Id;
        });
    }
    public getUser(Id: string): Observable<User> {
        return this.http.get<User>(this.usersUrl + '/' + Id);
    }
    public findAll(): Observable<User[]> {
        console.log('reach findAll');
        console.log('findall', this.http.get<User[]>(this.usersUrl));
        return this.http.get<User[]>(this.usersUrl);
    }

    public save(user: User) {
        console.log('reach save');
        return this.http.post<User>(this.usersaddUrl, user);
    }
    */
}
