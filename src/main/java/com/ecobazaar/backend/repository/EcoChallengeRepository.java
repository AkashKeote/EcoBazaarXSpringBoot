package com.ecobazaar.backend.repository;

import com.ecobazaar.backend.entity.EcoChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EcoChallengeRepository extends JpaRepository<EcoChallenge, Long> {
    Optional<EcoChallenge> findByChallengeId(String challengeId);
    List<EcoChallenge> findByIsActiveTrue();
    List<EcoChallenge> findByCategory(String category);
    List<EcoChallenge> findByDifficulty(String difficulty);
    boolean existsByChallengeId(String challengeId);
}
