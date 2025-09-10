package com.ecobazaar.backend.controller;

import com.ecobazaar.backend.entity.ChallengeStatus;
import com.ecobazaar.backend.entity.EcoChallenge;
import com.ecobazaar.backend.entity.UserChallenge;
import com.ecobazaar.backend.service.EcoChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eco-challenges")
public class EcoChallengeController {
    
    @Autowired
    private EcoChallengeService ecoChallengeService;
    
    // Get all challenges
    @GetMapping
    public ResponseEntity<List<EcoChallenge>> getAllChallenges() {
        List<EcoChallenge> challenges = ecoChallengeService.getAllChallenges();
        return ResponseEntity.ok(challenges);
    }
    
    // Get active challenges
    @GetMapping("/active")
    public ResponseEntity<List<EcoChallenge>> getActiveChallenges() {
        List<EcoChallenge> challenges = ecoChallengeService.getActiveChallenges();
        return ResponseEntity.ok(challenges);
    }
    
    // Get currently active challenges (within date range)
    @GetMapping("/currently-active")
    public ResponseEntity<List<EcoChallenge>> getCurrentlyActiveChallenges() {
        List<EcoChallenge> challenges = ecoChallengeService.getCurrentlyActiveChallenges();
        return ResponseEntity.ok(challenges);
    }
    
    // Get challenge by ID
    @GetMapping("/{id}")
    public ResponseEntity<EcoChallenge> getChallengeById(@PathVariable Long id) {
        Optional<EcoChallenge> challenge = ecoChallengeService.getChallengeById(id);
        return challenge.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }
    
    // Get challenges by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<EcoChallenge>> getChallengesByCategory(@PathVariable String category) {
        List<EcoChallenge> challenges = ecoChallengeService.getChallengesByCategory(category);
        return ResponseEntity.ok(challenges);
    }
    
    // Get challenges by difficulty
    @GetMapping("/difficulty/{difficultyLevel}")
    public ResponseEntity<List<EcoChallenge>> getChallengesByDifficulty(@PathVariable String difficultyLevel) {
        List<EcoChallenge> challenges = ecoChallengeService.getChallengesByDifficulty(difficultyLevel);
        return ResponseEntity.ok(challenges);
    }
    
    // Get challenges by points range
    @GetMapping("/points")
    public ResponseEntity<List<EcoChallenge>> getChallengesByPointsRange(
            @RequestParam Integer minPoints, 
            @RequestParam Integer maxPoints) {
        List<EcoChallenge> challenges = ecoChallengeService.getChallengesByPointsRange(minPoints, maxPoints);
        return ResponseEntity.ok(challenges);
    }
    
    // Get challenges by duration range
    @GetMapping("/duration")
    public ResponseEntity<List<EcoChallenge>> getChallengesByDurationRange(
            @RequestParam Integer minDuration, 
            @RequestParam Integer maxDuration) {
        List<EcoChallenge> challenges = ecoChallengeService.getChallengesByDurationRange(minDuration, maxDuration);
        return ResponseEntity.ok(challenges);
    }
    
    // Get challenges with available spots
    @GetMapping("/available")
    public ResponseEntity<List<EcoChallenge>> getChallengesWithAvailableSpots() {
        List<EcoChallenge> challenges = ecoChallengeService.getChallengesWithAvailableSpots();
        return ResponseEntity.ok(challenges);
    }
    
    // Search challenges by title
    @GetMapping("/search")
    public ResponseEntity<List<EcoChallenge>> searchChallengesByTitle(@RequestParam String title) {
        List<EcoChallenge> challenges = ecoChallengeService.searchChallengesByTitle(title);
        return ResponseEntity.ok(challenges);
    }
    
    // Get challenges by multiple criteria
    @GetMapping("/filter")
    public ResponseEntity<List<EcoChallenge>> getChallengesByCriteria(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficultyLevel,
            @RequestParam(required = false) Integer minPoints,
            @RequestParam(required = false) Integer maxPoints) {
        List<EcoChallenge> challenges = ecoChallengeService.getChallengesByCriteria(
                category, difficultyLevel, minPoints, maxPoints);
        return ResponseEntity.ok(challenges);
    }
    
    // Create new challenge (Admin only)
    @PostMapping
    public ResponseEntity<EcoChallenge> createChallenge(@RequestBody EcoChallenge challenge) {
        try {
            EcoChallenge createdChallenge = ecoChallengeService.createChallenge(challenge);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdChallenge);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // Update challenge (Admin only)
    @PutMapping("/{id}")
    public ResponseEntity<EcoChallenge> updateChallenge(@PathVariable Long id, @RequestBody EcoChallenge challenge) {
        try {
            EcoChallenge updatedChallenge = ecoChallengeService.updateChallenge(id, challenge);
            if (updatedChallenge != null) {
                return ResponseEntity.ok(updatedChallenge);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // Delete challenge (Admin only)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable Long id) {
        boolean deleted = ecoChallengeService.deleteChallenge(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    
    // Deactivate challenge (Admin only)
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateChallenge(@PathVariable Long id) {
        boolean deactivated = ecoChallengeService.deactivateChallenge(id);
        return deactivated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
    
    // User Challenge endpoints
    
    // Get user challenges
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserChallenge>> getUserChallenges(@PathVariable Long userId) {
        List<UserChallenge> userChallenges = ecoChallengeService.getUserChallenges(userId);
        return ResponseEntity.ok(userChallenges);
    }
    
    // Get user challenges by status
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<UserChallenge>> getUserChallengesByStatus(
            @PathVariable Long userId, 
            @PathVariable ChallengeStatus status) {
        List<UserChallenge> userChallenges = ecoChallengeService.getUserChallengesByStatus(userId, status);
        return ResponseEntity.ok(userChallenges);
    }
    
    // Get completed user challenges
    @GetMapping("/user/{userId}/completed")
    public ResponseEntity<List<UserChallenge>> getCompletedUserChallenges(@PathVariable Long userId) {
        List<UserChallenge> userChallenges = ecoChallengeService.getCompletedUserChallenges(userId);
        return ResponseEntity.ok(userChallenges);
    }
    
    // Get in-progress user challenges
    @GetMapping("/user/{userId}/in-progress")
    public ResponseEntity<List<UserChallenge>> getInProgressUserChallenges(@PathVariable Long userId) {
        List<UserChallenge> userChallenges = ecoChallengeService.getInProgressUserChallenges(userId);
        return ResponseEntity.ok(userChallenges);
    }
    
    // Get specific user challenge
    @GetMapping("/user/{userId}/challenge/{challengeId}")
    public ResponseEntity<UserChallenge> getUserChallenge(
            @PathVariable Long userId, 
            @PathVariable Long challengeId) {
        Optional<UserChallenge> userChallenge = ecoChallengeService.getUserChallenge(userId, challengeId);
        return userChallenge.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }
    
    // Join challenge
    @PostMapping("/user/{userId}/join/{challengeId}")
    public ResponseEntity<UserChallenge> joinChallenge(
            @PathVariable Long userId, 
            @PathVariable Long challengeId) {
        try {
            UserChallenge userChallenge = ecoChallengeService.joinChallenge(userId, challengeId);
            return ResponseEntity.status(HttpStatus.CREATED).body(userChallenge);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // Update challenge progress
    @PutMapping("/user/{userId}/challenge/{challengeId}/progress")
    public ResponseEntity<UserChallenge> updateChallengeProgress(
            @PathVariable Long userId, 
            @PathVariable Long challengeId,
            @RequestParam Integer progressPercentage,
            @RequestParam(required = false) String notes) {
        try {
            UserChallenge userChallenge = ecoChallengeService.updateChallengeProgress(
                    userId, challengeId, progressPercentage, notes);
            return ResponseEntity.ok(userChallenge);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // Complete challenge
    @PutMapping("/user/{userId}/challenge/{challengeId}/complete")
    public ResponseEntity<UserChallenge> completeChallenge(
            @PathVariable Long userId, 
            @PathVariable Long challengeId,
            @RequestParam(required = false) String proofUrl) {
        try {
            UserChallenge userChallenge = ecoChallengeService.completeChallenge(userId, challengeId, proofUrl);
            return ResponseEntity.ok(userChallenge);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // Cancel challenge
    @PutMapping("/user/{userId}/challenge/{challengeId}/cancel")
    public ResponseEntity<UserChallenge> cancelChallenge(
            @PathVariable Long userId, 
            @PathVariable Long challengeId) {
        try {
            UserChallenge userChallenge = ecoChallengeService.cancelChallenge(userId, challengeId);
            return ResponseEntity.ok(userChallenge);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // Get total points earned by user
    @GetMapping("/user/{userId}/total-points")
    public ResponseEntity<Integer> getTotalPointsEarned(@PathVariable Long userId) {
        Integer totalPoints = ecoChallengeService.getTotalPointsEarned(userId);
        return ResponseEntity.ok(totalPoints);
    }
    
    // Get challenge participant count
    @GetMapping("/{challengeId}/participants")
    public ResponseEntity<Long> getChallengeParticipantCount(@PathVariable Long challengeId) {
        long count = ecoChallengeService.getChallengeParticipantCount(challengeId);
        return ResponseEntity.ok(count);
    }
    
    // Get completed participant count
    @GetMapping("/{challengeId}/completed-participants")
    public ResponseEntity<Long> getCompletedParticipantCount(@PathVariable Long challengeId) {
        long count = ecoChallengeService.getCompletedParticipantCount(challengeId);
        return ResponseEntity.ok(count);
    }
}
