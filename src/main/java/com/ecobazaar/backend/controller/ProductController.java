package com.ecobazaar.backend.controller;

import com.ecobazaar.backend.entity.Product;
import com.ecobazaar.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Get product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        try {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isPresent()) {
                return ResponseEntity.ok(product.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get products by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        try {
            List<Product> products = productRepository.findByCategory(category);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Get products by store
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<Product>> getProductsByStore(@PathVariable String storeId) {
        try {
            List<Product> products = productRepository.findByStoreId(storeId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Search products
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query) {
        try {
            List<Product> products = productRepository.findByNameContainingIgnoreCase(query);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Add new product (Admin/Shopkeeper only)
    @PostMapping
    public ResponseEntity<Map<String, Object>> addProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productRepository.save(product);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Product added successfully",
                "product", savedProduct
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error adding product: " + e.getMessage()
            ));
        }
    }

    // Update product (Admin/Shopkeeper only)
    @PutMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> updateProduct(
            @PathVariable Long productId, 
            @RequestBody Product productDetails) {
        try {
            Optional<Product> productOptional = productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                product.setName(productDetails.getName());
                product.setDescription(productDetails.getDescription());
                product.setPrice(productDetails.getPrice());
                product.setQuantity(productDetails.getQuantity());
                product.setCategory(productDetails.getCategory());
                product.setStoreId(productDetails.getStoreId());
                product.setStoreName(productDetails.getStoreName());
                
                Product updatedProduct = productRepository.save(product);
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Product updated successfully",
                    "product", updatedProduct
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error updating product: " + e.getMessage()
            ));
        }
    }

    // Delete product (Admin/Shopkeeper only)
    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long productId) {
        try {
            if (productRepository.existsById(productId)) {
                productRepository.deleteById(productId);
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Product deleted successfully"
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error deleting product: " + e.getMessage()
            ));
        }
    }

    // Get product statistics
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getProductStats() {
        try {
            long totalProducts = productRepository.count();
            List<String> categories = productRepository.findDistinctCategories();
            
            return ResponseEntity.ok(Map.of(
                "totalProducts", totalProducts,
                "categories", categories,
                "totalCategories", categories.size()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "totalProducts", 0,
                "categories", List.of(),
                "totalCategories", 0
            ));
        }
    }
}
