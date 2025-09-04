package com.ecobazaar.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Privacy Settings Model Class
 * 
 * Represents user privacy and security preferences for EcoBazaarX
 */
public class PrivacySettings {

    @JsonProperty("locationEnabled")
    private boolean locationEnabled;

    @JsonProperty("dataCollectionEnabled")
    private boolean dataCollectionEnabled;

    @JsonProperty("biometricEnabled")
    private boolean biometricEnabled;

    // Default constructor
    public PrivacySettings() {
        // Default values
        this.locationEnabled = true;
        this.dataCollectionEnabled = true;
        this.biometricEnabled = false;
    }

    // Constructor with all fields
    public PrivacySettings(boolean locationEnabled, boolean dataCollectionEnabled, boolean biometricEnabled) {
        this.locationEnabled = locationEnabled;
        this.dataCollectionEnabled = dataCollectionEnabled;
        this.biometricEnabled = biometricEnabled;
    }

    // Getters and Setters
    public boolean isLocationEnabled() { return locationEnabled; }
    public void setLocationEnabled(boolean locationEnabled) { this.locationEnabled = locationEnabled; }

    public boolean isDataCollectionEnabled() { return dataCollectionEnabled; }
    public void setDataCollectionEnabled(boolean dataCollectionEnabled) { this.dataCollectionEnabled = dataCollectionEnabled; }

    public boolean isBiometricEnabled() { return biometricEnabled; }
    public void setBiometricEnabled(boolean biometricEnabled) { this.biometricEnabled = biometricEnabled; }

    @Override
    public String toString() {
        return "PrivacySettings{" +
                "locationEnabled=" + locationEnabled +
                ", dataCollectionEnabled=" + dataCollectionEnabled +
                ", biometricEnabled=" + biometricEnabled +
                '}';
    }
}





