import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserCartComponent } from './user-cart/user-cart.component';
import { UserHomeComponent } from './user-home/user-home.component';
import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';


const routes: Routes = [
    { path: '', component: UserHomeComponent },
    { path: 'cart', component: UserCartComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class UserRoutingModule { }
