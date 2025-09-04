package com.ecobazaar.backend.service;

import com.ecobazaar.backend.model.*;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Settings Service Class
 * 
 * Handles all settings-related business logic and Firestore operations
 * for the EcoBazaarX backend application.
 */
@Service
public class SettingsService {

    @Autowired
    private Firestore firestore;

    private static final String USERS_COLLECTION = "users";
    private static final String SETTINGS_FIELD = "settings";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Get user settings from Firestore
     * 
     * @param userId User ID
     * @return Settings object or null if not found
     */
    @Cacheable(value = "settings", key = "#userId")
    public Settings getUserSettings(String userId) {
        try {
            DocumentReference docRef = firestore.collection(USERS_COLLECTION).document(userId);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                Map<String, Object> data = document.getData();
                if (data != null && data.containsKey(SETTINGS_FIELD)) {
                    Map<String, Object> settingsData = (Map<String, Object>) data.get(SETTINGS_FIELD);
                    return mapToSettings(userId, settingsData);
                }
            }

            // Return default settings if user doesn't exist
            return createDefaultSettings(userId);

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error getting user settings: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Update a specific setting for a user
     * 
     * @param userId User ID
     * @param category Setting category (notifications, privacy, preferences, app, sync)
     * @param key Setting key
     * @param value Setting value
     * @return true if successful, false otherwise
     */
    @CacheEvict(value = "settings", key = "#userId")
    public boolean updateSetting(String userId, String category, String key, Object value) {
        try {
            String fieldPath = SETTINGS_FIELD + "." + category + "." + key;
            Map<String, Object> updates = new HashMap<>();
            updates.put(fieldPath, value);
            updates.put(SETTINGS_FIELD + ".lastUpdated", getCurrentTimestamp());

            DocumentReference docRef = firestore.collection(USERS_COLLECTION).document(userId);
            ApiFuture<WriteResult> future = docRef.update(updates);
            WriteResult result = future.get();

            System.out.println("✅ Setting updated: " + fieldPath + " = " + value);
            System.out.println("📍 Update time: " + result.getUpdateTime());
            return true;

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Error updating setting: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update notification settings for a user
     * 
     * @param userId User ID
     * @param notificationSettings Notification settings object
     * @return true if successful, false otherwise
     */
    @CacheEvict(value = "settings", key = "#userId")
    public boolean updateNotificationSettings(String userId, NotificationSettings notificationSettings) {
        try {
            Map<String, Object> updates = new HashMap<>();
            updates.put(SETTINGS_FIELD + ".notifications", notificationSettings);
            updates.put(SETTINGS_FIELD + ".lastUpdated", getCurrentTimestamp());

            DocumentReference docRef = firestore.collection(USERS_COLLECTION).document(userId);
            ApiFuture<WriteResult> future = docRef.update(updates);
            WriteResult result = future.get();

            System.out.println("✅ Notification settings updated for user: " + userId);
            System.out.println("📍 Update time: " + result.getUpdateTime());
            return true;

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Error updating notification settings: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Reset user settings to defaults
     * 
     * @param userId User ID
     * @return true if successful, false otherwise
     */
    @CacheEvict(value = "settings", key = "#userId")
    public boolean resetToDefaults(String userId) {
        try {
            Settings defaultSettings = createDefaultSettings(userId);
            Map<String, Object> updates = new HashMap<>();
            updates.put(SETTINGS_FIELD, defaultSettings);

            DocumentReference docRef = firestore.collection(USERS_COLLECTION).document(userId);
            ApiFuture<WriteResult> future = docRef.set(updates);
            WriteResult result = future.get();

            System.out.println("✅ Settings reset to defaults for user: " + userId);
            System.out.println("📍 Update time: " + result.getUpdateTime());
            return true;

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Error resetting settings: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Export user settings
     * 
     * @param userId User ID
     * @return Map containing exported settings data
     */
    public Map<String, Object> exportSettings(String userId) {
        try {
            Settings settings = getUserSettings(userId);
            if (settings != null) {
                Map<String, Object> exportData = new HashMap<>();
                exportData.put("version", "1.0.0");
                exportData.put("exported_at", getCurrentTimestamp());
                exportData.put("user_id", userId);
                exportData.put("settings", settings);
                return exportData;
            }
            return null;
        } catch (Exception e) {
            System.err.println("❌ Error exporting settings: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Import user settings
     * 
     * @param userId User ID
     * @param importData Import data map
     * @return true if successful, false otherwise
     */
    @CacheEvict(value = "settings", key = "#userId")
    public boolean importSettings(String userId, Map<String, Object> importData) {
        try {
            if (!"1.0.0".equals(importData.get("version"))) {
                System.err.println("❌ Unsupported settings version");
                return false;
            }

            Map<String, Object> settings = (Map<String, Object>) importData.get("settings");
            if (settings != null) {
                Map<String, Object> updates = new HashMap<>();
                updates.put(SETTINGS_FIELD, settings);
                updates.put(SETTINGS_FIELD + ".lastUpdated", getCurrentTimestamp());

                DocumentReference docRef = firestore.collection(USERS_COLLECTION).document(userId);
                ApiFuture<WriteResult> future = docRef.set(updates);
                WriteResult result = future.get();

                System.out.println("✅ Settings imported successfully for user: " + userId);
                System.out.println("📍 Update time: " + result.getUpdateTime());
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("❌ Error importing settings: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Create default settings for a new user
     * 
     * @param userId User ID
     * @return Default settings object
     */
    private Settings createDefaultSettings(String userId) {
        NotificationSettings notifications = new NotificationSettings();
        PrivacySettings privacy = new PrivacySettings();
        AppPreferences preferences = new AppPreferences();
        AppSettings app = new AppSettings();
        SyncSettings sync = new SyncSettings();

        String timestamp = getCurrentTimestamp();
        
        return new Settings(userId, notifications, privacy, preferences, app, sync);
    }

    /**
     * Map Firestore data to Settings object
     * 
     * @param userId User ID
     * @param data Firestore data
     * @return Settings object
     */
    private Settings mapToSettings(String userId, Map<String, Object> data) {
        // This is a simplified mapping - in production, you'd want more robust mapping
        Settings settings = new Settings();
        settings.setUserId(userId);
        
        // Map notifications
        if (data.containsKey("notifications")) {
            Map<String, Object> notifData = (Map<String, Object>) data.get("notifications");
            NotificationSettings notifications = new NotificationSettings();
            notifications.setPushNotificationsEnabled((Boolean) notifData.getOrDefault("pushNotificationsEnabled", true));
            notifications.setOrderNotificationsEnabled((Boolean) notifData.getOrDefault("orderNotificationsEnabled", true));
            notifications.setEcoTipsEnabled((Boolean) notifData.getOrDefault("ecoTipsEnabled", true));
            notifications.setPromotionalNotificationsEnabled((Boolean) notifData.getOrDefault("promotionalNotificationsEnabled", true));
            notifications.setCarbonTrackingEnabled((Boolean) notifData.getOrDefault("carbonTrackingEnabled", true));
            settings.setNotifications(notifications);
        }

        // Add similar mapping for other settings...
        
        return settings;
    }

    /**
     * Get current timestamp as formatted string
     * 
     * @return Formatted timestamp string
     */
    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(formatter);
    }
}

