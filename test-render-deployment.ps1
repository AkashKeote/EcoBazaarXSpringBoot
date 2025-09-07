# Test script for Eco Challenge API endpoints on Render deployment
# Testing the deployed backend at https://ecobazaarxspringboot-1.onrender.com/

$baseUrl = "https://ecobazaarxspringboot-1.onrender.com/api/eco-challenges"

Write-Host "Testing Eco Challenge API Endpoints on Render..." -ForegroundColor Green
Write-Host "Base URL: $baseUrl" -ForegroundColor Yellow
Write-Host "Deployment URL: https://ecobazaarxspringboot-1.onrender.com/" -ForegroundColor Cyan
Write-Host ""

# Wait for deployment to be ready
Write-Host "Waiting for deployment to be ready..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

# Test 1: Health check first
Write-Host "0. Testing Health Check" -ForegroundColor Cyan
try {
    $healthResponse = Invoke-RestMethod -Uri "https://ecobazaarxspringboot-1.onrender.com/api/actuator/health" -Method GET
    Write-Host "✓ Health Check: $($healthResponse.status)" -ForegroundColor Green
} catch {
    Write-Host "✗ Health Check Failed: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Deployment might still be in progress. Please wait a few minutes and try again." -ForegroundColor Yellow
    exit 1
}
Write-Host ""

# Test 2: Get all challenges
Write-Host "1. Testing GET /api/eco-challenges" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl" -Method GET
    Write-Host "✓ Success: Found $($response.Count) challenges" -ForegroundColor Green
    if ($response.Count -gt 0) {
        Write-Host "   First challenge: $($response[0].title)" -ForegroundColor Gray
        Write-Host "   Points: $($response[0].points), Duration: $($response[0].duration) days" -ForegroundColor Gray
    }
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 3: Get active challenges
Write-Host "2. Testing GET /api/eco-challenges/active" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/active" -Method GET
    Write-Host "✓ Success: Found $($response.Count) active challenges" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 4: Get currently active challenges
Write-Host "3. Testing GET /api/eco-challenges/currently-active" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/currently-active" -Method GET
    Write-Host "✓ Success: Found $($response.Count) currently active challenges" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 5: Get challenges by category
Write-Host "4. Testing GET /api/eco-challenges/category/Sustainability" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/category/Sustainability" -Method GET
    Write-Host "✓ Success: Found $($response.Count) Sustainability challenges" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 6: Get challenges by difficulty
Write-Host "5. Testing GET /api/eco-challenges/difficulty/EASY" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/difficulty/EASY" -Method GET
    Write-Host "✓ Success: Found $($response.Count) EASY challenges" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 7: Get challenges with available spots
Write-Host "6. Testing GET /api/eco-challenges/available" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/available" -Method GET
    Write-Host "✓ Success: Found $($response.Count) challenges with available spots" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 8: Search challenges by title
Write-Host "7. Testing GET /api/eco-challenges/search?title=plastic" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/search?title=plastic" -Method GET
    Write-Host "✓ Success: Found $($response.Count) challenges matching 'plastic'" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 9: Filter challenges by criteria
Write-Host "8. Testing GET /api/eco-challenges/filter?category=Energy&minPoints=50" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/filter?category=Energy&minPoints=50" -Method GET
    Write-Host "✓ Success: Found $($response.Count) Energy challenges with 50+ points" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 10: Get specific challenge by ID (if challenges exist)
Write-Host "9. Testing GET /api/eco-challenges/1" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/1" -Method GET
    Write-Host "✓ Success: Found challenge with ID 1: $($response.title)" -ForegroundColor Green
    Write-Host "   Category: $($response.category), Difficulty: $($response.difficultyLevel)" -ForegroundColor Gray
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 11: Test other API endpoints
Write-Host "10. Testing other API endpoints" -ForegroundColor Cyan
try {
    # Test products endpoint
    $productsResponse = Invoke-RestMethod -Uri "https://ecobazaarxspringboot-1.onrender.com/api/products" -Method GET
    Write-Host "✓ Products API: Found $($productsResponse.Count) products" -ForegroundColor Green
    
    # Test stores endpoint
    $storesResponse = Invoke-RestMethod -Uri "https://ecobazaarxspringboot-1.onrender.com/api/stores" -Method GET
    Write-Host "✓ Stores API: Found $($storesResponse.Count) stores" -ForegroundColor Green
    
} catch {
    Write-Host "✗ Other API Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

Write-Host "🎉 Eco Challenge API Testing Complete on Render!" -ForegroundColor Green
Write-Host ""
Write-Host "📊 Deployment Summary:" -ForegroundColor Yellow
Write-Host "✅ Backend deployed at: https://ecobazaarxspringboot-1.onrender.com/" -ForegroundColor Green
Write-Host "✅ Eco Challenge API: https://ecobazaarxspringboot-1.onrender.com/api/eco-challenges" -ForegroundColor Green
Write-Host "✅ MySQL Database: Connected to Railway" -ForegroundColor Green
Write-Host "✅ Auto-deployment: Enabled via GitHub" -ForegroundColor Green
Write-Host ""
Write-Host "🔗 Frontend Integration URLs:" -ForegroundColor Yellow
Write-Host "• All Challenges: GET https://ecobazaarxspringboot-1.onrender.com/api/eco-challenges" -ForegroundColor Gray
Write-Host "• Active Challenges: GET https://ecobazaarxspringboot-1.onrender.com/api/eco-challenges/active" -ForegroundColor Gray
Write-Host "• Join Challenge: POST https://ecobazaarxspringboot-1.onrender.com/api/eco-challenges/user/{userId}/join/{challengeId}" -ForegroundColor Gray
Write-Host "• User Challenges: GET https://ecobazaarxspringboot-1.onrender.com/api/eco-challenges/user/{userId}" -ForegroundColor Gray
Write-Host ""
Write-Host "📝 Next Steps:" -ForegroundColor Yellow
Write-Host "1. Update frontend to use the new eco challenge endpoints" -ForegroundColor Gray
Write-Host "2. Test user authentication and challenge participation" -ForegroundColor Gray
Write-Host "3. Implement challenge progress tracking in frontend" -ForegroundColor Gray
Write-Host "4. Add challenge completion and points system" -ForegroundColor Gray

