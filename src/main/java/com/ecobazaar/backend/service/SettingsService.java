package com.ecobazaar.backend.service;

import com.ecobazaar.backend.model.*;
import com.ecobazaar.backend.entity.UserSettings;
import com.ecobazaar.backend.repository.UserSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Settings Service Class
 * 
 * Handles all settings-related business logic and MySQL operations
 * for the EcoBazaarX backend application.
 */
@Service
public class SettingsService {

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Get user settings from MySQL
     * 
     * @param userId User ID
     * @return Settings object or null if not found
     */
    @Cacheable(value = "settings", key = "#userId")
    public Settings getUserSettings(String userId) {
        try {
            Optional<UserSettings> userSettingsOpt = userSettingsRepository.findByUserId(userId);
            
            if (userSettingsOpt.isPresent()) {
                UserSettings userSettings = userSettingsOpt.get();
                return mapToSettings(userId, userSettings);
            }

            // Return default settings if user doesn't exist
            return createDefaultSettings(userId);

        } catch (Exception e) {
            System.err.println("Error getting user settings: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Update user settings in MySQL
     * 
     * @param userId User ID
     * @param settings Settings object to update
     * @return Updated Settings object
     */
    @CacheEvict(value = "settings", key = "#userId")
    public Settings updateUserSettings(String userId, Settings settings) {
        try {
            Optional<UserSettings> userSettingsOpt = userSettingsRepository.findByUserId(userId);
            UserSettings userSettings;
            
            if (userSettingsOpt.isPresent()) {
                userSettings = userSettingsOpt.get();
            } else {
                userSettings = new UserSettings(userId);
            }
            
            // Update settings
            updateUserSettingsFromModel(userSettings, settings);
            userSettings = userSettingsRepository.save(userSettings);
            
            return mapToSettings(userId, userSettings);
            
        } catch (Exception e) {
            System.err.println("Error updating user settings: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to update user settings", e);
        }
    }

    /**
     * Update app preferences
     * 
     * @param userId User ID
     * @param preferences AppPreferences object
     * @return Updated Settings object
     */
    @CacheEvict(value = "settings", key = "#userId")
    public Settings updateAppPreferences(String userId, AppPreferences preferences) {
        try {
            Optional<UserSettings> userSettingsOpt = userSettingsRepository.findByUserId(userId);
            UserSettings userSettings;
            
            if (userSettingsOpt.isPresent()) {
                userSettings = userSettingsOpt.get();
            } else {
                userSettings = new UserSettings(userId);
            }
            
            // Update app preferences
            if (preferences.getTheme() != null) {
                userSettings.setTheme(preferences.getTheme());
            }
            if (preferences.getLanguage() != null) {
                userSettings.setLanguage(preferences.getLanguage());
            }
            
            userSettings = userSettingsRepository.save(userSettings);
            return mapToSettings(userId, userSettings);
            
        } catch (Exception e) {
            System.err.println("Error updating app preferences: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to update app preferences", e);
        }
    }

    /**
     * Update notification settings
     * 
     * @param userId User ID
     * @param notificationSettings NotificationSettings object
     * @return Updated Settings object
     */
    @CacheEvict(value = "settings", key = "#userId")
    public Settings updateNotificationSettings(String userId, NotificationSettings notificationSettings) {
        try {
            Optional<UserSettings> userSettingsOpt = userSettingsRepository.findByUserId(userId);
            UserSettings userSettings;
            
            if (userSettingsOpt.isPresent()) {
                userSettings = userSettingsOpt.get();
            } else {
                userSettings = new UserSettings(userId);
            }
            
            // Update notification settings
            if (notificationSettings.getNotificationsEnabled() != null) {
                userSettings.setNotificationsEnabled(notificationSettings.getNotificationsEnabled());
            }
            if (notificationSettings.getEmailNotifications() != null) {
                userSettings.setEmailNotifications(notificationSettings.getEmailNotifications());
            }
            if (notificationSettings.getPushNotifications() != null) {
                userSettings.setPushNotifications(notificationSettings.getPushNotifications());
            }
            if (notificationSettings.getSmsNotifications() != null) {
                userSettings.setSmsNotifications(notificationSettings.getSmsNotifications());
            }
            if (notificationSettings.getEcoTipsEnabled() != null) {
                userSettings.setEcoTipsEnabled(notificationSettings.getEcoTipsEnabled());
            }
            if (notificationSettings.getChallengeReminders() != null) {
                userSettings.setChallengeReminders(notificationSettings.getChallengeReminders());
            }
            if (notificationSettings.getOrderUpdates() != null) {
                userSettings.setOrderUpdates(notificationSettings.getOrderUpdates());
            }
            if (notificationSettings.getPromotionalEmails() != null) {
                userSettings.setPromotionalEmails(notificationSettings.getPromotionalEmails());
            }
            
            userSettings = userSettingsRepository.save(userSettings);
            return mapToSettings(userId, userSettings);
            
        } catch (Exception e) {
            System.err.println("Error updating notification settings: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to update notification settings", e);
        }
    }

    /**
     * Update privacy settings
     * 
     * @param userId User ID
     * @param privacySettings PrivacySettings object
     * @return Updated Settings object
     */
    @CacheEvict(value = "settings", key = "#userId")
    public Settings updatePrivacySettings(String userId, PrivacySettings privacySettings) {
        try {
            Optional<UserSettings> userSettingsOpt = userSettingsRepository.findByUserId(userId);
            UserSettings userSettings;
            
            if (userSettingsOpt.isPresent()) {
                userSettings = userSettingsOpt.get();
            } else {
                userSettings = new UserSettings(userId);
            }
            
            // Update privacy settings
            if (privacySettings.getPrivacyLevel() != null) {
                userSettings.setPrivacyLevel(privacySettings.getPrivacyLevel());
            }
            if (privacySettings.getDataSharing() != null) {
                userSettings.setDataSharing(privacySettings.getDataSharing());
            }
            if (privacySettings.getLocationTracking() != null) {
                userSettings.setLocationTracking(privacySettings.getLocationTracking());
            }
            
            userSettings = userSettingsRepository.save(userSettings);
            return mapToSettings(userId, userSettings);
            
        } catch (Exception e) {
            System.err.println("Error updating privacy settings: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to update privacy settings", e);
        }
    }

    /**
     * Update sync settings
     * 
     * @param userId User ID
     * @param syncSettings SyncSettings object
     * @return Updated Settings object
     */
    @CacheEvict(value = "settings", key = "#userId")
    public Settings updateSyncSettings(String userId, SyncSettings syncSettings) {
        try {
            // For now, sync settings are handled at the app level
            // This method can be extended based on specific sync requirements
            return getUserSettings(userId);
            
        } catch (Exception e) {
            System.err.println("Error updating sync settings: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to update sync settings", e);
        }
    }

    /**
     * Map UserSettings entity to Settings model
     */
    private Settings mapToSettings(String userId, UserSettings userSettings) {
        Settings settings = new Settings();
        settings.setUserId(userId);
        settings.setLastUpdated(LocalDateTime.now().format(formatter));
        
        // App Preferences
        AppPreferences appPreferences = new AppPreferences();
        appPreferences.setTheme(userSettings.getTheme());
        appPreferences.setLanguage(userSettings.getLanguage());
        settings.setAppPreferences(appPreferences);
        
        // Notification Settings
        NotificationSettings notificationSettings = new NotificationSettings();
        notificationSettings.setNotificationsEnabled(userSettings.getNotificationsEnabled());
        notificationSettings.setEmailNotifications(userSettings.getEmailNotifications());
        notificationSettings.setPushNotifications(userSettings.getPushNotifications());
        notificationSettings.setSmsNotifications(userSettings.getSmsNotifications());
        notificationSettings.setEcoTipsEnabled(userSettings.getEcoTipsEnabled());
        notificationSettings.setChallengeReminders(userSettings.getChallengeReminders());
        notificationSettings.setOrderUpdates(userSettings.getOrderUpdates());
        notificationSettings.setPromotionalEmails(userSettings.getPromotionalEmails());
        settings.setNotificationSettings(notificationSettings);
        
        // Privacy Settings
        PrivacySettings privacySettings = new PrivacySettings();
        privacySettings.setPrivacyLevel(userSettings.getPrivacyLevel());
        privacySettings.setDataSharing(userSettings.getDataSharing());
        privacySettings.setLocationTracking(userSettings.getLocationTracking());
        settings.setPrivacySettings(privacySettings);
        
        // Sync Settings (default values)
        SyncSettings syncSettings = new SyncSettings();
        syncSettings.setAutoSync(true);
        syncSettings.setSyncFrequency("daily");
        syncSettings.setLastSync(LocalDateTime.now().format(formatter));
        settings.setSyncSettings(syncSettings);
        
        return settings;
    }

    /**
     * Update UserSettings entity from Settings model
     */
    private void updateUserSettingsFromModel(UserSettings userSettings, Settings settings) {
        if (settings.getAppPreferences() != null) {
            AppPreferences appPrefs = settings.getAppPreferences();
            if (appPrefs.getTheme() != null) {
                userSettings.setTheme(appPrefs.getTheme());
            }
            if (appPrefs.getLanguage() != null) {
                userSettings.setLanguage(appPrefs.getLanguage());
            }
        }
        
        if (settings.getNotificationSettings() != null) {
            NotificationSettings notifSettings = settings.getNotificationSettings();
            if (notifSettings.getNotificationsEnabled() != null) {
                userSettings.setNotificationsEnabled(notifSettings.getNotificationsEnabled());
            }
            if (notifSettings.getEmailNotifications() != null) {
                userSettings.setEmailNotifications(notifSettings.getEmailNotifications());
            }
            if (notifSettings.getPushNotifications() != null) {
                userSettings.setPushNotifications(notifSettings.getPushNotifications());
            }
            if (notifSettings.getSmsNotifications() != null) {
                userSettings.setSmsNotifications(notifSettings.getSmsNotifications());
            }
            if (notifSettings.getEcoTipsEnabled() != null) {
                userSettings.setEcoTipsEnabled(notifSettings.getEcoTipsEnabled());
            }
            if (notifSettings.getChallengeReminders() != null) {
                userSettings.setChallengeReminders(notifSettings.getChallengeReminders());
            }
            if (notifSettings.getOrderUpdates() != null) {
                userSettings.setOrderUpdates(notifSettings.getOrderUpdates());
            }
            if (notifSettings.getPromotionalEmails() != null) {
                userSettings.setPromotionalEmails(notifSettings.getPromotionalEmails());
            }
        }
        
        if (settings.getPrivacySettings() != null) {
            PrivacySettings privacySettings = settings.getPrivacySettings();
            if (privacySettings.getPrivacyLevel() != null) {
                userSettings.setPrivacyLevel(privacySettings.getPrivacyLevel());
            }
            if (privacySettings.getDataSharing() != null) {
                userSettings.setDataSharing(privacySettings.getDataSharing());
            }
            if (privacySettings.getLocationTracking() != null) {
                userSettings.setLocationTracking(privacySettings.getLocationTracking());
            }
        }
    }

    /**
     * Create default settings for a new user
     */
    private Settings createDefaultSettings(String userId) {
        Settings settings = new Settings();
        settings.setUserId(userId);
        settings.setLastUpdated(LocalDateTime.now().format(formatter));
        
        // Default App Preferences
        AppPreferences appPreferences = new AppPreferences();
        appPreferences.setTheme("light");
        appPreferences.setLanguage("en");
        settings.setAppPreferences(appPreferences);
        
        // Default Notification Settings
        NotificationSettings notificationSettings = new NotificationSettings();
        notificationSettings.setNotificationsEnabled(true);
        notificationSettings.setEmailNotifications(true);
        notificationSettings.setPushNotifications(true);
        notificationSettings.setSmsNotifications(false);
        notificationSettings.setEcoTipsEnabled(true);
        notificationSettings.setChallengeReminders(true);
        notificationSettings.setOrderUpdates(true);
        notificationSettings.setPromotionalEmails(false);
        settings.setNotificationSettings(notificationSettings);
        
        // Default Privacy Settings
        PrivacySettings privacySettings = new PrivacySettings();
        privacySettings.setPrivacyLevel("medium");
        privacySettings.setDataSharing(false);
        privacySettings.setLocationTracking(false);
        settings.setPrivacySettings(privacySettings);
        
        // Default Sync Settings
        SyncSettings syncSettings = new SyncSettings();
        syncSettings.setAutoSync(true);
        syncSettings.setSyncFrequency("daily");
        syncSettings.setLastSync(LocalDateTime.now().format(formatter));
        settings.setSyncSettings(syncSettings);
        
        return settings;
    }
}