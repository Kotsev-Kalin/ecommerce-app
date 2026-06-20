package com.ecommerce.controller;

import com.ecommerce.dto.CheckoutRequest;
import com.ecommerce.entity.Order;
import com.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public Order checkout(Principal principal, @Valid @RequestBody CheckoutRequest request) {
        return orderService.checkout(principal.getName(), request.getShippingAddress());
    }

    @GetMapping
    public List<Order> getMyOrders(Principal principal) {
        return orderService.getUserOrders(principal.getName());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}
