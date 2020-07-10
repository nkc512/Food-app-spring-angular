import { Injectable } from '@angular/core';
import { CartItem } from '../_classes/cart-item';
import { BehaviorSubject, Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cart: BehaviorSubject<CartItem[]> = new BehaviorSubject(null);
  dumycart: Array<CartItem> = [];
  constructor() {
    this.getCart().subscribe(
      data => {
        if (data != null) {
          this.dumycart = data;
        }
      }
    );
  }
  addElementToCart(obj: CartItem) {
    if (obj.qnty != 0) {
      // var cartitemarray: CartItem[]=[];
      this.dumycart.push(obj);
      this.cart.next(this.dumycart);
      return 'Added to cart Successfully';
    }
    else {
      return 'Select non zero qunatity';
    }
  }
  getCart(): Observable<CartItem[]> {
    return this.cart.asObservable();
  }
  deleteElement(i) {
    // this.cart.splice(i, 1);
    // var newarray = this.cart.filter(songs => songs.id !== id);
    this.dumycart.splice(i, 1);
    this.cart.next(this.dumycart);
  }
  updatecart(obj: CartItem[]) {
    this.cart.next(obj);
  }
}
