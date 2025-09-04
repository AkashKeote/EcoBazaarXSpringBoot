package com.ecobazaar.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;

/**
 * Firebase Configuration Class
 * 
 * This class configures Firebase Admin SDK and Firestore client
 * for the EcoBazaarX backend application.
 * 
 * Features:
 * - Firebase Admin SDK initialization
 * - Firestore client configuration
 * - Service account authentication
 * - Project configuration
 */
@Configuration
public class FirebaseConfig {

    @Value("${firebase.project-id}")
    private String projectId;

    @Value("${firebase.credentials.path}")
    private String credentialsPath;

    /**
     * Initialize Firebase Admin SDK
     * This method runs after the application context is loaded
     */
    @PostConstruct
    public void initialize() {
        try {
            // Check if Firebase is already initialized
            if (FirebaseApp.getApps().isEmpty()) {
                
                InputStream serviceAccount;
                
                // Try to load from file path first, then fallback to classpath
                try {
                    File credentialsFile = new File(credentialsPath);
                    if (credentialsFile.exists() && credentialsFile.isFile()) {
                        serviceAccount = new FileInputStream(credentialsFile);
                        System.out.println("📁 Loading Firebase credentials from file: " + credentialsPath);
                    } else {
                        // Fallback to classpath resource
                        ClassPathResource resource = new ClassPathResource("firebase-service-account.json");
                        serviceAccount = resource.getInputStream();
                        System.out.println("📦 Loading Firebase credentials from classpath");
                    }
                } catch (Exception e) {
                    // Final fallback to classpath
                    ClassPathResource resource = new ClassPathResource("firebase-service-account.json");
                    serviceAccount = resource.getInputStream();
                    System.out.println("📦 Fallback: Loading Firebase credentials from classpath");
                }
                
                // Configure Firebase options
                FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(projectId)
                    .setStorageBucket(projectId + ".appspot.com")
                    .build();
                
                // Initialize Firebase app
                FirebaseApp.initializeApp(options);
                
                System.out.println("✅ Firebase Admin SDK initialized successfully!");
                System.out.println("📍 Project ID: " + projectId);
                
            } else {
                System.out.println("ℹ️ Firebase already initialized");
            }
            
        } catch (IOException e) {
            System.err.println("❌ Error initializing Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Firestore client bean
     * Provides Firestore instance for database operations
     */
    @Bean
    public Firestore firestore() {
        try {
            InputStream serviceAccount;
            
            // Try to load from file path first, then fallback to classpath
            try {
                File credentialsFile = new File(credentialsPath);
                if (credentialsFile.exists() && credentialsFile.isFile()) {
                    serviceAccount = new FileInputStream(credentialsFile);
                    System.out.println("📁 Loading Firestore credentials from file: " + credentialsPath);
                } else {
                    // Fallback to classpath resource
                    ClassPathResource resource = new ClassPathResource("firebase-service-account.json");
                    serviceAccount = resource.getInputStream();
                    System.out.println("📦 Loading Firestore credentials from classpath");
                }
            } catch (Exception e) {
                // Final fallback to classpath
                ClassPathResource resource = new ClassPathResource("firebase-service-account.json");
                serviceAccount = resource.getInputStream();
                System.out.println("📦 Fallback: Loading Firestore credentials from classpath");
            }
            
            FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
            
            Firestore firestore = firestoreOptions.getService();
            System.out.println("✅ Firestore client configured successfully!");
            
            return firestore;
            
        } catch (Exception e) {
            System.err.println("❌ Error configuring Firestore: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}



