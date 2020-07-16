import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { Router } from '@angular/router';
import { Order } from 'src/app/_classes/order';
import { CafeteriaService } from 'src/app/_services/cafeteria.service';

@Component({
  selector: 'app-cafeteria-home',
  templateUrl: './cafeteria-home.component.html',
  styleUrls: ['./cafeteria-home.component.css']
})
export class CafeteriaHomeComponent implements OnInit {
  placeOrder: Order[] = [];
  acceptedOrder: Order[] = [];
  cookingOrder: Order[] = [];
  readyTodeliveryOrder: Order[] = [];

  constructor(private tokenService: TokenStorageService, private router: Router, private cafeteriaService: CafeteriaService) {
    if (!this.tokenService.getUserRole().includes('ROLE_CAFETERIAMANAGER')) {
      this.router.navigate(['/accessalert']);
    }


  }
  ngOnInit(): void {
    console.log('init ');
    this.cafeteriaService.getPlacedOrder(this.tokenService.getUser().username).subscribe(data => {
      // console.log(data);
      this.placeOrder = data;
      setTimeout(() => {  }, 2000);
      // console.log(this.placeOrder);
    }, err => { console.log(err); }, () => { });

    this.cafeteriaService.getAcceptedOrder(this.tokenService.getUser().username).subscribe(data => {
      this.acceptedOrder = data;
      // console.log(this.placeOrder);
    }, err => { console.log(err); }, () => { });

    this.cafeteriaService.getCookingOrder(this.tokenService.getUser().username).subscribe(data => {
      this.cookingOrder = data;
      // console.log(this.placeOrder);
    }, err => { console.log(err); }, () => { });

    this.cafeteriaService.getReadyOrder(this.tokenService.getUser().username).subscribe(data => {
      this.readyTodeliveryOrder = data;
      // console.log(this.placeOrder);
    }, err => { console.log(err); }, () => { });

    console.log('init end');
  }

  acceptOrder(order: Order, i: number) {
    this.cafeteriaService.changeorderStatus(order.order_id, 'Accepted')
      .subscribe(res => {
        console.log(res); this.acceptedOrder.unshift(res);
        this.placeOrder.splice(i, 1);
      },
        err => { console.log(err); }, () => { });
  }

  cookingOrderFunction(order: Order, i: number) {
    this.cafeteriaService.changeorderStatus(order.order_id, 'Cooking')
      .subscribe(res => {
        console.log(res); this.cookingOrder.unshift(res);
        this.acceptedOrder.splice(i, 1);
      },
        err => { console.log(err); }, () => { });
  }

  readyOrder(order: Order, i: number) {
    this.cafeteriaService.changeorderStatus(order.order_id, 'Ready')
      .subscribe(res => {
        console.log(res); this.readyTodeliveryOrder.unshift(res);
        this.cookingOrder.splice(i, 1);
      },
        err => { console.log(err); }, () => { });
  }

  completedOrder(order: Order, i: number) {
    this.cafeteriaService.changeorderStatus(order.order_id, 'Complete')
      .subscribe(res => { console.log(res); this.readyTodeliveryOrder.splice(i, 1); },
        err => { console.log(err); }, () => { });
  }

}
