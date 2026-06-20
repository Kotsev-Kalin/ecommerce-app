package com.ecommerce.service;

import com.ecommerce.dto.CartItemRequest;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<CartItem> getCart(String email) {
        return cartItemRepository.findByUserEmailOrderByIdAsc(email);
    }

    @Transactional
    public List<CartItem> addItem(String email, CartItemRequest request) {
        User user = findUser(email);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (request.getQuantity() > product.getStockQuantity()) {
            throw new BadRequestException("Requested quantity exceeds stock");
        }

        CartItem item = cartItemRepository.findByUserEmailAndProductId(email, request.getProductId())
                .orElse(CartItem.builder().user(user).product(product).quantity(0).build());

        int updatedQuantity = item.getQuantity() + request.getQuantity();
        if (updatedQuantity > product.getStockQuantity()) {
            throw new BadRequestException("Cart quantity exceeds stock");
        }

        item.setQuantity(updatedQuantity);
        cartItemRepository.save(item);
        return getCart(email);
    }

    @Transactional
    public List<CartItem> updateQuantity(String email, Long productId, Integer quantity) {
        if (quantity == null || quantity < 1) {
            throw new BadRequestException("Quantity must be at least 1");
        }

        CartItem item = cartItemRepository.findByUserEmailAndProductId(email, productId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (quantity > item.getProduct().getStockQuantity()) {
            throw new BadRequestException("Requested quantity exceeds stock");
        }

        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return getCart(email);
    }

    @Transactional
    public List<CartItem> removeItem(String email, Long productId) {
        cartItemRepository.deleteByUserEmailAndProductId(email, productId);
        return getCart(email);
    }

    @Transactional
    public void clearCart(String email) {
        cartItemRepository.deleteAll(cartItemRepository.findByUserEmailOrderByIdAsc(email));
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
