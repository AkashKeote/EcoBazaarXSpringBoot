package com.ecobazaar.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Sync Settings Model Class
 * 
 * Represents synchronization settings for EcoBazaarX
 */
public class SyncSettings {

    @JsonProperty("autoSync")
    private boolean autoSync;

    @JsonProperty("offlineMode")
    private boolean offlineMode;

    // Default constructor
    public SyncSettings() {
        // Default values
        this.autoSync = true;
        this.offlineMode = false;
    }

    // Constructor with all fields
    public SyncSettings(boolean autoSync, boolean offlineMode) {
        this.autoSync = autoSync;
        this.offlineMode = offlineMode;
    }

    // Getters and Setters
    public boolean isAutoSync() { return autoSync; }
    public void setAutoSync(boolean autoSync) { this.autoSync = autoSync; }

    public boolean isOfflineMode() { return offlineMode; }
    public void setOfflineMode(boolean offlineMode) { this.offlineMode = offlineMode; }

    @Override
    public String toString() {
        return "SyncSettings{" +
                "autoSync=" + autoSync +
                ", offlineMode=" + offlineMode +
                '}';
    }
}





