import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UnauthorisedAccessComponent } from './unauthorised-access/unauthorised-access.component';


const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'accessalert', component: UnauthorisedAccessComponent },
  { path: 'user', loadChildren: './usermodule/user.module/#UserModule' },
  { path: 'admin', loadChildren: './adminmodule/admin.module/#AdminModule' },
  { path: 'cafeteria', loadChildren: './cafeteriamodule/cafeteria.module/#CafeteriaModule' },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
