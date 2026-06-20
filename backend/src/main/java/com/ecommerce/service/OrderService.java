package com.ecommerce.service;

import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public Order checkout(String email, String shippingAddress) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<CartItem> cartItems = cartItemRepository.findByUserEmailOrderByIdAsc(email);

        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        Order order = Order.builder()
                .user(user)
                .shippingAddress(shippingAddress)
                .status(OrderStatus.PENDING)
                .items(new ArrayList<>())
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            if (cartItem.getQuantity() > product.getStockQuantity()) {
                throw new BadRequestException("Insufficient stock for product: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total = total.add(lineTotal);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .unitPrice(product.getPrice())
                    .lineTotal(lineTotal)
                    .build();
            order.getItems().add(orderItem);
        }

        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);
        cartItemRepository.deleteAll(cartItems);
        return savedOrder;
    }

    @Transactional(readOnly = true)
    public List<Order> getUserOrders(String email) {
        return orderRepository.findByUserEmailOrderByCreatedAtDesc(email);
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }
}
