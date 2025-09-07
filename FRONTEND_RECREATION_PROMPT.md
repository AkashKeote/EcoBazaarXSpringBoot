# Frontend Recreation Prompt for EcoBazaarX Eco Challenge Feature

## 🎯 **Objective**
Recreate the eco challenge feature integration in the Flutter frontend to work with the newly deployed Spring Boot backend.

## 🔗 **Backend API Endpoints (LIVE)**
**Base URL**: `https://ecobazaarxspringboot-1.onrender.com/api`

### Available Endpoints:
1. **GET** `/eco-challenges` - Get all eco challenges
2. **GET** `/eco-challenges/active` - Get active challenges only
3. **GET** `/eco-challenges/{id}` - Get specific challenge by ID
4. **GET** `/eco-challenges/category/{category}` - Get challenges by category
5. **POST** `/eco-challenges/{id}/join` - Join a challenge
6. **POST** `/eco-challenges/{id}/leave` - Leave a challenge
7. **GET** `/eco-challenges/user/{userId}` - Get user's challenges
8. **GET** `/eco-challenges/user/{userId}/stats` - Get user challenge statistics

## 📊 **New Backend Data Structure**

### EcoChallenge Entity:
```json
{
  "id": "string",
  "title": "string",
  "description": "string",
  "category": "string",
  "points": "integer",
  "duration": "integer (days)",
  "difficultyLevel": "EASY|MEDIUM|HARD",
  "icon": "string (emoji)",
  "color": "string (hex color)",
  "isActive": "boolean",
  "startDate": "DateTime (nullable)",
  "endDate": "DateTime (nullable)",
  "maxParticipants": "integer (nullable)",
  "currentParticipants": "integer",
  "createdAt": "DateTime",
  "updatedAt": "DateTime"
}
```

### UserChallenge Entity:
```json
{
  "id": "string",
  "userId": "string",
  "challengeId": "string",
  "startDate": "DateTime",
  "endDate": "DateTime",
  "status": "IN_PROGRESS|COMPLETED|CANCELLED",
  "progress": "integer (0-100)",
  "pointsEarned": "integer",
  "createdAt": "DateTime",
  "updatedAt": "DateTime"
}
```

## 🔧 **Required Frontend Changes**

### 1. Update `lib/services/eco_challenges_service.dart`

**Current Issues to Fix:**
- Remove old field names: `rewards`, `targetValue`, `unit`, `isCompleted`, `currentValue`, `progressPercentage`, `currentProgress`
- Update to new field names: `points`, `duration`, `difficultyLevel`, `icon` (String), `color` (String), `isActive`, `startDate`, `endDate`, `maxParticipants`, `currentParticipants`
- Replace mock service calls with actual HTTP calls to backend
- Update `EcoChallengeData` class structure

**Required Methods:**
```dart
// Replace existing methods with these:
static Future<List<EcoChallengeData>> getAllChallenges()
static Future<List<EcoChallengeData>> getActiveChallenges()
static Future<EcoChallengeData?> getChallengeById(String challengeId)
static Future<List<EcoChallengeData>> getChallengesByCategory(String category)
static Future<bool> joinChallenge(String challengeId, String userId)
static Future<bool> leaveChallenge(String challengeId, String userId)
static Future<List<UserChallengeData>> getUserChallenges(String userId)
static Future<Map<String, dynamic>> getUserChallengeStats(String userId)
```

### 2. Update `lib/providers/eco_challenges_provider.dart`

**Current Issues to Fix:**
- Update `EcoChallenge` model to match new backend structure
- Fix field name mismatches: `rewards` → `points`, `targetValue` → `duration`, `targetUnit` → `difficultyLevel`
- Update color parsing from String to Color
- Update icon parsing from String to IconData
- Fix nullable DateTime fields
- Update method calls to match new service methods

**Required Model Structure:**
```dart
class EcoChallenge {
  final String id;
  final String title;
  final String description;
  final int points; // Changed from reward
  final Color color;
  final IconData icon;
  final int duration; // Changed from targetValue
  final String difficultyLevel; // Changed from targetUnit
  final DateTime? startDate; // Changed to nullable
  final DateTime? endDate; // Changed to nullable
  final String category;
  final bool isActive;
  final int currentParticipants; // New field
  final int? maxParticipants; // New field
}
```

### 3. Update `lib/screens/home/home_screen.dart`

**Current Issues to Fix:**
- Replace old field references: `challenge.rewards` → `challenge.points`
- Replace old field references: `challenge.targetValue` → `challenge.duration`
- Replace old field references: `challenge.targetUnit` → `challenge.difficultyLevel`
- Replace old field references: `challenge.isCompleted` → check status from UserChallenge
- Replace old field references: `challenge.currentValue` → get from UserChallenge progress
- Replace old field references: `challenge.progressPercentage` → calculate from UserChallenge progress
- Replace old field references: `challenge.currentProgress` → get from UserChallenge progress

### 4. Update `lib/config/firebase_config.dart`

**Verify this is set correctly:**
```dart
static const String baseApiUrl = 'https://ecobazaarxspringboot-1.onrender.com/api';
```

## 🚀 **Implementation Steps**

### Step 1: Fix Service Layer
1. Update `EcoChallengeData` class with new field structure
2. Replace all mock service calls with actual HTTP calls
3. Add proper error handling and fallback to sample data
4. Update `fromMap` and `toMap` methods

### Step 2: Fix Provider Layer
1. Update `EcoChallenge` model structure
2. Add helper methods for color and icon parsing
3. Update all field references in the provider
4. Fix method calls to match new service methods

### Step 3: Fix UI Layer
1. Update all field references in home screen
2. Fix any compilation errors
3. Test the integration

### Step 4: Test Integration
1. Run `flutter clean && flutter pub get`
2. Test `flutter build web --release`
3. Verify API calls work with backend

## 📝 **Sample Data Structure (Backend Provides)**

The backend automatically provides 10 sample eco challenges:
1. "30-Day Plastic Free Challenge" - 100 points, 30 days, MEDIUM
2. "Energy Saving Challenge" - 75 points, 14 days, EASY
3. "Zero Waste Challenge" - 150 points, 21 days, HARD
4. "Water Conservation Challenge" - 50 points, 7 days, EASY
5. "Sustainable Transportation Challenge" - 80 points, 14 days, MEDIUM
6. "Local Food Challenge" - 60 points, 10 days, EASY
7. "Digital Detox Challenge" - 40 points, 7 days, EASY
8. "Green Energy Challenge" - 120 points, 30 days, HARD
9. "Minimalist Living Challenge" - 90 points, 21 days, MEDIUM
10. "Carbon Footprint Challenge" - 200 points, 45 days, HARD

## ⚠️ **Important Notes**

1. **Backend is LIVE**: All APIs are working at `https://ecobazaarxspringboot-1.onrender.com/api`
2. **Database Connected**: MySQL database on Railway is connected and working
3. **Sample Data Available**: Backend automatically provides 10 sample challenges
4. **Error Handling**: Always provide fallback to sample data if API calls fail
5. **Field Mapping**: Be careful with field name changes - they must match exactly

## 🎯 **Success Criteria**

After implementation:
- ✅ Flutter app builds without errors
- ✅ Eco challenges load from backend API
- ✅ All field references work correctly
- ✅ User can join/leave challenges
- ✅ Progress tracking works
- ✅ App can be deployed to Firebase

## 🔍 **Testing Commands**

```bash
# Test backend APIs
curl https://ecobazaarxspringboot-1.onrender.com/api/eco-challenges
curl https://ecobazaarxspringboot-1.onrender.com/api/eco-challenges/active

# Test frontend build
flutter clean
flutter pub get
flutter build web --release
```

## 📞 **Support**

If you encounter issues:
1. Check console logs for API call errors
2. Verify field name mappings
3. Test backend APIs directly
4. Check Firebase config for correct base URL

**Good luck! The backend is ready and waiting for the frontend integration! 🚀**
