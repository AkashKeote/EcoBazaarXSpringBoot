package com.ecobazaar.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Settings Model Class
 * 
 * Represents user settings data structure for EcoBazaarX
 * Includes notification preferences, privacy settings, and app preferences
 */
public class Settings {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("notifications")
    private NotificationSettings notifications;

    @JsonProperty("privacy")
    private PrivacySettings privacy;

    @JsonProperty("preferences")
    private AppPreferences preferences;

    @JsonProperty("app")
    private AppSettings app;

    @JsonProperty("sync")
    private SyncSettings sync;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("lastUpdated")
    private String lastUpdated;

    // Default constructor
    public Settings() {}

    // Constructor with all fields
    public Settings(String userId, NotificationSettings notifications, 
                   PrivacySettings privacy, AppPreferences preferences, 
                   AppSettings app, SyncSettings sync) {
        this.userId = userId;
        this.notifications = notifications;
        this.privacy = privacy;
        this.preferences = preferences;
        this.app = app;
        this.sync = sync;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public NotificationSettings getNotifications() { return notifications; }
    public void setNotifications(NotificationSettings notifications) { this.notifications = notifications; }

    public PrivacySettings getPrivacy() { return privacy; }
    public void setPrivacy(PrivacySettings privacy) { this.privacy = privacy; }

    public AppPreferences getPreferences() { return preferences; }
    public void setPreferences(AppPreferences preferences) { this.preferences = preferences; }

    public AppSettings getApp() { return app; }
    public void setApp(AppSettings app) { this.app = app; }

    public SyncSettings getSync() { return sync; }
    public void setSync(SyncSettings sync) { this.sync = sync; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }

    @Override
    public String toString() {
        return "Settings{" +
                "userId='" + userId + '\'' +
                ", notifications=" + notifications +
                ", privacy=" + privacy +
                ", preferences=" + preferences +
                ", app=" + app +
                ", sync=" + sync +
                ", createdAt='" + createdAt + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                '}';
    }
}





