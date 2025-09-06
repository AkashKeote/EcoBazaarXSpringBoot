package com.ecobazaar.backend.controller;

import com.ecobazaar.backend.entity.Store;
import com.ecobazaar.backend.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/stores")
@CrossOrigin(origins = "*")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    // Get all stores
    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        try {
            List<Store> stores = storeRepository.findAll();
            return ResponseEntity.ok(stores);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Get store by ID
    @GetMapping("/{storeId}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long storeId) {
        try {
            Optional<Store> store = storeRepository.findById(storeId);
            if (store.isPresent()) {
                return ResponseEntity.ok(store.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get stores by owner
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Store>> getStoresByOwner(@PathVariable String ownerId) {
        try {
            List<Store> stores = storeRepository.findByOwnerId(ownerId);
            return ResponseEntity.ok(stores);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Search stores
    @GetMapping("/search")
    public ResponseEntity<List<Store>> searchStores(@RequestParam String query) {
        try {
            List<Store> stores = storeRepository.findByNameContainingIgnoreCase(query);
            return ResponseEntity.ok(stores);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Add new store
    @PostMapping
    public ResponseEntity<Map<String, Object>> addStore(@RequestBody Store store) {
        try {
            Store savedStore = storeRepository.save(store);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Store added successfully",
                "store", savedStore
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error adding store: " + e.getMessage()
            ));
        }
    }

    // Update store
    @PutMapping("/{storeId}")
    public ResponseEntity<Map<String, Object>> updateStore(
            @PathVariable Long storeId, 
            @RequestBody Store storeDetails) {
        try {
            Optional<Store> storeOptional = storeRepository.findById(storeId);
            if (storeOptional.isPresent()) {
                Store store = storeOptional.get();
                store.setStoreName(storeDetails.getStoreName());
                store.setDescription(storeDetails.getDescription());
                store.setOwnerId(storeDetails.getOwnerId());
                store.setOwnerEmail(storeDetails.getOwnerEmail());
                store.setContactPhone(storeDetails.getContactPhone());
                store.setAddress(storeDetails.getAddress());
                
                Store updatedStore = storeRepository.save(store);
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Store updated successfully",
                    "store", updatedStore
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error updating store: " + e.getMessage()
            ));
        }
    }

    // Delete store
    @DeleteMapping("/{storeId}")
    public ResponseEntity<Map<String, Object>> deleteStore(@PathVariable Long storeId) {
        try {
            if (storeRepository.existsById(storeId)) {
                storeRepository.deleteById(storeId);
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Store deleted successfully"
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error deleting store: " + e.getMessage()
            ));
        }
    }

    // Get store statistics
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStoreStats() {
        try {
            long totalStores = storeRepository.count();
            
            return ResponseEntity.ok(Map.of(
                "totalStores", totalStores
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "totalStores", 0
            ));
        }
    }
}
