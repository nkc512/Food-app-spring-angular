import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-add-product',
  templateUrl: './admin-add-product.component.html',
  styleUrls: ['./admin-add-product.component.css']
})
export class AdminAddProductComponent implements OnInit {

  constructor(private tokenStorageService: TokenStorageService, private router: Router) {
    if(!this.tokenStorageService.getUserRole().includes('ROLE_ADMIN'))
    {
      this.router.navigate(['/accessalert']);
    }
   }

  ngOnInit(): void {
  }

}
