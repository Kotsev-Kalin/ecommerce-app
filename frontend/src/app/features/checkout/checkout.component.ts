import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CartService } from '../../core/services/cart.service';
import { OrderService } from '../../core/services/order.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html'
})
export class CheckoutComponent {
  submitting = false;

  readonly form = this.fb.group({
    shippingAddress: ['', [Validators.required, Validators.minLength(10)]]
  });

  constructor(
    private readonly fb: FormBuilder,
    private readonly orderService: OrderService,
    private readonly cartService: CartService,
    private readonly router: Router
  ) {}

  submit(): void {
    if (this.form.invalid || this.submitting) {
      this.form.markAllAsTouched();
      return;
    }

    this.submitting = true;
    this.orderService.placeOrder(this.form.getRawValue() as { shippingAddress: string }).subscribe({
      next: () => {
        this.submitting = false;
        this.cartService.loadCart().subscribe();
        this.router.navigate(['/profile']);
      },
      error: () => {
        this.submitting = false;
      }
    });
  }
}
