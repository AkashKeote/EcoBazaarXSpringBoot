package com.ecobazaar.backend.service;

import com.ecobazaar.backend.entity.ChallengeStatus;
import com.ecobazaar.backend.entity.EcoChallenge;
import com.ecobazaar.backend.entity.UserChallenge;
import com.ecobazaar.backend.repository.EcoChallengeRepository;
import com.ecobazaar.backend.repository.UserChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EcoChallengeService {
    
    @Autowired
    private EcoChallengeRepository ecoChallengeRepository;
    
    @Autowired
    private UserChallengeRepository userChallengeRepository;
    
    // EcoChallenge CRUD operations
    public List<EcoChallenge> getAllChallenges() {
        return ecoChallengeRepository.findAll();
    }
    
    public List<EcoChallenge> getActiveChallenges() {
        return ecoChallengeRepository.findByIsActiveTrue();
    }
    
    public List<EcoChallenge> getCurrentlyActiveChallenges() {
        return ecoChallengeRepository.findCurrentlyActiveChallenges(LocalDateTime.now());
    }
    
    public Optional<EcoChallenge> getChallengeById(Long id) {
        return ecoChallengeRepository.findById(id);
    }
    
    public List<EcoChallenge> getChallengesByCategory(String category) {
        return ecoChallengeRepository.findByCategoryAndIsActiveTrue(category);
    }
    
    public List<EcoChallenge> getChallengesByDifficulty(String difficultyLevel) {
        return ecoChallengeRepository.findByDifficultyLevelAndIsActiveTrue(difficultyLevel);
    }
    
    public List<EcoChallenge> getChallengesByPointsRange(Integer minPoints, Integer maxPoints) {
        return ecoChallengeRepository.findByPointsRange(minPoints, maxPoints);
    }
    
    public List<EcoChallenge> getChallengesByDurationRange(Integer minDuration, Integer maxDuration) {
        return ecoChallengeRepository.findByDurationRange(minDuration, maxDuration);
    }
    
    public List<EcoChallenge> getChallengesWithAvailableSpots() {
        return ecoChallengeRepository.findChallengesWithAvailableSpots();
    }
    
    public List<EcoChallenge> searchChallengesByTitle(String title) {
        return ecoChallengeRepository.findByTitleContainingIgnoreCase(title);
    }
    
    public List<EcoChallenge> getChallengesByCriteria(String category, String difficultyLevel, 
                                                     Integer minPoints, Integer maxPoints) {
        return ecoChallengeRepository.findChallengesByCriteria(category, difficultyLevel, minPoints, maxPoints);
    }
    
    public EcoChallenge createChallenge(EcoChallenge challenge) {
        challenge.setCreatedAt(LocalDateTime.now());
        challenge.setUpdatedAt(LocalDateTime.now());
        return ecoChallengeRepository.save(challenge);
    }
    
    public EcoChallenge updateChallenge(Long id, EcoChallenge challengeDetails) {
        Optional<EcoChallenge> challengeOpt = ecoChallengeRepository.findById(id);
        if (challengeOpt.isPresent()) {
            EcoChallenge challenge = challengeOpt.get();
            challenge.setTitle(challengeDetails.getTitle());
            challenge.setDescription(challengeDetails.getDescription());
            challenge.setPoints(challengeDetails.getPoints());
            challenge.setDuration(challengeDetails.getDuration());
            challenge.setCategory(challengeDetails.getCategory());
            challenge.setDifficultyLevel(challengeDetails.getDifficultyLevel());
            challenge.setImageUrl(challengeDetails.getImageUrl());
            challenge.setIcon(challengeDetails.getIcon());
            challenge.setColor(challengeDetails.getColor());
            challenge.setIsActive(challengeDetails.getIsActive());
            challenge.setStartDate(challengeDetails.getStartDate());
            challenge.setEndDate(challengeDetails.getEndDate());
            challenge.setMaxParticipants(challengeDetails.getMaxParticipants());
            challenge.setRequirements(challengeDetails.getRequirements());
            challenge.setRewards(challengeDetails.getRewards());
            challenge.setUpdatedAt(LocalDateTime.now());
            return ecoChallengeRepository.save(challenge);
        }
        return null;
    }
    
    public boolean deleteChallenge(Long id) {
        Optional<EcoChallenge> challengeOpt = ecoChallengeRepository.findById(id);
        if (challengeOpt.isPresent()) {
            ecoChallengeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public boolean deactivateChallenge(Long id) {
        Optional<EcoChallenge> challengeOpt = ecoChallengeRepository.findById(id);
        if (challengeOpt.isPresent()) {
            EcoChallenge challenge = challengeOpt.get();
            challenge.setIsActive(false);
            challenge.setUpdatedAt(LocalDateTime.now());
            ecoChallengeRepository.save(challenge);
            return true;
        }
        return false;
    }
    
    // UserChallenge operations
    public List<UserChallenge> getUserChallenges(Long userId) {
        return userChallengeRepository.findByUserId(userId);
    }
    
    public List<UserChallenge> getUserChallengesByStatus(Long userId, ChallengeStatus status) {
        return userChallengeRepository.findByUserIdAndStatus(userId, status);
    }
    
    public List<UserChallenge> getCompletedUserChallenges(Long userId) {
        return userChallengeRepository.findCompletedChallengesByUserId(userId);
    }
    
    public List<UserChallenge> getInProgressUserChallenges(Long userId) {
        return userChallengeRepository.findInProgressChallengesByUserId(userId);
    }
    
    public Optional<UserChallenge> getUserChallenge(Long userId, Long challengeId) {
        return userChallengeRepository.findByUserIdAndChallengeId(userId, challengeId);
    }
    
    public UserChallenge joinChallenge(Long userId, Long challengeId) {
        // Check if user is already participating
        Optional<UserChallenge> existingChallenge = userChallengeRepository.findByUserIdAndChallengeId(userId, challengeId);
        if (existingChallenge.isPresent()) {
            throw new RuntimeException("User is already participating in this challenge");
        }
        
        // Check if challenge exists and is active
        Optional<EcoChallenge> challengeOpt = ecoChallengeRepository.findById(challengeId);
        if (!challengeOpt.isPresent() || !challengeOpt.get().getIsActive()) {
            throw new RuntimeException("Challenge not found or not active");
        }
        
        EcoChallenge challenge = challengeOpt.get();
        
        // Check if there are available spots
        if (challenge.getMaxParticipants() != null && 
            challenge.getCurrentParticipants() >= challenge.getMaxParticipants()) {
            throw new RuntimeException("Challenge is full");
        }
        
        // Create user challenge
        UserChallenge userChallenge = new UserChallenge(userId, challengeId, ChallengeStatus.IN_PROGRESS);
        userChallenge.setStartedAt(LocalDateTime.now());
        userChallenge.setCreatedAt(LocalDateTime.now());
        userChallenge.setUpdatedAt(LocalDateTime.now());
        
        // Update challenge participant count
        challenge.setCurrentParticipants(challenge.getCurrentParticipants() + 1);
        ecoChallengeRepository.save(challenge);
        
        return userChallengeRepository.save(userChallenge);
    }
    
    public UserChallenge updateChallengeProgress(Long userId, Long challengeId, Integer progressPercentage, String notes) {
        Optional<UserChallenge> userChallengeOpt = userChallengeRepository.findByUserIdAndChallengeId(userId, challengeId);
        if (!userChallengeOpt.isPresent()) {
            throw new RuntimeException("User challenge not found");
        }
        
        UserChallenge userChallenge = userChallengeOpt.get();
        userChallenge.setProgressPercentage(progressPercentage);
        userChallenge.setNotes(notes);
        userChallenge.setUpdatedAt(LocalDateTime.now());
        
        return userChallengeRepository.save(userChallenge);
    }
    
    public UserChallenge completeChallenge(Long userId, Long challengeId, String proofUrl) {
        Optional<UserChallenge> userChallengeOpt = userChallengeRepository.findByUserIdAndChallengeId(userId, challengeId);
        if (!userChallengeOpt.isPresent()) {
            throw new RuntimeException("User challenge not found");
        }
        
        UserChallenge userChallenge = userChallengeOpt.get();
        userChallenge.setStatus(ChallengeStatus.COMPLETED);
        userChallenge.setCompletedAt(LocalDateTime.now());
        userChallenge.setProgressPercentage(100);
        userChallenge.setProofUrl(proofUrl);
        userChallenge.setUpdatedAt(LocalDateTime.now());
        
        // Award points
        Optional<EcoChallenge> challengeOpt = ecoChallengeRepository.findById(challengeId);
        if (challengeOpt.isPresent()) {
            userChallenge.setPointsEarned(challengeOpt.get().getPoints());
        }
        
        return userChallengeRepository.save(userChallenge);
    }
    
    public UserChallenge cancelChallenge(Long userId, Long challengeId) {
        Optional<UserChallenge> userChallengeOpt = userChallengeRepository.findByUserIdAndChallengeId(userId, challengeId);
        if (!userChallengeOpt.isPresent()) {
            throw new RuntimeException("User challenge not found");
        }
        
        UserChallenge userChallenge = userChallengeOpt.get();
        userChallenge.setStatus(ChallengeStatus.CANCELLED);
        userChallenge.setUpdatedAt(LocalDateTime.now());
        
        // Decrease participant count
        Optional<EcoChallenge> challengeOpt = ecoChallengeRepository.findById(challengeId);
        if (challengeOpt.isPresent()) {
            EcoChallenge challenge = challengeOpt.get();
            challenge.setCurrentParticipants(Math.max(0, challenge.getCurrentParticipants() - 1));
            ecoChallengeRepository.save(challenge);
        }
        
        return userChallengeRepository.save(userChallenge);
    }
    
    public Integer getTotalPointsEarned(Long userId) {
        return userChallengeRepository.getTotalPointsEarnedByUserId(userId);
    }
    
    public long getChallengeParticipantCount(Long challengeId) {
        return userChallengeRepository.countByChallengeIdAndStatus(challengeId, ChallengeStatus.IN_PROGRESS);
    }
    
    public long getCompletedParticipantCount(Long challengeId) {
        return userChallengeRepository.countByChallengeIdAndStatus(challengeId, ChallengeStatus.COMPLETED);
    }
}
