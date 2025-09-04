package com.ecobazaar.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Notification Settings Model Class
 * 
 * Represents user notification preferences for EcoBazaarX
 */
public class NotificationSettings {

    @JsonProperty("pushNotificationsEnabled")
    private boolean pushNotificationsEnabled;

    @JsonProperty("orderNotificationsEnabled")
    private boolean orderNotificationsEnabled;

    @JsonProperty("ecoTipsEnabled")
    private boolean ecoTipsEnabled;

    @JsonProperty("promotionalNotificationsEnabled")
    private boolean promotionalNotificationsEnabled;

    @JsonProperty("carbonTrackingEnabled")
    private boolean carbonTrackingEnabled;

    // Default constructor
    public NotificationSettings() {
        // Default values
        this.pushNotificationsEnabled = true;
        this.orderNotificationsEnabled = true;
        this.ecoTipsEnabled = true;
        this.promotionalNotificationsEnabled = true;
        this.carbonTrackingEnabled = true;
    }

    // Constructor with all fields
    public NotificationSettings(boolean pushNotificationsEnabled, boolean orderNotificationsEnabled,
                              boolean ecoTipsEnabled, boolean promotionalNotificationsEnabled,
                              boolean carbonTrackingEnabled) {
        this.pushNotificationsEnabled = pushNotificationsEnabled;
        this.orderNotificationsEnabled = orderNotificationsEnabled;
        this.ecoTipsEnabled = ecoTipsEnabled;
        this.promotionalNotificationsEnabled = promotionalNotificationsEnabled;
        this.carbonTrackingEnabled = carbonTrackingEnabled;
    }

    // Getters and Setters
    public boolean isPushNotificationsEnabled() { return pushNotificationsEnabled; }
    public void setPushNotificationsEnabled(boolean pushNotificationsEnabled) { this.pushNotificationsEnabled = pushNotificationsEnabled; }

    public boolean isOrderNotificationsEnabled() { return orderNotificationsEnabled; }
    public void setOrderNotificationsEnabled(boolean orderNotificationsEnabled) { this.orderNotificationsEnabled = orderNotificationsEnabled; }

    public boolean isEcoTipsEnabled() { return ecoTipsEnabled; }
    public void setEcoTipsEnabled(boolean ecoTipsEnabled) { this.ecoTipsEnabled = ecoTipsEnabled; }

    public boolean isPromotionalNotificationsEnabled() { return promotionalNotificationsEnabled; }
    public void setPromotionalNotificationsEnabled(boolean promotionalNotificationsEnabled) { this.promotionalNotificationsEnabled = promotionalNotificationsEnabled; }

    public boolean isCarbonTrackingEnabled() { return carbonTrackingEnabled; }
    public void setCarbonTrackingEnabled(boolean carbonTrackingEnabled) { this.carbonTrackingEnabled = carbonTrackingEnabled; }

    @Override
    public String toString() {
        return "NotificationSettings{" +
                "pushNotificationsEnabled=" + pushNotificationsEnabled +
                ", orderNotificationsEnabled=" + orderNotificationsEnabled +
                ", ecoTipsEnabled=" + ecoTipsEnabled +
                ", promotionalNotificationsEnabled=" + promotionalNotificationsEnabled +
                ", carbonTrackingEnabled=" + carbonTrackingEnabled +
                '}';
    }
}



