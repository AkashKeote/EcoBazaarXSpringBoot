package com.ecobazaar.backend.controller;

import com.ecobazaar.backend.entity.Order;
import com.ecobazaar.backend.entity.OrderStatus;
import com.ecobazaar.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Get order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        try {
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isPresent()) {
                return ResponseEntity.ok(order.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get orders by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable String userId) {
        try {
            List<Order> orders = orderRepository.findByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Get orders by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable OrderStatus status) {
        try {
            List<Order> orders = orderRepository.findByOrderStatus(status);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Create new order
    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Order order) {
        try {
            Order savedOrder = orderRepository.save(order);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Order created successfully",
                "order", savedOrder
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error creating order: " + e.getMessage()
            ));
        }
    }

    // Update order status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Map<String, Object>> updateOrderStatus(
            @PathVariable Long orderId, 
            @RequestBody Map<String, String> request) {
        try {
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                String newStatus = request.get("status");
                if (newStatus != null) {
                    try {
                        order.setOrderStatus(OrderStatus.valueOf(newStatus.toUpperCase()));
                        Order updatedOrder = orderRepository.save(order);
                        return ResponseEntity.ok(Map.of(
                            "success", true,
                            "message", "Order status updated successfully",
                            "order", updatedOrder
                        ));
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body(Map.of(
                            "success", false,
                            "message", "Invalid status: " + newStatus
                        ));
                    }
                } else {
                    return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "Status is required"
                    ));
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error updating order status: " + e.getMessage()
            ));
        }
    }

    // Cancel order
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Map<String, Object>> cancelOrder(@PathVariable Long orderId) {
        try {
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                order.setOrderStatus(OrderStatus.CANCELLED);
                Order updatedOrder = orderRepository.save(order);
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Order cancelled successfully",
                    "order", updatedOrder
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error cancelling order: " + e.getMessage()
            ));
        }
    }

    // Delete order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable Long orderId) {
        try {
            if (orderRepository.existsById(orderId)) {
                orderRepository.deleteById(orderId);
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Order deleted successfully"
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error deleting order: " + e.getMessage()
            ));
        }
    }

    // Get order statistics
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getOrderStats() {
        try {
            long totalOrders = orderRepository.count();
            long pendingOrders = orderRepository.countByOrderStatus(OrderStatus.PENDING);
            long completedOrders = orderRepository.countByOrderStatus(OrderStatus.COMPLETED);
            long cancelledOrders = orderRepository.countByOrderStatus(OrderStatus.CANCELLED);
            
            return ResponseEntity.ok(Map.of(
                "totalOrders", totalOrders,
                "pendingOrders", pendingOrders,
                "completedOrders", completedOrders,
                "cancelledOrders", cancelledOrders
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "totalOrders", 0,
                "pendingOrders", 0,
                "completedOrders", 0,
                "cancelledOrders", 0
            ));
        }
    }
}
