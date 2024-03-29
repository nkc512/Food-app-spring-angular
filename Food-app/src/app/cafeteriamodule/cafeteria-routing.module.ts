import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CafeteriaHomeComponent } from './cafeteria-home/cafeteria-home.component';
import { CafeteriaAddProductComponent } from './cafeteria-add-product/cafeteria-add-product.component';
import { CafeteriaNotificationComponent } from './cafeteria-notification/cafeteria-notification.component';
import { CafeteriaFeedbackComponent } from './cafeteria-feedback/cafeteria-feedback.component';


const routes: Routes = [
  { path: '', component: CafeteriaHomeComponent },
  { path: 'addproduct', component: CafeteriaAddProductComponent },
  { path: 'notifications', component: CafeteriaNotificationComponent },
  { path: 'feedbacks', component: CafeteriaFeedbackComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]

})
export class CafeteriaRoutingModule { }
