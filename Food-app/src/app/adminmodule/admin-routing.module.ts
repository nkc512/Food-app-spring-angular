import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminAddProductComponent } from './admin-add-product/admin-add-product.component';
import { AdminNotificationComponent } from './admin-notification/admin-notification.component';


const routes: Routes = [
  { path: '', component: AdminHomeComponent },
  { path: 'addproduct', component: AdminAddProductComponent },
  { path: 'notifications', component: AdminNotificationComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
