import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cafeteria-home',
  templateUrl: './cafeteria-home.component.html',
  styleUrls: ['./cafeteria-home.component.css']
})
export class CafeteriaHomeComponent implements OnInit {

  constructor(private tokenService: TokenStorageService, private router: Router) {
    if (!this.tokenService.getUserRole().includes('ROLE_CAFETERIAMANAGER')) {
      this.router.navigate(['/accessalert']);
    }


  }
    ngOnInit(): void {
    }

  
}

