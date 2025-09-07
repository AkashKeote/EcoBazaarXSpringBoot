# Eco Challenge Feature Documentation

## Overview
The Eco Challenge feature has been successfully recreated and integrated into the EcoBazaarX backend. This feature allows users to participate in environmental challenges, track their progress, and earn eco points for sustainable actions.

## Database Schema

### Tables Created

#### 1. `eco_challenges` Table
- **id**: Primary key (Long, Auto-increment)
- **title**: Challenge title (String, Not null)
- **description**: Detailed challenge description (TEXT)
- **points**: Points awarded for completion (Integer, Not null)
- **duration**: Challenge duration in days (Integer, Not null)
- **category**: Challenge category (String, Not null)
- **difficulty_level**: EASY, MEDIUM, HARD (String)
- **image_url**: Challenge image URL (String)
- **icon**: Challenge icon/emoji (String)
- **color**: Challenge theme color (String)
- **is_active**: Whether challenge is active (Boolean, Default: true)
- **start_date**: Challenge start date (LocalDateTime)
- **end_date**: Challenge end date (LocalDateTime)
- **max_participants**: Maximum participants allowed (Integer)
- **current_participants**: Current participant count (Integer, Default: 0)
- **requirements**: Challenge requirements (TEXT, JSON format)
- **rewards**: Challenge rewards (TEXT, JSON format)
- **created_at**: Creation timestamp (LocalDateTime)
- **updated_at**: Last update timestamp (LocalDateTime)

#### 2. `user_challenges` Table
- **id**: Primary key (Long, Auto-increment)
- **user_id**: Reference to user (Long, Not null)
- **challenge_id**: Reference to challenge (Long, Not null)
- **status**: Challenge status enum (NOT_STARTED, IN_PROGRESS, COMPLETED, FAILED, CANCELLED)
- **started_at**: When user started the challenge (LocalDateTime)
- **completed_at**: When user completed the challenge (LocalDateTime)
- **progress_percentage**: Progress percentage (Integer, Default: 0)
- **points_earned**: Points earned by user (Integer, Default: 0)
- **notes**: User notes (TEXT)
- **proof_url**: URL to proof of completion (String)
- **created_at**: Creation timestamp (LocalDateTime)
- **updated_at**: Last update timestamp (LocalDateTime)

## API Endpoints

### Challenge Management Endpoints

#### Get All Challenges
```
GET /api/eco-challenges
```
Returns all challenges (active and inactive).

#### Get Active Challenges
```
GET /api/eco-challenges/active
```
Returns only active challenges.

#### Get Currently Active Challenges
```
GET /api/eco-challenges/currently-active
```
Returns challenges that are currently active based on date range.

#### Get Challenge by ID
```
GET /api/eco-challenges/{id}
```
Returns a specific challenge by ID.

#### Get Challenges by Category
```
GET /api/eco-challenges/category/{category}
```
Returns challenges filtered by category.

#### Get Challenges by Difficulty
```
GET /api/eco-challenges/difficulty/{difficultyLevel}
```
Returns challenges filtered by difficulty level.

#### Get Challenges by Points Range
```
GET /api/eco-challenges/points?minPoints={min}&maxPoints={max}
```
Returns challenges within specified points range.

#### Get Challenges by Duration Range
```
GET /api/eco-challenges/duration?minDuration={min}&maxDuration={max}
```
Returns challenges within specified duration range.

#### Get Challenges with Available Spots
```
GET /api/eco-challenges/available
```
Returns challenges that still have available participant spots.

#### Search Challenges by Title
```
GET /api/eco-challenges/search?title={searchTerm}
```
Returns challenges matching the search term in title.

#### Filter Challenges by Multiple Criteria
```
GET /api/eco-challenges/filter?category={cat}&difficultyLevel={diff}&minPoints={min}&maxPoints={max}
```
Returns challenges filtered by multiple criteria.

### Challenge Creation/Management (Admin)

#### Create New Challenge
```
POST /api/eco-challenges
Content-Type: application/json

{
  "title": "Challenge Title",
  "description": "Challenge description",
  "points": 100,
  "duration": 30,
  "category": "Sustainability",
  "difficultyLevel": "MEDIUM",
  "icon": "🌱",
  "color": "#4CAF50",
  "maxParticipants": 100
}
```

#### Update Challenge
```
PUT /api/eco-challenges/{id}
Content-Type: application/json

{
  // Updated challenge data
}
```

#### Delete Challenge
```
DELETE /api/eco-challenges/{id}
```

#### Deactivate Challenge
```
PUT /api/eco-challenges/{id}/deactivate
```

### User Challenge Endpoints

#### Get User Challenges
```
GET /api/eco-challenges/user/{userId}
```
Returns all challenges for a specific user.

#### Get User Challenges by Status
```
GET /api/eco-challenges/user/{userId}/status/{status}
```
Returns user challenges filtered by status.

#### Get Completed User Challenges
```
GET /api/eco-challenges/user/{userId}/completed
```
Returns completed challenges for a user.

#### Get In-Progress User Challenges
```
GET /api/eco-challenges/user/{userId}/in-progress
```
Returns in-progress challenges for a user.

#### Get Specific User Challenge
```
GET /api/eco-challenges/user/{userId}/challenge/{challengeId}
```
Returns a specific user challenge.

#### Join Challenge
```
POST /api/eco-challenges/user/{userId}/join/{challengeId}
```
Allows a user to join a challenge.

#### Update Challenge Progress
```
PUT /api/eco-challenges/user/{userId}/challenge/{challengeId}/progress?progressPercentage={percent}&notes={notes}
```
Updates user's progress in a challenge.

#### Complete Challenge
```
PUT /api/eco-challenges/user/{userId}/challenge/{challengeId}/complete?proofUrl={url}
```
Marks a challenge as completed for a user.

#### Cancel Challenge
```
PUT /api/eco-challenges/user/{userId}/challenge/{challengeId}/cancel
```
Cancels user's participation in a challenge.

#### Get Total Points Earned
```
GET /api/eco-challenges/user/{userId}/total-points
```
Returns total points earned by a user.

#### Get Challenge Participant Count
```
GET /api/eco-challenges/{challengeId}/participants
```
Returns number of participants in a challenge.

#### Get Completed Participant Count
```
GET /api/eco-challenges/{challengeId}/completed-participants
```
Returns number of users who completed a challenge.

## Sample Data

The system includes 10 pre-configured eco challenges:

1. **30-Day Plastic Free Challenge** (100 points, 30 days, Sustainability)
2. **Energy Saving Challenge** (75 points, 14 days, Energy)
3. **Zero Waste Week** (50 points, 7 days, Waste Reduction)
4. **Plant-Based Diet Challenge** (80 points, 21 days, Food & Diet)
5. **Water Conservation Challenge** (60 points, 14 days, Water Conservation)
6. **Sustainable Transportation** (90 points, 30 days, Transportation)
7. **Digital Detox Challenge** (40 points, 7 days, Lifestyle)
8. **Local Shopping Challenge** (70 points, 14 days, Shopping)
9. **Green Home Challenge** (85 points, 30 days, Home & Living)
10. **Ocean Cleanup Challenge** (95 points, 21 days, Ocean Conservation)

## Integration with MySQL Database

The eco challenge feature is fully integrated with MySQL database:

- **Database**: MySQL 8.0
- **Connection**: Configured in `application.properties` and `application-prod.properties`
- **JPA**: Uses Hibernate with MySQL8Dialect
- **Auto-creation**: Tables are automatically created with `spring.jpa.hibernate.ddl-auto=update`

## Caching Configuration

Eco challenges are included in the caching system:
- **Cache Names**: `ecoChallenges`, `userChallenges`
- **Cache Type**: Caffeine
- **Configuration**: Maximum 500 entries, expire after 600 seconds

## Frontend Integration

The API endpoints are designed to work seamlessly with frontend applications:

- **CORS**: Configured to allow frontend origins
- **JSON**: All responses are in JSON format
- **RESTful**: Follows REST API conventions
- **Error Handling**: Proper HTTP status codes and error messages

## Testing

A PowerShell test script (`test-eco-challenge-api.ps1`) is provided to test all API endpoints.

## Security Considerations

- Admin endpoints (create, update, delete) should be protected with proper authentication
- User-specific endpoints should validate user ownership
- Input validation is implemented for all endpoints
- SQL injection protection through JPA/Hibernate

## Future Enhancements

1. **Gamification**: Add leaderboards and achievements
2. **Social Features**: Allow users to share progress and invite friends
3. **Notifications**: Send reminders and completion notifications
4. **Analytics**: Track challenge completion rates and user engagement
5. **Custom Challenges**: Allow users to create their own challenges
6. **Rewards System**: Integrate with product discounts and eco points

## Database Migration

The eco challenge feature is automatically initialized when the application starts if no challenges exist in the database. This ensures backward compatibility and easy deployment.

## Performance Considerations

- Database indexes are automatically created on foreign keys
- Caching is implemented for frequently accessed data
- Pagination can be added for large result sets
- Query optimization through JPA repository methods

This eco challenge feature provides a comprehensive foundation for gamifying environmental actions and encouraging sustainable behavior among users.
