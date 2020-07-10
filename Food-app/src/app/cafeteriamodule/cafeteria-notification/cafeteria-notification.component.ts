import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cafeteria-notification',
  templateUrl: './cafeteria-notification.component.html',
  styleUrls: ['./cafeteria-notification.component.css']
})
export class CafeteriaNotificationComponent implements OnInit {

  constructor(private tokenService: TokenStorageService, private router: Router) {
    if (!this.tokenService.getUserRole().includes('ROLE_CAFETERIA')) {
      this.router.navigate(['/accessalert']);
    }
  }
  ngOnInit(): void {
  }


}
