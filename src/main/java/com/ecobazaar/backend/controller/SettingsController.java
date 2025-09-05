package com.ecobazaar.backend.controller;

import com.ecobazaar.backend.model.NotificationSettings;
import com.ecobazaar.backend.model.Settings;
import com.ecobazaar.backend.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Settings Controller Class
 * 
 * Provides REST API endpoints for user settings management
 * in the EcoBazaarX backend application.
 */
@RestController
@RequestMapping("/settings")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173", "https://akashkeote.github.io"})
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    /**
     * Get user settings
     * 
     * @param userId User ID
     * @return User settings or error response
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserSettings(@PathVariable String userId) {
        try {
            Settings settings = settingsService.getUserSettings(userId);
            if (settings != null) {
                return ResponseEntity.ok(settings);
            } else {
                return ResponseEntity.status(404).body("Settings not found for user: " + userId);
            }
        } catch (Exception e) {
            System.err.println("❌ Error getting settings: " + e.getMessage());
            return ResponseEntity.internalServerError()
                .body("Error retrieving settings: " + e.getMessage());
        }
    }

    /**
     * Update a specific setting
     * 
     * @param userId User ID
     * @param request Update request containing category, key, and value
     * @return Success or error response
     */
    @PostMapping("/{userId}/update")
    public ResponseEntity<?> updateSetting(
            @PathVariable String userId,
            @RequestBody Map<String, Object> request) {
        
        try {
            String category = (String) request.get("category");
            String key = (String) request.get("key");
            Object value = request.get("value");

            if (category == null || key == null || value == null) {
                return ResponseEntity.badRequest()
                    .body("Missing required fields: category, key, or value");
            }

            boolean success = settingsService.updateSetting(userId, category, key, value);
            
            if (success) {
                return ResponseEntity.ok("Setting updated successfully");
            } else {
                return ResponseEntity.internalServerError()
                    .body("Failed to update setting");
            }

        } catch (Exception e) {
            System.err.println("❌ Error updating setting: " + e.getMessage());
            return ResponseEntity.internalServerError()
                .body("Error updating setting: " + e.getMessage());
        }
    }

    /**
     * Update notification settings
     * 
     * @param userId User ID
     * @param notificationSettings Notification settings object
     * @return Success or error response
     */
    @PostMapping("/{userId}/notifications")
    public ResponseEntity<?> updateNotificationSettings(
            @PathVariable String userId,
            @RequestBody NotificationSettings notificationSettings) {
        
        try {
            boolean success = settingsService.updateNotificationSettings(userId, notificationSettings);
            
            if (success) {
                return ResponseEntity.ok("Notification settings updated successfully");
            } else {
                return ResponseEntity.internalServerError()
                    .body("Failed to update notification settings");
            }

        } catch (Exception e) {
            System.err.println("❌ Error updating notification settings: " + e.getMessage());
            return ResponseEntity.internalServerError()
                .body("Error updating notification settings: " + e.getMessage());
        }
    }

    /**
     * Reset user settings to defaults
     * 
     * @param userId User ID
     * @return Success or error response
     */
    @PostMapping("/{userId}/reset")
    public ResponseEntity<?> resetToDefaults(@PathVariable String userId) {
        try {
            boolean success = settingsService.resetToDefaults(userId);
            
            if (success) {
                return ResponseEntity.ok("Settings reset to defaults successfully");
            } else {
                return ResponseEntity.internalServerError()
                    .body("Failed to reset settings");
            }

        } catch (Exception e) {
            System.err.println("❌ Error resetting settings: " + e.getMessage());
            return ResponseEntity.internalServerError()
                .body("Error resetting settings: " + e.getMessage());
        }
    }

    /**
     * Export user settings
     * 
     * @param userId User ID
     * @return Exported settings data or error response
     */
    @GetMapping("/{userId}/export")
    public ResponseEntity<?> exportSettings(@PathVariable String userId) {
        try {
            Map<String, Object> exportData = settingsService.exportSettings(userId);
            
            if (exportData != null) {
                return ResponseEntity.ok(exportData);
            } else {
                return ResponseEntity.status(404)
                    .body("No settings found to export for user: " + userId);
            }

        } catch (Exception e) {
            System.err.println("❌ Error exporting settings: " + e.getMessage());
            return ResponseEntity.internalServerError()
                .body("Error exporting settings: " + e.getMessage());
        }
    }

    /**
     * Import user settings
     * 
     * @param userId User ID
     * @param importData Import data map
     * @return Success or error response
     */
    @PostMapping("/{userId}/import")
    public ResponseEntity<?> importSettings(
            @PathVariable String userId,
            @RequestBody Map<String, Object> importData) {
        
        try {
            boolean success = settingsService.importSettings(userId, importData);
            
            if (success) {
                return ResponseEntity.ok("Settings imported successfully");
            } else {
                return ResponseEntity.badRequest()
                    .body("Failed to import settings - check data format");
            }

        } catch (Exception e) {
            System.err.println("❌ Error importing settings: " + e.getMessage());
            return ResponseEntity.internalServerError()
                .body("Error importing settings: " + e.getMessage());
        }
    }

    /**
     * Health check endpoint
     * 
     * @return Health status
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("✅ Settings Service is running!");
    }
}



