package com.ecobazaar.backend;

import com.ecobazaar.backend.service.DataMigrationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Migration Runner - A simple way to run data migration from command line
 * This bypasses the REST API and runs migration directly
 */
@SpringBootApplication
public class MigrationRunner {

    public static void main(String[] args) {
        SpringApplication.run(MigrationRunner.class, args);
    }

    @Bean
    public CommandLineRunner runMigration(DataMigrationService migrationService) {
        return args -> {
            if (args.length > 0 && "migrate".equals(args[0])) {
                System.out.println("Starting data migration from Firestore exports...");
                try {
                    String result = migrationService.migrateAllData();
                    System.out.println(result);
                    System.out.println("Migration completed successfully!");
                } catch (Exception e) {
                    System.err.println("Migration failed: " + e.getMessage());
                    e.printStackTrace();
                }
                System.exit(0);
            }
        };
    }
}
