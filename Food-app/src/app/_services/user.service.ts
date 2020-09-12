import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from '../_classes/order';
import { Payment } from '../_classes/payment';
import { Cart } from '../_classes/cart';
import { TokenStorageService } from './token-storage.service';
import { CartwithDish } from '../_classes/cartwith-dish';
import { Feedback } from '../_classes/feedback';
import { environment } from '../../environments/environment'
const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable()
export class UserService {


    private usersUrl: string;
    private head: HttpHeaders;
    private orderIdData:string;
    private amountData:number;
    private restNameData:string;

    constructor(private http: HttpClient, private tokenService: TokenStorageService) {
        this.usersUrl = environment.API_URL + '/user';
        const token = this.tokenService.getToken();
        if (token != null) {
            console.log(token);
        }
        else {
            console.log('no token found');
        }
        this.head = new HttpHeaders().set('access-control-allow-origin', this.usersUrl);
    }
    placeOrder(orderdata: Order): Observable<Order> {
        console.log('reach Placeorder');
        let val = this.http.post<Order>(this.usersUrl + '/order', orderdata);
        console.log('placeorder userservice', val);
        return val;
    }
    public saveCart(cart: Cart): Observable<any> {
        return this.http.post<Cart>(this.usersUrl + '/createcart', cart,
            { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
    }
    public cancelCart(): Observable<any> {

        return this.http.delete(this.usersUrl + '/deletecart',
            { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
    }
    public getCart(): Observable<CartwithDish> {
        console.log('getcart');
        return this.http.get<CartwithDish>(this.usersUrl + '/getcart',
            { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
    }

    public getOrders(): Observable<any> {
        return this.http.get<any>(this.usersUrl + '/getallorders',
            { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
    }
    public getOrder(id: string): Observable<any> {
        return this.http.get<any>(this.usersUrl + '/getorder/' + id,
            { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
    }
    public getFeedback(id: string): Observable<any> {
        return this.http.get<any>(this.usersUrl + '/getfeedback/' + id,
            { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
    }
    public saveFeedback(feedback: Feedback): Observable<any> {
        feedback.username = this.tokenService.getUsername();
        console.log('user service savefeedback', feedback);
        return this.http.post<any>(this.usersUrl + '/savefeedback', feedback,
        { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
    }
    makePayment(paymentdata: Payment): Observable<Payment> {
        console.log('reach makePayment',paymentdata);
        
        return this.http.post<Payment>(this.usersUrl + '/makePayment', paymentdata);
        
    }

    getPreviousPayment(): Observable<Payment>{
        return this.http.get<Payment>(this.usersUrl + '/getPreviousPayment',
        { headers: this.head.append('Authorization', 'Bearer ' + this.tokenService.getToken()) });
    }
    get OrderIdData():string{
        return this.orderIdData;
    }

    set OrderIdData(orderIdData:string){
        this.orderIdData=orderIdData;
    }
    get RestNameData():string{
        return this.restNameData;
    }

    set RestNameData(restNameData:string){
        this.restNameData=restNameData;
    }

    getAmountData(){
        let temp=this.amountData;
        return temp;
    }

    setAmountData(amountData:number){
        this.amountData=amountData;
    }
}
