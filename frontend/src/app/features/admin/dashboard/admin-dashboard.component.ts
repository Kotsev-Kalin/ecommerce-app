import { Component, OnInit } from '@angular/core';
import { Order } from '../../../core/models/api.models';
import { OrderService } from '../../../core/services/order.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html'
})
export class AdminDashboardComponent implements OnInit {
  orders: Order[] = [];

  constructor(private readonly orderService: OrderService) {}

  ngOnInit(): void {
    this.orderService.getAllOrders().subscribe((orders) => (this.orders = orders));
  }

  get totalRevenue(): number {
    return this.orders.reduce((sum, order) => sum + order.totalAmount, 0);
  }
}
