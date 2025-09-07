package com.ecobazaar.backend.service;

import com.ecobazaar.backend.entity.EcoChallenge;
import com.ecobazaar.backend.repository.EcoChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EcoChallengeService {

    @Autowired
    private EcoChallengeRepository ecoChallengeRepository;

    public List<EcoChallenge> getAllChallenges() {
        return ecoChallengeRepository.findAll();
    }

    public List<EcoChallenge> getActiveChallenges() {
        return ecoChallengeRepository.findByIsActiveTrue();
    }

    public Optional<EcoChallenge> getChallengeById(String challengeId) {
        return ecoChallengeRepository.findByChallengeId(challengeId);
    }

    public List<EcoChallenge> getChallengesByCategory(String category) {
        return ecoChallengeRepository.findByCategory(category);
    }

    public List<EcoChallenge> getChallengesByDifficulty(String difficulty) {
        return ecoChallengeRepository.findByDifficulty(difficulty);
    }

    public EcoChallenge saveChallenge(EcoChallenge challenge) {
        return ecoChallengeRepository.save(challenge);
    }

    public void deleteChallenge(String challengeId) {
        Optional<EcoChallenge> challenge = ecoChallengeRepository.findByChallengeId(challengeId);
        if (challenge.isPresent()) {
            ecoChallengeRepository.delete(challenge.get());
        }
    }

    public boolean challengeExists(String challengeId) {
        return ecoChallengeRepository.existsByChallengeId(challengeId);
    }

    public EcoChallenge createDefaultChallenge(String challengeId, String title) {
        EcoChallenge challenge = new EcoChallenge();
        challenge.setChallengeId(challengeId);
        challenge.setTitle(title);
        challenge.setDescription("Default challenge description");
        challenge.setCategory("General");
        challenge.setDifficulty("Medium");
        challenge.setEcoPoints(10);
        challenge.setCarbonSavings(5.0);
        challenge.setIsActive(true);
        return challenge;
    }
}
