import { Component, Input } from '@angular/core';
import { Product } from '../../../core/models/api.models';
import { CartService } from '../../../core/services/cart.service';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.scss']
})
export class ProductCardComponent {
  @Input() product!: Product;

  constructor(private readonly cartService: CartService) {}

  addToCart(): void {
    if (!this.product?.id) {
      return;
    }

    this.cartService.addToCart(this.product.id).subscribe();
  }
}
