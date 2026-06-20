import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminGuard } from './core/auth/admin.guard';
import { AuthGuard } from './core/auth/auth.guard';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { CartComponent } from './features/cart/cart.component';
import { CheckoutComponent } from './features/checkout/checkout.component';
import { HomeComponent } from './features/home/home.component';
import { ProfileComponent } from './features/profile/profile.component';
import { ProductDetailComponent } from './features/products/product-detail/product-detail.component';
import { ProductListComponent } from './features/products/product-list/product-list.component';
import { AdminDashboardComponent } from './features/admin/dashboard/admin-dashboard.component';
import { AdminProductsComponent } from './features/admin/products/admin-products.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'products', component: ProductListComponent },
  { path: 'products/:id', component: ProductDetailComponent },
  { path: 'cart', component: CartComponent, canActivate: [AuthGuard] },
  { path: 'checkout', component: CheckoutComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'admin', component: AdminDashboardComponent, canActivate: [AdminGuard] },
  { path: 'admin/products', component: AdminProductsComponent, canActivate: [AdminGuard] },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
