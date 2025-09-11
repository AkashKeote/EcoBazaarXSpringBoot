package com.ecobazaar.backend.controller;

import com.ecobazaar.backend.entity.CartItem;
import com.ecobazaar.backend.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = {
    "http://localhost:3000", 
    "http://localhost:5173", 
    "http://127.0.0.1:3000",
    "http://127.0.0.1:5173",
    "https://ecobazaar.vercel.app",
    "https://ecobazzarx.web.app", 
    "https://ecobazzarx.firebaseapp.com",
    "https://akashkeote.github.io"
})
public class CartController {

    @Autowired
    private CartItemRepository cartItemRepository;

    // Get all cart items
    @GetMapping
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        try {
            List<CartItem> cartItems = cartItemRepository.findAll();
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Get cart items by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItem>> getCartByUser(@PathVariable String userId) {
        try {
            List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Add item to cart
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(@RequestBody Map<String, Object> request) {
        try {
            String userId = (String) request.get("userId");
            String productId = (String) request.get("productId");
            String productName = (String) request.get("productName");
            Double price = ((Number) request.get("price")).doubleValue();
            Integer quantity = request.get("quantity") != null ? 
                ((Number) request.get("quantity")).intValue() : 1;
            String imageUrl = (String) request.get("imageUrl");

            if (userId == null || productId == null || productName == null || price == null) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Missing required fields: userId, productId, productName, price"
                ));
            }

            // Check if item already exists in cart
            Optional<CartItem> existingItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
            
            CartItem cartItem;
            if (existingItem.isPresent()) {
                // Update quantity if item exists
                cartItem = existingItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setUpdatedAt(LocalDateTime.now());
            } else {
                // Create new cart item
                cartItem = new CartItem();
                cartItem.setUserId(userId);
                cartItem.setProductId(productId);
                cartItem.setProductName(productName);
                cartItem.setPrice(price);
                cartItem.setQuantity(quantity);
                cartItem.setImageUrl(imageUrl);
                cartItem.setCreatedAt(LocalDateTime.now());
                cartItem.setUpdatedAt(LocalDateTime.now());
            }

            CartItem savedItem = cartItemRepository.save(cartItem);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Item added to cart successfully",
                "cartItem", savedItem
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error adding to cart: " + e.getMessage()
            ));
        }
    }

    // Update cart item quantity
    @PutMapping("/update/{cartId}")
    public ResponseEntity<Map<String, Object>> updateCartItem(
            @PathVariable Long cartId, 
            @RequestBody Map<String, Object> request) {
        try {
            Integer quantity = ((Number) request.get("quantity")).intValue();
            
            Optional<CartItem> cartOptional = cartItemRepository.findById(cartId);
            if (cartOptional.isPresent()) {
                CartItem cartItem = cartOptional.get();
                cartItem.setQuantity(quantity);
                cartItem.setUpdatedAt(LocalDateTime.now());
                
                CartItem updatedItem = cartItemRepository.save(cartItem);
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Cart item updated successfully",
                    "cartItem", updatedItem
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error updating cart item: " + e.getMessage()
            ));
        }
    }

    // Remove item from cart
    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<Map<String, Object>> removeFromCart(@PathVariable Long cartId) {
        try {
            if (cartItemRepository.existsById(cartId)) {
                cartItemRepository.deleteById(cartId);
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Item removed from cart successfully"
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error removing from cart: " + e.getMessage()
            ));
        }
    }

    // Clear user cart
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Map<String, Object>> clearCart(@PathVariable String userId) {
        try {
            List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
            cartItemRepository.deleteAll(cartItems);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Cart cleared successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error clearing cart: " + e.getMessage()
            ));
        }
    }

    // Get cart count for user
    @GetMapping("/count/{userId}")
    public ResponseEntity<Map<String, Object>> getCartCount(@PathVariable String userId) {
        try {
            List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
            int totalCount = cartItems.stream().mapToInt(CartItem::getQuantity).sum();
            return ResponseEntity.ok(Map.of(
                "count", totalCount,
                "items", cartItems.size()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "count", 0,
                "items", 0,
                "error", e.getMessage()
            ));
        }
    }

    // Get cart total for user
    @GetMapping("/total/{userId}")
    public ResponseEntity<Map<String, Object>> getCartTotal(@PathVariable String userId) {
        try {
            List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
            double total = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
            return ResponseEntity.ok(Map.of(
                "total", total,
                "itemCount", cartItems.size()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "total", 0.0,
                "itemCount", 0,
                "error", e.getMessage()
            ));
        }
    }
}
