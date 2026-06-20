import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, map, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CartItem } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class CartService {
  private readonly apiUrl = `${environment.apiUrl}/cart`;
  private readonly itemsSubject = new BehaviorSubject<CartItem[]>([]);
  readonly items$ = this.itemsSubject.asObservable();
  readonly cartCount$ = this.items$.pipe(map((items) => items.reduce((sum, item) => sum + item.quantity, 0)));

  constructor(private readonly http: HttpClient) {}

  loadCart(): Observable<CartItem[]> {
    return this.http.get<CartItem[]>(this.apiUrl).pipe(tap((items) => this.itemsSubject.next(items)));
  }

  addToCart(productId: number, quantity = 1): Observable<CartItem[]> {
    return this.http.post<CartItem[]>(this.apiUrl, { productId, quantity }).pipe(
      tap((items) => this.itemsSubject.next(items))
    );
  }

  updateQuantity(productId: number, quantity: number): Observable<CartItem[]> {
    return this.http.put<CartItem[]>(`${this.apiUrl}/${productId}?quantity=${quantity}`, {}).pipe(
      tap((items) => this.itemsSubject.next(items))
    );
  }

  removeItem(productId: number): Observable<CartItem[]> {
    return this.http.delete<CartItem[]>(`${this.apiUrl}/${productId}`).pipe(
      tap((items) => this.itemsSubject.next(items))
    );
  }
}
