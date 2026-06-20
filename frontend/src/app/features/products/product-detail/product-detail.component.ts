import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../../../core/models/api.models';
import { CartService } from '../../../core/services/cart.service';
import { ProductService } from '../../../core/services/product.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html'
})
export class ProductDetailComponent implements OnInit {
  product?: Product;

  constructor(
    private readonly route: ActivatedRoute,
    private readonly productService: ProductService,
    private readonly cartService: CartService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProduct(id).subscribe((product) => (this.product = product));
  }

  addToCart(): void {
    if (!this.product?.id) {
      return;
    }

    this.cartService.addToCart(this.product.id).subscribe();
  }
}
