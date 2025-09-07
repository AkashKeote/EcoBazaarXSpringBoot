package com.ecobazaar.backend.repository;

import com.ecobazaar.backend.entity.ChallengeStatus;
import com.ecobazaar.backend.entity.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {
    
    // Find user challenges by user ID
    List<UserChallenge> findByUserId(Long userId);
    
    // Find user challenges by challenge ID
    List<UserChallenge> findByChallengeId(Long challengeId);
    
    // Find specific user challenge
    Optional<UserChallenge> findByUserIdAndChallengeId(Long userId, Long challengeId);
    
    // Find user challenges by status
    List<UserChallenge> findByUserIdAndStatus(Long userId, ChallengeStatus status);
    
    // Find user challenges by challenge ID and status
    List<UserChallenge> findByChallengeIdAndStatus(Long challengeId, ChallengeStatus status);
    
    // Find completed user challenges
    @Query("SELECT uc FROM UserChallenge uc WHERE uc.userId = :userId AND uc.status = 'COMPLETED'")
    List<UserChallenge> findCompletedChallengesByUserId(@Param("userId") Long userId);
    
    // Find in-progress user challenges
    @Query("SELECT uc FROM UserChallenge uc WHERE uc.userId = :userId AND uc.status = 'IN_PROGRESS'")
    List<UserChallenge> findInProgressChallengesByUserId(@Param("userId") Long userId);
    
    // Find user challenges started within date range
    @Query("SELECT uc FROM UserChallenge uc WHERE uc.userId = :userId AND " +
           "uc.startedAt BETWEEN :startDate AND :endDate")
    List<UserChallenge> findUserChallengesByDateRange(@Param("userId") Long userId,
                                                     @Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate);
    
    // Count user challenges by status
    long countByUserIdAndStatus(Long userId, ChallengeStatus status);
    
    // Count total challenges for a user
    long countByUserId(Long userId);
    
    // Count participants for a specific challenge
    long countByChallengeIdAndStatus(Long challengeId, ChallengeStatus status);
    
    // Find users who completed a specific challenge
    @Query("SELECT uc FROM UserChallenge uc WHERE uc.challengeId = :challengeId AND uc.status = 'COMPLETED'")
    List<UserChallenge> findCompletedParticipantsByChallengeId(@Param("challengeId") Long challengeId);
    
    // Find user challenges with progress percentage
    @Query("SELECT uc FROM UserChallenge uc WHERE uc.userId = :userId AND uc.progressPercentage > 0")
    List<UserChallenge> findUserChallengesWithProgress(@Param("userId") Long userId);
    
    // Find user challenges by points earned
    @Query("SELECT uc FROM UserChallenge uc WHERE uc.userId = :userId AND uc.pointsEarned > 0")
    List<UserChallenge> findUserChallengesWithPoints(@Param("userId") Long userId);
    
    // Get total points earned by user
    @Query("SELECT COALESCE(SUM(uc.pointsEarned), 0) FROM UserChallenge uc WHERE uc.userId = :userId")
    Integer getTotalPointsEarnedByUserId(@Param("userId") Long userId);
    
    // Check if user is already participating in a challenge
    boolean existsByUserIdAndChallengeIdAndStatusIn(Long userId, Long challengeId, List<ChallengeStatus> statuses);
}
