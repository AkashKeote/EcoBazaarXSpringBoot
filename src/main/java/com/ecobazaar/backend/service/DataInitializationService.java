package com.ecobazaar.backend.service;

import com.ecobazaar.backend.entity.*;
import com.ecobazaar.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Data Initialization Service for creating sample data when database is empty
 * This service provides methods to initialize the database with sample data
 */
@Service
@Transactional
public class DataInitializationService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private StoreRepository storeRepository;
    
    @Autowired
    private WishlistRepository wishlistRepository;
    
    @Autowired
    private WishlistItemRepository wishlistItemRepository;
    
    @Autowired
    private UserSettingsRepository userSettingsRepository;
    
    @Autowired
    private EcoChallengeRepository ecoChallengeRepository;

    /**
     * Initialize database with sample data if it's empty
     */
    public String initializeSampleData() {
        try {
            StringBuilder result = new StringBuilder();
            result.append("Initializing sample data...\n\n");
            
            // Initialize stores first
            result.append(initializeStores());
            
            // Initialize products
            result.append(initializeProducts());
            
            // Initialize users
            result.append(initializeUsers());
            
            // Initialize wishlists
            result.append(initializeWishlists());
            
            // Initialize eco challenges
            result.append(initializeEcoChallenges());
            
            result.append("\nSample data initialization completed successfully!\n");
            result.append(getInitializationStats());
            
            return result.toString();
            
        } catch (Exception e) {
            return "Error during initialization: " + e.getMessage();
        }
    }

    /**
     * Initialize sample stores
     */
    private String initializeStores() {
        try {
            if (storeRepository.count() > 0) {
                return "Stores already exist, skipping...\n";
            }
            
            List<Store> stores = Arrays.asList(
                createStore("store-001", "EcoMart", "Your one-stop shop for eco-friendly products", 
                          "admin", "admin@ecobazaar.com", "+91-9876543210", 
                          "123 Green Street, Eco City, EC 12345"),
                createStore("store-002", "GreenLife Store", "Sustainable living made easy", 
                          "admin", "admin@ecobazaar.com", "+91-9876543211", 
                          "456 Nature Avenue, Green Valley, GV 54321"),
                createStore("store-003", "EcoFriendly Hub", "Premium eco-friendly products", 
                          "admin", "admin@ecobazaar.com", "+91-9876543212", 
                          "789 Earth Road, Sustainable City, SC 67890"),
                createStore("store-004", "Green Corner", "Local eco-friendly products", 
                          "admin", "admin@ecobazaar.com", "+91-9876543213", 
                          "321 Eco Lane, Green Town, GT 13579"),
                createStore("store-005", "Sustainable Shop", "Environmentally conscious shopping", 
                          "admin", "admin@ecobazaar.com", "+91-9876543214", 
                          "654 Green Boulevard, Eco District, ED 24680")
            );
            
            storeRepository.saveAll(stores);
            return String.format("Initialized %d stores\n", stores.size());
            
        } catch (Exception e) {
            return "Error initializing stores: " + e.getMessage() + "\n";
        }
    }

    /**
     * Initialize sample products
     */
    private String initializeProducts() {
        try {
            if (productRepository.count() > 0) {
                return "Products already exist, skipping...\n";
            }
            
            List<Product> products = Arrays.asList(
                // Eco-friendly Home Products
                createProduct("Bamboo Toothbrush Set", "Biodegradable bamboo toothbrushes with soft bristles", 
                            29.99, 100, "Home & Living", "store-001", "EcoMart"),
                createProduct("Reusable Water Bottle", "Stainless steel water bottle, BPA-free", 
                            19.99, 150, "Home & Living", "store-001", "EcoMart"),
                createProduct("Organic Cotton Tote Bag", "Handmade organic cotton shopping bag", 
                            12.99, 200, "Fashion", "store-002", "GreenLife Store"),
                createProduct("Solar Phone Charger", "Portable solar-powered phone charger", 
                            49.99, 75, "Electronics", "store-002", "GreenLife Store"),
                createProduct("Beeswax Food Wraps", "Natural beeswax food storage wraps", 
                            24.99, 80, "Home & Living", "store-003", "EcoFriendly Hub"),
                createProduct("Compostable Cutlery Set", "Biodegradable cutlery made from corn starch", 
                            8.99, 300, "Home & Living", "store-003", "EcoFriendly Hub"),
                createProduct("Organic Face Cream", "Natural face cream with organic ingredients", 
                            34.99, 60, "Beauty & Personal Care", "store-004", "Green Corner"),
                createProduct("LED Energy Saver Bulbs", "Energy-efficient LED light bulbs", 
                            15.99, 120, "Home & Living", "store-004", "Green Corner"),
                createProduct("Recycled Paper Notebook", "Notebook made from 100% recycled paper", 
                            9.99, 180, "Office & Stationery", "store-005", "Sustainable Shop"),
                createProduct("Plant-Based Shampoo", "Vegan shampoo with natural ingredients", 
                            22.99, 90, "Beauty & Personal Care", "store-005", "Sustainable Shop"),
                
                // Eco-friendly Fashion
                createProduct("Organic Cotton T-Shirt", "100% organic cotton t-shirt", 
                            25.99, 100, "Fashion", "store-001", "EcoMart"),
                createProduct("Hemp Backpack", "Durable hemp backpack for daily use", 
                            45.99, 50, "Fashion", "store-002", "GreenLife Store"),
                createProduct("Recycled Denim Jeans", "Jeans made from recycled denim", 
                            59.99, 75, "Fashion", "store-003", "EcoFriendly Hub"),
                createProduct("Cork Wallet", "Eco-friendly wallet made from cork", 
                            18.99, 80, "Fashion", "store-004", "Green Corner"),
                createProduct("Bamboo Sunglasses", "Sunglasses with bamboo frames", 
                            39.99, 60, "Fashion", "store-005", "Sustainable Shop"),
                
                // Eco-friendly Kitchen
                createProduct("Glass Food Storage Set", "BPA-free glass food storage containers", 
                            35.99, 40, "Home & Living", "store-001", "EcoMart"),
                createProduct("Wooden Kitchen Utensils", "Handcrafted wooden cooking utensils", 
                            28.99, 65, "Home & Living", "store-002", "GreenLife Store"),
                createProduct("Silicone Baking Mats", "Reusable silicone baking mats", 
                            16.99, 90, "Home & Living", "store-003", "EcoFriendly Hub"),
                createProduct("Cast Iron Skillet", "Pre-seasoned cast iron skillet", 
                            42.99, 30, "Home & Living", "store-004", "Green Corner"),
                createProduct("Stainless Steel Straws", "Set of reusable stainless steel straws", 
                            11.99, 150, "Home & Living", "store-005", "Sustainable Shop")
            );
            
            productRepository.saveAll(products);
            return String.format("Initialized %d products\n", products.size());
            
        } catch (Exception e) {
            return "Error initializing products: " + e.getMessage() + "\n";
        }
    }

    /**
     * Initialize sample users
     */
    private String initializeUsers() {
        try {
            if (userRepository.count() > 0) {
                return "Users already exist, skipping...\n";
            }
            
            List<User> users = Arrays.asList(
                createUser("admin@ecobazaar.com", "Admin User", "+91-9876543210", "ADMIN"),
                createUser("shopkeeper@ecobazaar.com", "Shopkeeper User", "+91-9876543211", "SHOPKEEPER"),
                createUser("customer@ecobazaar.com", "Customer User", "+91-9876543212", "CUSTOMER"),
                createUser("keoteakash@gmail.com", "Akash Keote", "+91-9876543213", "CUSTOMER")
            );
            
            userRepository.saveAll(users);
            return String.format("Initialized %d users\n", users.size());
            
        } catch (Exception e) {
            return "Error initializing users: " + e.getMessage() + "\n";
        }
    }

    /**
     * Initialize sample wishlists
     */
    private String initializeWishlists() {
        try {
            if (wishlistRepository.count() > 0) {
                return "Wishlists already exist, skipping...\n";
            }
            
            // Create wishlists for existing users
            List<User> users = userRepository.findAll();
            for (User user : users) {
                if (user.getRole() == UserRole.CUSTOMER) {
                    Wishlist wishlist = new Wishlist();
                    wishlist.setWishlistId(UUID.randomUUID().toString());
                    wishlist.setUserId(user.getId().toString());
                    wishlist.setTotalItems(0);
                    wishlistRepository.save(wishlist);
                    
                    // Create default user settings
                    UserSettings settings = new UserSettings();
                    settings.setUserId(user.getId().toString());
                    userSettingsRepository.save(settings);
                }
            }
            
            return String.format("Initialized %d wishlists and user settings\n", users.size());
            
        } catch (Exception e) {
            return "Error initializing wishlists: " + e.getMessage() + "\n";
        }
    }

    /**
     * Create a store entity
     */
    private Store createStore(String storeId, String storeName, String description, 
                            String ownerId, String ownerEmail, String contactPhone, String address) {
        Store store = new Store();
        store.setStoreId(storeId);
        store.setStoreName(storeName);
        store.setDescription(description);
        store.setOwnerId(ownerId);
        store.setOwnerEmail(ownerEmail);
        store.setContactPhone(contactPhone);
        store.setAddress(address);
        store.setIsActive(true);
        store.setIsVerified(true);
        store.setEcoRating(4.5);
        store.setTotalProducts(0);
        return store;
    }

    /**
     * Create a product entity
     */
    private Product createProduct(String name, String description, Double price, 
                                Integer quantity, String category, String storeId, String storeName) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setCategory(category);
        product.setStoreId(storeId);
        product.setStoreName(storeName);
        product.setIsActive(true);
        product.setCarbonFootprint(0.5); // Default carbon footprint
        product.setEcoPoints(10); // Default eco points
        return product;
    }

    /**
     * Create a user entity
     */
    private User createUser(String email, String name, String phone, String role) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPhone(phone);
        user.setRole(UserRole.valueOf(role));
        user.setIsActive(true);
        // Set a default password for sample users
        user.setPassword("$2a$10$defaultPasswordForSampleUsers");
        return user;
    }

    /**
     * Initialize sample eco challenges
     */
    private String initializeEcoChallenges() {
        try {
            if (ecoChallengeRepository.count() > 0) {
                return "Eco challenges already exist, skipping...\n";
            }
            
            List<EcoChallenge> challenges = Arrays.asList(
                createEcoChallenge("30-Day Plastic Free Challenge", 
                    "Go 30 days without using single-use plastic products. This includes plastic bags, bottles, straws, and packaging.", 
                    100, 30, "Sustainability", "MEDIUM", "🌱", "#4CAF50"),
                createEcoChallenge("Energy Saving Challenge", 
                    "Reduce your energy consumption by 20% for 14 days. Use energy-efficient appliances and turn off unused devices.", 
                    75, 14, "Energy", "EASY", "⚡", "#FF9800"),
                createEcoChallenge("Zero Waste Week", 
                    "Generate zero waste for 7 days. Compost organic waste, recycle properly, and avoid single-use items.", 
                    50, 7, "Waste Reduction", "HARD", "♻️", "#2196F3"),
                createEcoChallenge("Plant-Based Diet Challenge", 
                    "Follow a plant-based diet for 21 days. Reduce your carbon footprint through dietary choices.", 
                    80, 21, "Food & Diet", "MEDIUM", "🥗", "#8BC34A"),
                createEcoChallenge("Water Conservation Challenge", 
                    "Reduce water usage by 30% for 14 days. Take shorter showers, fix leaks, and use water-efficient appliances.", 
                    60, 14, "Water Conservation", "EASY", "💧", "#00BCD4"),
                createEcoChallenge("Sustainable Transportation", 
                    "Use only eco-friendly transportation for 30 days. Walk, bike, use public transport, or carpool.", 
                    90, 30, "Transportation", "HARD", "🚲", "#9C27B0"),
                createEcoChallenge("Digital Detox Challenge", 
                    "Reduce screen time by 50% for 7 days. Spend more time outdoors and with nature.", 
                    40, 7, "Lifestyle", "EASY", "📱", "#607D8B"),
                createEcoChallenge("Local Shopping Challenge", 
                    "Buy only locally produced items for 14 days. Support local businesses and reduce transportation emissions.", 
                    70, 14, "Shopping", "MEDIUM", "🏪", "#795548"),
                createEcoChallenge("Green Home Challenge", 
                    "Make your home more eco-friendly for 30 days. Use natural cleaning products, reduce energy consumption.", 
                    85, 30, "Home & Living", "MEDIUM", "🏠", "#4CAF50"),
                createEcoChallenge("Ocean Cleanup Challenge", 
                    "Participate in beach cleanups or reduce ocean pollution for 21 days. Every action counts!", 
                    95, 21, "Ocean Conservation", "HARD", "🌊", "#00BCD4")
            );
            
            ecoChallengeRepository.saveAll(challenges);
            return String.format("Initialized %d eco challenges\n", challenges.size());
            
        } catch (Exception e) {
            return "Error initializing eco challenges: " + e.getMessage() + "\n";
        }
    }
    
    /**
     * Create an eco challenge entity
     */
    private EcoChallenge createEcoChallenge(String title, String description, Integer points, 
                                          Integer duration, String category, String difficultyLevel, 
                                          String icon, String color) {
        EcoChallenge challenge = new EcoChallenge();
        challenge.setTitle(title);
        challenge.setDescription(description);
        challenge.setPoints(points);
        challenge.setDuration(duration);
        challenge.setCategory(category);
        challenge.setDifficultyLevel(difficultyLevel);
        challenge.setIcon(icon);
        challenge.setColor(color);
        challenge.setIsActive(true);
        challenge.setCurrentParticipants(0);
        challenge.setMaxParticipants(100); // Default max participants
        return challenge;
    }

    /**
     * Get initialization statistics
     */
    public String getInitializationStats() {
        return String.format(
            "Initialization Statistics:\n" +
            "Users: %d\n" +
            "Products: %d\n" +
            "Stores: %d\n" +
            "Wishlists: %d\n" +
            "User Settings: %d\n" +
            "Eco Challenges: %d",
            userRepository.count(),
            productRepository.count(),
            storeRepository.count(),
            wishlistRepository.count(),
            userSettingsRepository.count(),
            ecoChallengeRepository.count()
        );
    }

    /**
     * Check if database needs initialization
     */
    public boolean needsInitialization() {
        return productRepository.count() == 0 && storeRepository.count() == 0;
    }
}


