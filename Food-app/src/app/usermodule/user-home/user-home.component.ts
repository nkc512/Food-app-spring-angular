import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {

  constructor(private tokenService: TokenStorageService, private router: Router) {
    if (!this.tokenService.getUserRole().includes('ROLE_USER')) {
      this.router.navigate(['/accessalert']);
    }
  }

  ngOnInit(): void {
  }

}
