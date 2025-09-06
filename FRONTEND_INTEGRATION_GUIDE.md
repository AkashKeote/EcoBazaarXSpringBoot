# Frontend Integration Guide - EcoBazaarX Backend

## 🚨 CRITICAL: Network Error Fix

### Problem Identified
The backend is working correctly (Status 200, valid JWT tokens), but the frontend shows "Network error" due to improper response handling.

### Backend Response Format
The login API returns the following JSON structure:

```json
{
  "success": true,
  "message": "Login successful",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
  "userId": "4",
  "userRole": "customer"
}
```

### Frontend Error Handling Fix

#### ❌ INCORRECT Frontend Code (causing "Network error"):
```javascript
// DON'T DO THIS
fetch('https://ecobazaarxspringboot-1.onrender.com/api/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ email, password, role })
})
.then(response => {
  if (!response.ok) {
    throw new Error('Network error'); // ❌ This is wrong!
  }
  return response.json();
})
.catch(error => {
  showError('Network error. Please check your connection.'); // ❌ Wrong error message
});
```

#### ✅ CORRECT Frontend Code:
```javascript
// DO THIS INSTEAD
fetch('https://ecobazaarxspringboot-1.onrender.com/api/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ email, password, role })
})
.then(response => response.json()) // Always parse JSON first
.then(data => {
  if (data.success) {
    // Login successful
    localStorage.setItem('token', data.token);
    localStorage.setItem('refreshToken', data.refreshToken);
    localStorage.setItem('userId', data.userId);
    localStorage.setItem('userRole', data.userRole);
    
    // Redirect to dashboard or show success
    console.log('Login successful:', data.message);
    // Handle successful login...
  } else {
    // Login failed - show backend error message
    showError(data.message); // Use backend error message
  }
})
.catch(error => {
  // Only show network error for actual network issues
  console.error('Network error:', error);
  showError('Unable to connect to server. Please check your internet connection.');
});
```

### Key Points for Frontend Developers:

1. **Always parse JSON first**: `response.json()` before checking success
2. **Check `data.success`**: Not `response.ok`
3. **Use backend error messages**: `data.message` contains the actual error
4. **Handle CORS properly**: The backend now supports all required origins
5. **Store tokens correctly**: Save both `token` and `refreshToken`

### CORS Configuration (Backend is Fixed)
The backend now properly supports:
- ✅ `https://ecobazzarx.web.app`
- ✅ `https://ecobazzarx.firebaseapp.com`
- ✅ `http://localhost:5173` (development)
- ✅ `http://localhost:3000` (development)

### API Endpoints

#### Login
```
POST https://ecobazaarxspringboot-1.onrender.com/api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "role": "customer"
}
```

#### Get User Settings
```
GET https://ecobazaarxspringboot-1.onrender.com/api/settings/{userId}
```

**IMPORTANT**: Use `/api/settings/{userId}` NOT `/api/settings/user/{userId}`

#### Response (Success)
```json
{
  "success": true,
  "message": "Login successful",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
  "userId": "4",
  "userRole": "customer"
}
```

#### Response (Error)
```json
{
  "success": false,
  "message": "Invalid password",
  "token": null,
  "refreshToken": null,
  "userId": null,
  "userRole": null
}
```

#### Settings Response Format
```json
{
  "userId": "4",
  "appPreferences": {
    "theme": "light",
    "darkModeEnabled": false,
    "autoSaveEnabled": true,
    "hapticFeedbackEnabled": true,
    "language": "en",
    "currency": "INR",
    "timezone": "Asia/Kolkata"
  },
  "notificationSettings": {
    "notificationsEnabled": true,
    "emailNotifications": true,
    "pushNotifications": true,
    "smsNotifications": false,
    "ecoTipsEnabled": true,
    "challengeReminders": true,
    "orderUpdates": true,
    "promotionalEmails": false
  },
  "privacySettings": {
    "privacyLevel": "medium",
    "dataSharing": false,
    "locationTracking": false
  },
  "syncSettings": {
    "autoSync": true,
    "syncFrequency": "daily",
    "lastSync": "2025-09-06 17:49:12"
  },
  "createdAt": null,
  "lastUpdated": "2025-09-06 17:49:12"
}
```

### Frontend Settings Fix

#### ❌ INCORRECT Settings API Call:
```javascript
// DON'T DO THIS - Wrong endpoint
fetch(`https://ecobazaarxspringboot-1.onrender.com/api/settings/user/${userId}`)
```

#### ✅ CORRECT Settings API Call:
```javascript
// DO THIS INSTEAD - Correct endpoint
fetch(`https://ecobazaarxspringboot-1.onrender.com/api/settings/${userId}`)
  .then(response => response.json())
  .then(settings => {
    // Handle settings object properly
    console.log('User settings:', settings);
    // settings.appPreferences.theme
    // settings.notificationSettings.notificationsEnabled
    // etc.
  })
  .catch(error => {
    console.error('Settings error:', error);
  });
```

### Testing the Fix

1. **Open DevTools Console** in your frontend
2. **Check Network Tab** - you should see Status 200 responses
3. **Check Console** - you should see successful API calls
4. **Update frontend code** to use the correct error handling pattern above
5. **Fix settings endpoint** from `/api/settings/user/{userId}` to `/api/settings/{userId}`
6. **Test login** - should work without "Network error"

### Common Error Messages from Backend:
- "User not found"
- "Invalid password" 
- "Invalid role"
- "User account is deactivated"
- "Authentication failed: [details]"

### Need Help?
If you're still seeing issues after implementing the correct error handling, check:
1. Network tab in DevTools for actual HTTP status codes
2. Console for any JavaScript errors
3. Ensure you're using the correct API URL
4. Verify the request body format matches the expected structure

---
**Backend Status**: ✅ Working correctly  
**CORS Status**: ✅ Fixed and deployed  
**Issue**: Frontend error handling logic needs update
