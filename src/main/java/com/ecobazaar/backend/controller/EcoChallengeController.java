package com.ecobazaar.backend.controller;

import com.ecobazaar.backend.entity.EcoChallenge;
import com.ecobazaar.backend.repository.EcoChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/eco-challenges")
@CrossOrigin(origins = "*")
public class EcoChallengeController {

    @Autowired
    private EcoChallengeRepository ecoChallengeRepository;

    // Get all eco challenges
    @GetMapping
    public ResponseEntity<List<EcoChallenge>> getAllEcoChallenges() {
        try {
            List<EcoChallenge> challenges = ecoChallengeRepository.findAll();
            return ResponseEntity.ok(challenges);
        } catch (Exception e) {
            System.err.println("Error fetching eco challenges: " + e.getMessage());
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Get active eco challenges
    @GetMapping("/active")
    public ResponseEntity<List<EcoChallenge>> getActiveEcoChallenges() {
        try {
            List<EcoChallenge> challenges = ecoChallengeRepository.findByIsActiveTrue();
            return ResponseEntity.ok(challenges);
        } catch (Exception e) {
            System.err.println("Error fetching active eco challenges: " + e.getMessage());
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Get eco challenge by ID
    @GetMapping("/{challengeId}")
    public ResponseEntity<EcoChallenge> getEcoChallengeById(@PathVariable String challengeId) {
        try {
            Optional<EcoChallenge> challenge = ecoChallengeRepository.findByChallengeId(challengeId);
            if (challenge.isPresent()) {
                return ResponseEntity.ok(challenge.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Error fetching eco challenge: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get eco challenges by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<EcoChallenge>> getEcoChallengesByCategory(@PathVariable String category) {
        try {
            List<EcoChallenge> challenges = ecoChallengeRepository.findByCategory(category);
            return ResponseEntity.ok(challenges);
        } catch (Exception e) {
            System.err.println("Error fetching eco challenges by category: " + e.getMessage());
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    // Add new eco challenge (Admin only)
    @PostMapping
    public ResponseEntity<Map<String, Object>> addEcoChallenge(@RequestBody EcoChallenge ecoChallenge) {
        try {
            // Validate required fields
            if (ecoChallenge.getChallengeId() == null || ecoChallenge.getChallengeId().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Challenge ID is required"
                ));
            }
            
            if (ecoChallenge.getTitle() == null || ecoChallenge.getTitle().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Challenge title is required"
                ));
            }
            
            // Check if challenge ID already exists
            if (ecoChallengeRepository.existsByChallengeId(ecoChallenge.getChallengeId())) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Challenge ID already exists"
                ));
            }
            
            // Set default values if not provided
            if (ecoChallenge.getDescription() == null) {
                ecoChallenge.setDescription("");
            }
            
            if (ecoChallenge.getCategory() == null) {
                ecoChallenge.setCategory("General");
            }
            
            if (ecoChallenge.getDifficulty() == null) {
                ecoChallenge.setDifficulty("Medium");
            }
            
            if (ecoChallenge.getEcoPoints() == null) {
                ecoChallenge.setEcoPoints(0);
            }
            
            if (ecoChallenge.getCarbonSavings() == null) {
                ecoChallenge.setCarbonSavings(0.0);
            }
            
            if (ecoChallenge.getIsActive() == null) {
                ecoChallenge.setIsActive(true);
            }
            
            EcoChallenge savedChallenge = ecoChallengeRepository.save(ecoChallenge);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Eco challenge added successfully",
                "challenge", savedChallenge
            ));
        } catch (Exception e) {
            System.err.println("Error adding eco challenge: " + e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error adding eco challenge: " + e.getMessage()
            ));
        }
    }

    // Update eco challenge (Admin only)
    @PutMapping("/{challengeId}")
    public ResponseEntity<Map<String, Object>> updateEcoChallenge(
            @PathVariable String challengeId, 
            @RequestBody EcoChallenge challengeDetails) {
        try {
            Optional<EcoChallenge> challengeOptional = ecoChallengeRepository.findByChallengeId(challengeId);
            if (challengeOptional.isPresent()) {
                EcoChallenge challenge = challengeOptional.get();
                challenge.setTitle(challengeDetails.getTitle());
                challenge.setDescription(challengeDetails.getDescription());
                challenge.setCategory(challengeDetails.getCategory());
                challenge.setDifficulty(challengeDetails.getDifficulty());
                challenge.setEcoPoints(challengeDetails.getEcoPoints());
                challenge.setCarbonSavings(challengeDetails.getCarbonSavings());
                challenge.setIsActive(challengeDetails.getIsActive());
                challenge.setStartDate(challengeDetails.getStartDate());
                challenge.setEndDate(challengeDetails.getEndDate());
                
                EcoChallenge updatedChallenge = ecoChallengeRepository.save(challenge);
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Eco challenge updated successfully",
                    "challenge", updatedChallenge
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Error updating eco challenge: " + e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error updating eco challenge: " + e.getMessage()
            ));
        }
    }

    // Delete eco challenge (Admin only)
    @DeleteMapping("/{challengeId}")
    public ResponseEntity<Map<String, Object>> deleteEcoChallenge(@PathVariable String challengeId) {
        try {
            Optional<EcoChallenge> challengeOptional = ecoChallengeRepository.findByChallengeId(challengeId);
            if (challengeOptional.isPresent()) {
                ecoChallengeRepository.delete(challengeOptional.get());
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Eco challenge deleted successfully"
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Error deleting eco challenge: " + e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Error deleting eco challenge: " + e.getMessage()
            ));
        }
    }
}
