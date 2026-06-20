import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CheckoutRequest, Order } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private readonly apiUrl = `${environment.apiUrl}/orders`;

  constructor(private readonly http: HttpClient) {}

  placeOrder(payload: CheckoutRequest): Observable<Order> {
    return this.http.post<Order>(`${this.apiUrl}/checkout`, payload);
  }

  getMyOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(this.apiUrl);
  }

  getAllOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.apiUrl}/admin/all`);
  }
}
