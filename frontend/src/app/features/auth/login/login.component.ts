import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CartService } from '../../../core/services/cart.service';
import { AuthService } from '../../../core/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  readonly form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  errorMessage = '';

  constructor(
    private readonly fb: FormBuilder,
    private readonly authService: AuthService,
    private readonly cartService: CartService,
    private readonly router: Router
  ) {}

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.authService.login(this.form.getRawValue() as { email: string; password: string }).subscribe({
      next: () => {
        this.cartService.loadCart().subscribe();
        this.router.navigate(['/products']);
      },
      error: () => {
        this.errorMessage = 'Login failed. Please check your credentials.';
      }
    });
  }
}
