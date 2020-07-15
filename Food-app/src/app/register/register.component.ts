import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormControl } from '@angular/forms';
import { UserService } from '../_services/user.service';
import { AuthService } from '../_services/auth.service';
import { User } from '../_classes/user';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


  userReactiveForm = new FormGroup({
    password: new FormControl(''),
    username: new FormControl(''),
    email: new FormControl(''),
  });
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  submitClick = false;

  user: User;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private authService: AuthService) {
    this.user = new User();
  }

  ngOnInit(): void {

  }
  onSubmit() {
    this.submitClick = true;
    this.authService.register(this.userReactiveForm.value).subscribe(
      data => {
        //console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        this.router.navigate(['/login'])
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }
  /*
  reactiveSubmit() {
    console.log(this.userReactive.value);
    console.log('reach reactiveSubmit');
    this.user = this.userReactive.value;
    //this.userService.signUp(this.user).subscribe(result => this.gotoUserList());
    
  }
*/
  gotoUserList() {
    console.log('reach gotoUserList');
    this.router.navigate(['/users']);
  }

}
