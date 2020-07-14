import { CartItem } from './cart-item';
import { CartProduct } from './cart-product';

export class Cart {
    id: string;
    dateCreated: Date;
    status: string;
    totalPrice: number;
    cartItems: Array<CartProduct>;
}
