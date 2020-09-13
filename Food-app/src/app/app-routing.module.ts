import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UnauthorisedAccessComponent } from './unauthorised-access/unauthorised-access.component';
import { AboutUsComponent } from './about-us/about-us.component';
import { ContactUsComponent } from './contact-us/contact-us.component';


const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'login/:message', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'accessalert', component: UnauthorisedAccessComponent },
  { path: 'aboutus', component: AboutUsComponent },
  { path: 'contactus', component: ContactUsComponent },
  { path: 'user', loadChildren: () => import('./usermodule/user.module').then(m => m.UserModule)},
  { path: 'admin', loadChildren: () => import('./adminmodule/admin.module').then(m => m.AdminModule)},
  { path: 'cafeteria', loadChildren: () => import('./cafeteriamodule/cafeteria.module').then(m => m.CafeteriaModule)},
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
