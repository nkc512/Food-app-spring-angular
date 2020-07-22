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
    console.log('vgvbvbvb');
    this.authService.login(this.userReactiveForm.value).subscribe(
      data => {
        console.log(data.accessToken);

        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;
        //this.reloadPage();
        this.router.navigate(['/home']).then(() => {
          window.location.reload();
        });
      },
      err => {
        console.log(err);
        if (err.status === 403){
          this.errorMessage = err.error;
        }
        else if (err.status === 401)
        {
            this.errorMessage = 'Wrong Credentials. Please try again.';
        }
        else
        {
          this.errorMessage = err.error.error;
        }
        this.isLoginFailed = true;
      }
    );
  }
  reloadPage() {
    window.location.reload();
  }
}
