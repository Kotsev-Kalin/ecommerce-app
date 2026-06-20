import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../../../core/auth/auth.service';
import { CartService } from '../../../core/services/cart.service';
import { AuthResponse } from '../../../core/models/api.models';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  readonly currentUser$: Observable<AuthResponse | null> = this.authService.currentUser$;
  readonly cartCount$ = this.cartService.cartCount$;

  constructor(
    public readonly authService: AuthService,
    private readonly cartService: CartService
  ) {}

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.cartService.loadCart().subscribe();
    }
  }

  logout(): void {
    this.authService.logout();
  }
}
