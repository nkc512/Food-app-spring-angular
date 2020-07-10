import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cafeteria-profile',
  templateUrl: './cafeteria-profile.component.html',
  styleUrls: ['./cafeteria-profile.component.css']
})
export class CafeteriaProfileComponent implements OnInit {

  constructor(private tokenService: TokenStorageService, private router: Router) {
    if (!this.tokenService.getUserRole().includes('ROLE_CAFETERIA')) {
      this.router.navigate(['/accessalert']);
    }
  }
  ngOnInit(): void {
  }

}
