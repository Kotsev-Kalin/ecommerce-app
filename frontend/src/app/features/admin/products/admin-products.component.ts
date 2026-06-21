import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Product } from '../../../core/models/api.models';
import { ProductService } from '../../../core/services/product.service';

@Component({
  selector: 'app-admin-products',
  templateUrl: './admin-products.component.html'
})
export class AdminProductsComponent implements OnInit {
  products: Product[] = [];
  editingId?: number;

  readonly form = this.fb.group({
    name: ['', Validators.required],
    description: [''],
    price: [0, [Validators.required, Validators.min(0)]],
    imageUrl: [''],
    stockQuantity: [0, [Validators.required, Validators.min(0)]],
    categoryName: ['', Validators.required],
    active: [true]
  });

  constructor(private readonly fb: FormBuilder, private readonly productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe((products) => (this.products = products));
  }

  edit(product: Product): void {
    this.editingId = product.id;
    this.form.patchValue({
      name: product.name,
      description: product.description,
      price: product.price,
      imageUrl: product.imageUrl,
      stockQuantity: product.stockQuantity,
      categoryName: product.categoryName,
      active: product.active ?? true
    });
  }

  save(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const payload = this.form.getRawValue() as Product;
    const request = this.editingId
      ? this.productService.updateProduct(this.editingId, payload)
      : this.productService.createProduct(payload);

    request.subscribe(() => {
      this.resetForm();
      this.loadProducts();
    });
  }

  delete(id?: number): void {
    if (!id) {
      return;
    }
    this.productService.deleteProduct(id).subscribe(() => this.loadProducts());
  }

  resetForm(): void {
    this.editingId = undefined;
    this.form.reset({
      name: '',
      description: '',
      price: 0,
      imageUrl: '',
      stockQuantity: 0,
      categoryName: '',
      active: true
    });
  }
}
