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
  showSuccess: boolean=false;
  successMessage: string;
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
    this.authService.login(this.userReactiveForm.value).subscribe(
      data => {
        console.log(data.accessToken);

        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;
        this.router.navigate(['/home']).then(() => {
          window.location.reload();
        });
      },
      err => {
        console.log(err);
        if (err.status === 403){
          this.errorMessage = "Login failed: "+err.error;
        }
        else if (err.status === 401)
        {
            this.errorMessage = 'Login failed: Wrong Credentials. Please try again.';
        }
        else
        {
          this.errorMessage = "Login failed: "+ err.error.error;
        }
        this.isLoginFailed = true;
      }
    );
   }
  // reloadPage() {
  //   window.location.reload();
  // }
  forgotPassword(){
    const user=this.userReactiveForm.value;
      if(user.username==''){
      this.errorMessage="Forgot password failed : Username is empty.";
      this.submitted=true; 
      this.isLoginFailed=true;
      setTimeout(() => { this.errorMessage = ''; this.isLoginFailed = false; this.submitted=false;}, 3000);
    }
    else{
      this.authService.forgotPassword(user.username).subscribe(
       res=>{
         console.log(res);
         this.showSuccess=true;
         this.successMessage="Reset password link is sent to your registered email.";
       },
       err=>{
         console.log(err);
         this.submitted=true;
         this.isLoginFailed=true;
         this.errorMessage="Forgot password failed: "+err.error.message;
         setTimeout(() => { this.errorMessage = ''; this.isLoginFailed = false; this.submitted=false;}, 3000);
       }
      );
    }
    console.log("forgot password :", user.username,"hi");
  }
}
