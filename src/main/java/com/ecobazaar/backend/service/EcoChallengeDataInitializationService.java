package com.ecobazaar.backend.service;

import com.ecobazaar.backend.entity.EcoChallenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class EcoChallengeDataInitializationService implements CommandLineRunner {

    @Autowired
    private EcoChallengeService ecoChallengeService;

    @Override
    public void run(String... args) throws Exception {
        initializeSampleChallenges();
    }

    private void initializeSampleChallenges() {
        try {
            // Check if challenges already exist
            List<EcoChallenge> existingChallenges = ecoChallengeService.getAllChallenges();
            if (!existingChallenges.isEmpty()) {
                System.out.println("✅ Eco challenges already exist (" + existingChallenges.size() + " challenges)");
                return;
            }

            System.out.println("🌱 Initializing sample eco challenges...");

            List<EcoChallenge> sampleChallenges = Arrays.asList(
                createChallenge("WALK_BIKE_1", "Walk or Bike to Work", 
                    "Replace your daily commute with walking or biking at least 3 times this week", 
                    "Transportation", "Easy", 15, 2.5),
                
                createChallenge("PLASTIC_FREE_1", "Plastic-Free Day", 
                    "Go one full day without using any single-use plastic items", 
                    "Waste Reduction", "Medium", 20, 1.8),
                
                createChallenge("ENERGY_SAVE_1", "Energy Conservation Week", 
                    "Reduce your electricity usage by 20% this week compared to last week", 
                    "Energy", "Medium", 25, 3.2),
                
                createChallenge("WATER_SAVE_1", "Water Saving Challenge", 
                    "Reduce your daily water usage by 30% for 7 consecutive days", 
                    "Water Conservation", "Easy", 18, 1.5),
                
                createChallenge("ZERO_WASTE_1", "Zero Waste Day", 
                    "Produce zero waste for an entire day - no trash, only recyclables", 
                    "Waste Reduction", "Hard", 35, 4.0),
                
                createChallenge("LOCAL_FOOD_1", "Local Food Week", 
                    "Eat only locally sourced food for 7 days", 
                    "Food", "Medium", 22, 2.8),
                
                createChallenge("DIGITAL_DETOX_1", "Digital Detox Weekend", 
                    "Spend 48 hours without using any digital devices", 
                    "Lifestyle", "Hard", 30, 1.2),
                
                createChallenge("GARDEN_START_1", "Start a Garden", 
                    "Plant and maintain a small herb or vegetable garden", 
                    "Gardening", "Easy", 20, 1.0),
                
                createChallenge("CAR_SHARE_1", "Car Sharing Week", 
                    "Use carpooling or public transport instead of driving alone", 
                    "Transportation", "Medium", 25, 3.5),
                
                createChallenge("REUSABLE_1", "Reusable Everything", 
                    "Use only reusable items (bags, bottles, containers) for 2 weeks", 
                    "Waste Reduction", "Medium", 28, 2.2)
            );

            for (EcoChallenge challenge : sampleChallenges) {
                if (!ecoChallengeService.challengeExists(challenge.getChallengeId())) {
                    ecoChallengeService.saveChallenge(challenge);
                    System.out.println("✅ Added challenge: " + challenge.getTitle());
                }
            }

            System.out.println("🎉 Successfully initialized " + sampleChallenges.size() + " eco challenges!");
            
        } catch (Exception e) {
            System.err.println("❌ Error initializing eco challenges: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private EcoChallenge createChallenge(String challengeId, String title, String description, 
                                       String category, String difficulty, int ecoPoints, double carbonSavings) {
        EcoChallenge challenge = new EcoChallenge();
        challenge.setChallengeId(challengeId);
        challenge.setTitle(title);
        challenge.setDescription(description);
        challenge.setCategory(category);
        challenge.setDifficulty(difficulty);
        challenge.setEcoPoints(ecoPoints);
        challenge.setCarbonSavings(carbonSavings);
        challenge.setIsActive(true);
        challenge.setStartDate(LocalDateTime.now());
        challenge.setEndDate(LocalDateTime.now().plusDays(30)); // 30 days from now
        return challenge;
    }
}
