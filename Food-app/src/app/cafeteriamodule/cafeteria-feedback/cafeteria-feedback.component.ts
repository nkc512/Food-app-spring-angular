import { Component, OnInit } from '@angular/core';
import { Feedback } from 'src/app/_classes/feedback';
import { CafeteriaService } from 'src/app/_services/cafeteria.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cafeteria-feedback',
  templateUrl: './cafeteria-feedback.component.html',
  styleUrls: ['./cafeteria-feedback.component.css']
})
export class CafeteriaFeedbackComponent implements OnInit {
  feedbacks: Feedback[] = [];
  constructor(private tokenService: TokenStorageService, private cafeteriaService: CafeteriaService, private router: Router) {
    if (!this.tokenService.getUserRole().includes('ROLE_CAFETERIAMANAGER')) {
      this.router.navigate(['/accessalert']);
    }
  }

  ngOnInit(): void {
    this.getAllFeedbacks();
  }
  getAllFeedbacks() {
    this.cafeteriaService.getFeedbacks().subscribe(data => { this.feedbacks = data; }, err => {
      console.log(err);
    },
    () => {
      console.log('getAllFeedbacks response completed');
      this.feedbacks = this.feedbacks.reverse();
    });
  }
}
