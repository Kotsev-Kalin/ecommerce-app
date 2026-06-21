import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../core/auth/auth.service';
import { Order } from '../../core/models/api.models';
import { OrderService } from '../../core/services/order.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  orders: Order[] = [];

  constructor(
    public readonly authService: AuthService,
    private readonly orderService: OrderService
  ) {}

  ngOnInit(): void {
    this.orderService.getMyOrders().subscribe((orders) => (this.orders = orders));
  }
}
