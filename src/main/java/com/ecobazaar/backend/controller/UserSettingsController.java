package com.ecobazaar.backend.controller;

import com.ecobazaar.backend.entity.UserSettings;
import com.ecobazaar.backend.repository.UserSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-settings")
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
public class UserSettingsController {

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    // Get all user settings
    @GetMapping
    public ResponseEntity<List<UserSettings>> getAllUserSettings() {
        try {
            List<UserSettings> settings = userSettingsRepository.findAll();
            return ResponseEntity.ok(settings);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Get user settings by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserSettings> getUserSettings(@PathVariable String userId) {
        try {
            Optional<UserSettings> settings = userSettingsRepository.findByUserId(userId);
            if (settings.isPresent()) {
                return ResponseEntity.ok(settings.get());
            } else {
                // Return default settings if not found
                UserSettings defaultSettings = new UserSettings();
                defaultSettings.setUserId(userId);
                return ResponseEntity.ok(defaultSettings);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Create or update user settings
    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrUpdateSettings(@RequestBody Map<String, Object> request) {
        try {
            String userId = (String) request.get("userId");
            
            if (userId == null) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Missing required field: userId"
                ));
            }

            // Check if settings already exist
            Optional<UserSettings> existingSettings = userSettingsRepository.findByUserId(userId);
            
            UserSettings userSettings;
            if (existingSettings.isPresent()) {
                userSettings = existingSettings.get();
            } else {
                userSettings = new UserSettings();
                userSettings.setUserId(userId);
                userSettings.setCreatedAt(LocalDateTime.now());
            }

            // Update settings from request
            if (request.containsKey("language")) {
                userSettings.setLanguage((String) request.get("language"));
            }
            if (request.containsKey("theme")) {
                userSettings.setTheme((String) request.get("theme"));
            }
            if (request.containsKey("notificationsEnabled")) {
                userSettings.setNotificationsEnabled((Boolean) request.get("notificationsEnabled"));
            }
            if (request.containsKey("emailNotifications")) {
                userSettings.setEmailNotifications((Boolean) request.get("emailNotifications"));
            }
            if (request.containsKey("pushNotifications")) {
                userSettings.setPushNotifications((Boolean) request.get("pushNotifications"));
            }
            if (request.containsKey("smsNotifications")) {
                userSettings.setSmsNotifications((Boolean) request.get("smsNotifications"));
            }
            if (request.containsKey("ecoTipsEnabled")) {
                userSettings.setEcoTipsEnabled((Boolean) request.get("ecoTipsEnabled"));
            }
            if (request.containsKey("challengeReminders")) {
                userSettings.setChallengeReminders((Boolean) request.get("challengeReminders"));
            }
            if (request.containsKey("orderUpdates")) {
                userSettings.setOrderUpdates((Boolean) request.get("orderUpdates"));
            }
            if (request.containsKey("promotionalEmails")) {
                userSettings.setPromotionalEmails((Boolean) request.get("promotionalEmails"));
            }
            if (request.containsKey("privacyLevel")) {
                userSettings.setPrivacyLevel((String) request.get("privacyLevel"));
            }
            if (request.containsKey("dataSharing")) {
                userSettings.setDataSharing((Boolean) request.get("dataSharing"));
            }
            if (request.containsKey("locationTracking")) {
                userSettings.setLocationTracking((Boolean) request.get("locationTracking"));
            }
            
            userSettings.setUpdatedAt(LocalDateTime.now());

            UserSettings savedSettings = userSettingsRepository.save(userSettings);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "User settings saved successfully",
                "settings", savedSettings
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error saving user settings: " + e.getMessage()
            ));
        }
    }

    // Update specific setting
    @PutMapping("/user/{userId}/{settingKey}")
    public ResponseEntity<Map<String, Object>> updateSpecificSetting(
            @PathVariable String userId,
            @PathVariable String settingKey,
            @RequestBody Map<String, Object> request) {
        try {
            Optional<UserSettings> optionalSettings = userSettingsRepository.findByUserId(userId);
            
            UserSettings userSettings;
            if (optionalSettings.isPresent()) {
                userSettings = optionalSettings.get();
            } else {
                userSettings = new UserSettings();
                userSettings.setUserId(userId);
                userSettings.setCreatedAt(LocalDateTime.now());
            }

            Object value = request.get("value");
            
            // Update the specific setting
            switch (settingKey.toLowerCase()) {
                case "language":
                    userSettings.setLanguage((String) value);
                    break;
                case "theme":
                    userSettings.setTheme((String) value);
                    break;
                case "notificationsenabled":
                    userSettings.setNotificationsEnabled((Boolean) value);
                    break;
                case "emailnotifications":
                    userSettings.setEmailNotifications((Boolean) value);
                    break;
                case "pushnotifications":
                    userSettings.setPushNotifications((Boolean) value);
                    break;
                case "smsnotifications":
                    userSettings.setSmsNotifications((Boolean) value);
                    break;
                case "ecotipsenabled":
                    userSettings.setEcoTipsEnabled((Boolean) value);
                    break;
                case "challengereminders":
                    userSettings.setChallengeReminders((Boolean) value);
                    break;
                case "orderupdates":
                    userSettings.setOrderUpdates((Boolean) value);
                    break;
                case "promotionalemails":
                    userSettings.setPromotionalEmails((Boolean) value);
                    break;
                case "privacylevel":
                    userSettings.setPrivacyLevel((String) value);
                    break;
                case "datasharing":
                    userSettings.setDataSharing((Boolean) value);
                    break;
                case "locationtracking":
                    userSettings.setLocationTracking((Boolean) value);
                    break;
                default:
                    return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "Invalid setting key: " + settingKey
                    ));
            }
            
            userSettings.setUpdatedAt(LocalDateTime.now());
            UserSettings savedSettings = userSettingsRepository.save(userSettings);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Setting updated successfully",
                "settings", savedSettings
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error updating setting: " + e.getMessage()
            ));
        }
    }

    // Delete user settings
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUserSettings(@PathVariable String userId) {
        try {
            Optional<UserSettings> settings = userSettingsRepository.findByUserId(userId);
            if (settings.isPresent()) {
                userSettingsRepository.delete(settings.get());
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "User settings deleted successfully"
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error deleting user settings: " + e.getMessage()
            ));
        }
    }

    // Reset user settings to default
    @PostMapping("/reset/{userId}")
    public ResponseEntity<Map<String, Object>> resetUserSettings(@PathVariable String userId) {
        try {
            Optional<UserSettings> existingSettings = userSettingsRepository.findByUserId(userId);
            
            UserSettings userSettings;
            if (existingSettings.isPresent()) {
                userSettings = existingSettings.get();
            } else {
                userSettings = new UserSettings();
                userSettings.setUserId(userId);
                userSettings.setCreatedAt(LocalDateTime.now());
            }

            // Reset to default values
            userSettings.setLanguage("en");
            userSettings.setTheme("light");
            userSettings.setNotificationsEnabled(true);
            userSettings.setEmailNotifications(true);
            userSettings.setPushNotifications(true);
            userSettings.setSmsNotifications(false);
            userSettings.setEcoTipsEnabled(true);
            userSettings.setChallengeReminders(true);
            userSettings.setOrderUpdates(true);
            userSettings.setPromotionalEmails(false);
            userSettings.setPrivacyLevel("medium");
            userSettings.setDataSharing(false);
            userSettings.setLocationTracking(false);
            userSettings.setUpdatedAt(LocalDateTime.now());

            UserSettings savedSettings = userSettingsRepository.save(userSettings);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "User settings reset to default successfully",
                "settings", savedSettings
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error resetting user settings: " + e.getMessage()
            ));
        }
    }
}
