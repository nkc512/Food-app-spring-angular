
import {CartItem} from "./cart-item";
export class Order {
    order_id:string;
    user_id:string;
    restaurantName:string;
    items:CartItem[];
    payableAmount:number;
    status:string;
    createdAt:Date;
}
