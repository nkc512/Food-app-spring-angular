import { CartItem } from './cart-item';

export class CartwithDish {
    id: string;
    dateCreated: Date;
    status: string;
    totalCost: number;
    cartItems: CartItem[];
}
