package com.ecommerce.controller;

import com.ecommerce.dto.CartItemRequest;
import com.ecommerce.entity.CartItem;
import com.ecommerce.service.CartService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<CartItem> getCart(Principal principal) {
        return cartService.getCart(principal.getName());
    }

    @PostMapping
    public List<CartItem> addItem(Principal principal, @Valid @RequestBody CartItemRequest request) {
        return cartService.addItem(principal.getName(), request);
    }

    @PutMapping("/{productId}")
    public List<CartItem> updateQuantity(Principal principal,
                                         @PathVariable Long productId,
                                         @RequestParam Integer quantity) {
        return cartService.updateQuantity(principal.getName(), productId, quantity);
    }

    @DeleteMapping("/{productId}")
    public List<CartItem> removeItem(Principal principal, @PathVariable Long productId) {
        return cartService.removeItem(principal.getName(), productId);
    }
}
