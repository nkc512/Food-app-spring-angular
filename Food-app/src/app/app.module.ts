import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthService } from './_services/auth.service';
import { TokenStorageService } from './_services/token-storage.service';
import { UserService } from './_services/user.service';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UploadFileService } from './_services/upload-file.service';
import { DishService } from './_services/dish.service';
import { AuthInterceptor } from './_helpers/auth.interceptor';
import { CartService } from './_services/cart.service';
import { UnauthorisedAccessComponent } from './unauthorised-access/unauthorised-access.component';
import { AboutUsComponent } from './about-us/about-us.component';
import { ContactUsComponent } from './contact-us/contact-us.component';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    PageNotFoundComponent,
    HomeComponent,
    UnauthorisedAccessComponent,
    AboutUsComponent,
    ContactUsComponent
  ],
  imports: [
    NgbModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    CommonModule,
    AppRoutingModule
  ],
  providers: [
    AuthService,
    { provide : HTTP_INTERCEPTORS,useClass:  AuthInterceptor, multi:true},
    TokenStorageService,
    UserService,
    UploadFileService,
    DishService,
    CartService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
