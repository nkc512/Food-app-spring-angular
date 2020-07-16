import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { Router } from '@angular/router';
import { UserService } from 'src/app/_services/user.service';
import { Order } from 'src/app/_classes/order';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {
  failAlertClose: boolean;
  errMsg: string;
  orders: Order[] = [];
  constructor(private tokenService: TokenStorageService, private userService: UserService, private router: Router) {
    if (!this.tokenService.getUserRole().includes('ROLE_USER')) {
      this.router.navigate(['/accessalert']);
    }
  }

  ngOnInit(): void {
    this.getAllOrders();
  }

  getAllOrders() {
    this.userService.getOrders().subscribe(data => {
      this.orders = data;
      setTimeout(() => {  }, 2000);

    },
    err => {
      console.log(err);
      this.errMsg = 'You have not ordered yet from our delicious dishes';
      setTimeout(() => { this.failAlertClose = false; this.errMsg = ''; }, 2000);
    },
    () => {
      console.log('getAllOrders response completed');
    });
  }

}
