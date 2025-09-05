package com.ecobazaar.backend.repository;

import com.ecobazaar.backend.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByWishlistId(String wishlistId);
    Optional<WishlistItem> findByWishlistIdAndProductId(String wishlistId, String productId);
    boolean existsByWishlistIdAndProductId(String wishlistId, String productId);
    void deleteByWishlistIdAndProductId(String wishlistId, String productId);
    long countByWishlistId(String wishlistId);
}
