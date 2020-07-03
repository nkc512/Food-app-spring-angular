import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './_services/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Food-app';
  private roles: string[];
  isLoggedIn = false;
  showCafeteriaBoard = false;
  showUserBoard = false;
  username: string;

  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    console.log('ngOnInit', this.isLoggedIn);

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      console.log(user);
      this.roles = user.roles;
      console.log(this.roles);

      this.showCafeteriaBoard = this.roles.includes('ROLE_CAFETERIAMANAGER');
      this.showUserBoard = this.roles.includes('ROLE_USER');
      console.log(this.showCafeteriaBoard, this.showUserBoard);
      this.username = user.username;
    }
  }

  logout() {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}
