import { Component, OnInit } from '@angular/core';
import { CartService } from '../../_services/cart.service'
import { CartItem } from 'src/app/_classes/cart-item';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-user-cart',
  templateUrl: './user-cart.component.html',
  styleUrls: ['./user-cart.component.css']
})
export class UserCartComponent implements OnInit {
  cartarray: CartItem[] = [];
  payable: any = 0;

  constructor(private cartservice: CartService, private tokenService: TokenStorageService, private router: Router) {
    if (!this.tokenService.getUserRole().includes('ROLE_USER')) {
      this.router.navigate(['/accessalert']);
    }
  }

  ngOnInit(): void {
    this.payable = 0;
    console.log(this.payable);
    this.cartservice.getCart().subscribe(function (data) {
      if (data != null) {
        this.cartarray = data;
        this.cartarray.forEach(function (element) {
          this.payable = this.payable + (element.qnty * element.dish.price);
        });

        console.log(this.payable);
      }
    });
  }


  deleteElementFormCart(i) {
    this.cartservice.deleteElement(i);
  }

  updateQnty() {
    console.log(this.cartarray);
    this.cartservice.updatecart(this.cartarray);
  }

}
