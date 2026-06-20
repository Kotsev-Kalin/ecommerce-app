import { Component, OnInit } from '@angular/core';
import { Product } from '../../../core/models/api.models';
import { ProductService } from '../../../core/services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  searchTerm = '';

  constructor(private readonly productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getProducts(this.searchTerm).subscribe((products) => {
      this.products = products;
    });
  }
}
