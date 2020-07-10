import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../_services/user.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { AuthService } from '../_services/auth.service';
import { Credentials } from '../_classes/credentials';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  submitted = false;
  roles: string[] = [];
  userReactiveForm = new FormGroup({
    password: new FormControl(''),
    username: new FormControl(''),
  });
  user: Credentials;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private tokenStorage: TokenStorageService,
    private authService: AuthService) {
    this.user = new Credentials();
  }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    }
  }
  onSubmit() {
    this.submitted = true;
    console.log("vgvbvbvb");
    this.authService.login(this.userReactiveForm.value).subscribe(
      data => {
        console.log(data.accessToken);

        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;
        this.reloadPage();
      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }
  reloadPage() {
    window.location.reload();
  }
  /*
  reactiveSubmit() {
    console.log(this.userReactive.value);
    console.log('reach reactiveSubmit');
    this.user = this.userReactive.value;
    this.userService.login(this.user).subscribe(result => this.gotoUserList());
  }
*/
/*
  gotoUserList() {
    console.log('/api/test/user gotoUserlist function');
    this.router.navigate(['/api/test/user']);
  }
*/
}
