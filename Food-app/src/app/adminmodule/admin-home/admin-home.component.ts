import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {

  constructor(private tokenStorageService: TokenStorageService, private router: Router) {
    if(!this.tokenStorageService.getUserRole().includes('ROLE_ADMIN'))
    {
      this.router.navigate(['/accessalert']);
    }
  }
  ngOnInit() {
  }
}

