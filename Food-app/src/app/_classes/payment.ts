export class Payment {
    id:number;
    orderId:string;
    amount:number;
    cardOwner:string;
    cardNumber:string;
    expirationDate:string;
    cvv:number;
    restaurantName:string;
    paidAt:Date;
}