package com.ecobazaar.backend.repository;

import com.ecobazaar.backend.entity.EcoChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EcoChallengeRepository extends JpaRepository<EcoChallenge, Long> {
    
    // Find all active challenges
    List<EcoChallenge> findByIsActiveTrue();
    
    // Find challenges by category
    List<EcoChallenge> findByCategoryAndIsActiveTrue(String category);
    
    // Find challenges by difficulty level
    List<EcoChallenge> findByDifficultyLevelAndIsActiveTrue(String difficultyLevel);
    
    // Find challenges by points range
    @Query("SELECT c FROM EcoChallenge c WHERE c.points BETWEEN :minPoints AND :maxPoints AND c.isActive = true")
    List<EcoChallenge> findByPointsRange(@Param("minPoints") Integer minPoints, @Param("maxPoints") Integer maxPoints);
    
    // Find challenges by duration range
    @Query("SELECT c FROM EcoChallenge c WHERE c.duration BETWEEN :minDuration AND :maxDuration AND c.isActive = true")
    List<EcoChallenge> findByDurationRange(@Param("minDuration") Integer minDuration, @Param("maxDuration") Integer maxDuration);
    
    // Find currently active challenges (within date range)
    @Query("SELECT c FROM EcoChallenge c WHERE c.isActive = true AND " +
           "(c.startDate IS NULL OR c.startDate <= :currentDate) AND " +
           "(c.endDate IS NULL OR c.endDate >= :currentDate)")
    List<EcoChallenge> findCurrentlyActiveChallenges(@Param("currentDate") LocalDateTime currentDate);
    
    // Find challenges with available spots
    @Query("SELECT c FROM EcoChallenge c WHERE c.isActive = true AND " +
           "(c.maxParticipants IS NULL OR c.currentParticipants < c.maxParticipants)")
    List<EcoChallenge> findChallengesWithAvailableSpots();
    
    // Find challenges by title (case insensitive)
    @Query("SELECT c FROM EcoChallenge c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%')) AND c.isActive = true")
    List<EcoChallenge> findByTitleContainingIgnoreCase(@Param("title") String title);
    
    // Find challenges by multiple criteria
    @Query("SELECT c FROM EcoChallenge c WHERE c.isActive = true AND " +
           "(:category IS NULL OR c.category = :category) AND " +
           "(:difficultyLevel IS NULL OR c.difficultyLevel = :difficultyLevel) AND " +
           "(:minPoints IS NULL OR c.points >= :minPoints) AND " +
           "(:maxPoints IS NULL OR c.points <= :maxPoints)")
    List<EcoChallenge> findChallengesByCriteria(@Param("category") String category,
                                               @Param("difficultyLevel") String difficultyLevel,
                                               @Param("minPoints") Integer minPoints,
                                               @Param("maxPoints") Integer maxPoints);
    
    // Count active challenges
    long countByIsActiveTrue();
    
    // Count challenges by category
    long countByCategoryAndIsActiveTrue(String category);
}
