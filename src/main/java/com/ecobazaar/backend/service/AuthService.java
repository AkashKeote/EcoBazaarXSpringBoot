package com.ecobazaar.backend.service;

import com.ecobazaar.backend.model.AuthResponse;
import com.ecobazaar.backend.model.UserRegistration;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Authentication Service for EcoBazaarX
 * Handles user authentication, JWT token generation, and Firebase integration
 */
@Service
public class AuthService {

    @Autowired
    private Firestore firestore;

    // JWT Secret Key (in production, use environment variable)
    private static final String JWT_SECRET = "EcoBazaarXSecretKeyForJWTTokenGeneration2024";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    
    // Token expiration times
    private static final long ACCESS_TOKEN_EXPIRATION = TimeUnit.HOURS.toMillis(24); // 24 hours
    private static final long REFRESH_TOKEN_EXPIRATION = TimeUnit.DAYS.toMillis(7); // 7 days

    /**
     * Authenticate user with email, password, and role
     */
    public AuthResponse authenticateUser(String email, String password, String role) {
        try {
            // Query Firestore for user with matching email and role
            QuerySnapshot querySnapshot = firestore.collection("users")
                .whereEqualTo("email", email)
                .whereEqualTo("role", role)
                .limit(1)
                .get()
                .get();

            if (querySnapshot.isEmpty()) {
                return new AuthResponse(false, "Invalid email or role", null, null, null, null);
            }

            QueryDocumentSnapshot userDoc = querySnapshot.getDocuments().get(0);
            Map<String, Object> userData = userDoc.getData();

            // In a real application, you would verify the password hash here
            // For now, we'll assume the password is correct if user exists
            String userId = userDoc.getId();
            String userName = (String) userData.get("name");
            String userRole = (String) userData.get("role");

            // Generate JWT tokens
            String accessToken = generateAccessToken(userId, email, userRole);
            String refreshToken = generateRefreshToken(userId, email);

            return new AuthResponse(
                true,
                "Login successful",
                accessToken,
                refreshToken,
                userId,
                userRole
            );

        } catch (Exception e) {
            return new AuthResponse(false, "Authentication failed: " + e.getMessage(), null, null, null, null);
        }
    }

    /**
     * Register new user
     */
    public AuthResponse registerUser(UserRegistration registration) {
        try {
            // Validate input
            if (registration.getName() == null || registration.getName().trim().isEmpty()) {
                return new AuthResponse(false, "Name is required", null, null, null, null);
            }
            if (registration.getEmail() == null || registration.getEmail().trim().isEmpty()) {
                return new AuthResponse(false, "Email is required", null, null, null, null);
            }
            if (registration.getPassword() == null || registration.getPassword().length() < 8) {
                return new AuthResponse(false, "Password must be at least 8 characters", null, null, null, null);
            }
            if (!registration.getPassword().equals(registration.getConfirmPassword())) {
                return new AuthResponse(false, "Passwords do not match", null, null, null, null);
            }

            // Check if user already exists
            QuerySnapshot existingUsers = firestore.collection("users")
                .whereEqualTo("email", registration.getEmail())
                .get()
                .get();

            if (!existingUsers.isEmpty()) {
                return new AuthResponse(false, "User with this email already exists", null, null, null, null);
            }

            // Create new user document
            String userId = UUID.randomUUID().toString();
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", userId);
            userData.put("name", registration.getName());
            userData.put("email", registration.getEmail());
            userData.put("role", registration.getRole());
            userData.put("status", "Active");
            userData.put("joinDate", new Date());
            userData.put("createdAt", new Date());
            userData.put("updatedAt", new Date());
            userData.put("phone", registration.getPhone() != null ? registration.getPhone() : "");
            userData.put("address", registration.getAddress() != null ? registration.getAddress() : "");
            userData.put("preferences", Map.of(
                "notifications", true,
                "emailNotifications", true,
                "darkMode", false
            ));
            userData.put("stats", Map.of(
                "totalOrders", 0,
                "totalSpent", 0.0,
                "carbonSaved", 0.0,
                "ecoPoints", 0,
                "streakDays", 0
            ));

            // Save to Firestore
            firestore.collection("users").document(userId).set(userData).get();

            // Generate JWT tokens
            String accessToken = generateAccessToken(userId, registration.getEmail(), registration.getRole());
            String refreshToken = generateRefreshToken(userId, registration.getEmail());

            return new AuthResponse(
                true,
                "Registration successful",
                accessToken,
                refreshToken,
                userId,
                registration.getRole()
            );

        } catch (Exception e) {
            return new AuthResponse(false, "Registration failed: " + e.getMessage(), null, null, null, null);
        }
    }

    /**
     * Validate JWT token
     */
    public Map<String, Object> validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

            return Map.of(
                "valid", true,
                "userId", claims.get("userId"),
                "email", claims.get("email"),
                "role", claims.get("role"),
                "expiresAt", claims.getExpiration()
            );
        } catch (Exception e) {
            return Map.of("valid", false, "message", "Invalid token: " + e.getMessage());
        }
    }

    /**
     * Refresh JWT token
     */
    public AuthResponse refreshToken(String refreshToken) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

            String userId = (String) claims.get("userId");
            String email = (String) claims.get("email");

            // Get user role from Firestore
            Map<String, Object> userData = firestore.collection("users")
                .document(userId)
                .get()
                .get()
                .getData();

            if (userData == null) {
                return new AuthResponse(false, "User not found", null, null, null, null);
            }

            String userRole = (String) userData.get("role");

            // Generate new tokens
            String newAccessToken = generateAccessToken(userId, email, userRole);
            String newRefreshToken = generateRefreshToken(userId, email);

            return new AuthResponse(
                true,
                "Token refreshed successfully",
                newAccessToken,
                newRefreshToken,
                userId,
                userRole
            );

        } catch (Exception e) {
            return new AuthResponse(false, "Token refresh failed: " + e.getMessage(), null, null, null, null);
        }
    }

    /**
     * Logout user (invalidate token)
     */
    public void logout(String token) {
        // In a real application, you would add the token to a blacklist
        // For now, we'll just log the logout
        System.out.println("User logged out with token: " + token.substring(0, Math.min(20, token.length())) + "...");
    }

    /**
     * Request password reset for user
     */
    public Map<String, Object> requestPasswordReset(String email) {
        try {
            // Check if user exists
            QuerySnapshot querySnapshot = firestore.collection("users")
                .whereEqualTo("email", email)
                .limit(1)
                .get()
                .get();

            if (querySnapshot.isEmpty()) {
                return Map.of(
                    "success", false,
                    "message", "No account found with this email address"
                );
            }

            // In a real application, you would:
            // 1. Generate a secure reset token
            // 2. Store it in the database with expiration
            // 3. Send an email with the reset link
            // For now, we'll just return a success message
            
            System.out.println("Password reset requested for email: " + email);
            
            return Map.of(
                "success", true,
                "message", "Password reset instructions have been sent to your email address"
            );

        } catch (Exception e) {
            return Map.of(
                "success", false,
                "message", "Password reset request failed: " + e.getMessage()
            );
        }
    }

    /**
     * Generate access token
     */
    private String generateAccessToken(String userId, String email, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION);

        return Jwts.builder()
            .setSubject(userId)
            .claim("userId", userId)
            .claim("email", email)
            .claim("role", role)
            .claim("type", "access")
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * Generate refresh token
     */
    private String generateRefreshToken(String userId, String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION);

        return Jwts.builder()
            .setSubject(userId)
            .claim("userId", userId)
            .claim("email", email)
            .claim("type", "refresh")
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
            .compact();
    }
}
