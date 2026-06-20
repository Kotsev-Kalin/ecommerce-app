import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Product } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private readonly apiUrl = `${environment.apiUrl}/products`;

  constructor(private readonly http: HttpClient) {}

  getProducts(search = ''): Observable<Product[]> {
    const suffix = search ? `?search=${encodeURIComponent(search)}` : '';
    return this.http.get<Product[]>(`${this.apiUrl}${suffix}`);
  }

  getProduct(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/${id}`);
  }

  createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, product);
  }

  updateProduct(id: number, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.apiUrl}/${id}`, product);
  }

  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
