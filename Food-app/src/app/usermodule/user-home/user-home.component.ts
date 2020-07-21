import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { Router } from '@angular/router';
import { UserService } from 'src/app/_services/user.service';
import { Order } from 'src/app/_classes/order';
import { Feedback } from 'src/app/_classes/feedback';
import { FormControl } from '@angular/forms';
import { NgForm } from '@angular/forms';
@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {
  failAlertClose: boolean;
  errmsg: string;
  successAlertClosed: boolean;
  successmsg: string;
  orders: Order[] = [];
  feedback: Feedback = new Feedback();

  constructor(private tokenService: TokenStorageService, private userService: UserService, private router: Router) {
    if (!this.tokenService.getUserRole().includes('ROLE_USER')) {
      this.router.navigate(['/accessalert']);
    }
  }

  ngOnInit(): void {
    this.getAllOrders();
    this.errmsg = '';
    this.successmsg = '';
    this.successAlertClosed = false;
    this.failAlertClose = false;
  }

  openFeedbackModal(id: string)
  {
    console.log(this.feedback, 'start');
    this.feedback.id = id;
    console.log(this.feedback, ' end');
  }
  getAllOrders() {
    this.userService.getOrders().subscribe(data => {
      this.orders = data;
      setTimeout(() => { }, 2000);

    },
      err => {
        console.log(err);
        this.errmsg = 'You have not ordered yet from our delicious dishes';
        setTimeout(() => { this.failAlertClose = false; this.errmsg = ''; }, 2000);
      },
      () => {
        console.log('getAllOrders response completed');
        this.orders = this.orders.reverse();
      });
  }
  getOrder(id) {
    console.log(id, 'getOrder called');
    this.router.navigate(['/user/cart/' + id]);
  }
  submitFeedback(feedback) {
    // console.log('Submit feedback called ', id);
    console.log('submit feedback', feedback);
    this.userService.saveFeedback(feedback).subscribe(data => {
      // console.log(data);
      this.successAlertClosed = true;
      this.successmsg = 'Feedback added successfully: ';
      setTimeout(() => { this.successmsg = ''; this.successAlertClosed = false; }, 2000);
    },
      err => {
        // console.log(err.error.message);
        this.failAlertClose = true;
        this.errmsg = err.error.message;
        setTimeout(() => { this.errmsg = ''; this.failAlertClose = false; }, 3000);
  },
  () => { });
  }
}
