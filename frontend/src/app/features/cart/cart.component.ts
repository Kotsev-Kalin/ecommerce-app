import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartItem } from '../../core/models/api.models';
import { CartService } from '../../core/services/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  items: CartItem[] = [];

  constructor(private readonly cartService: CartService, private readonly router: Router) {}

  ngOnInit(): void {
    this.cartService.loadCart().subscribe((items) => (this.items = items));
    this.cartService.items$.subscribe((items) => (this.items = items));
  }

  get total(): number {
    return this.items.reduce((sum, item) => sum + item.product.price * item.quantity, 0);
  }

  updateQuantity(item: CartItem, quantity: string): void {
    this.cartService.updateQuantity(item.product.id!, Number(quantity)).subscribe();
  }

  removeItem(item: CartItem): void {
    this.cartService.removeItem(item.product.id!).subscribe();
  }

  goToCheckout(): void {
    this.router.navigate(['/checkout']);
  }
}
