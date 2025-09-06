# Test Eco Challenge API Endpoints
$baseUrl = "https://ecobazaarxspringboot-1.onrender.com"

Write-Host "🧪 Testing Eco Challenge API Endpoints..." -ForegroundColor Green
Write-Host "Base URL: $baseUrl" -ForegroundColor Yellow
Write-Host ""

# Test 1: Get all eco challenges
Write-Host "1️⃣ Testing GET /api/eco-challenges" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/api/eco-challenges" -Method GET
    Write-Host "✅ Success: Found $($response.Count) eco challenges" -ForegroundColor Green
    if ($response.Count -gt 0) {
        Write-Host "   First challenge: $($response[0].title)" -ForegroundColor Gray
    }
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 2: Get active eco challenges
Write-Host "2️⃣ Testing GET /api/eco-challenges/active" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/api/eco-challenges/active" -Method GET
    Write-Host "✅ Success: Found $($response.Count) active eco challenges" -ForegroundColor Green
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 3: Add new eco challenge
Write-Host "3️⃣ Testing POST /api/eco-challenges" -ForegroundColor Cyan
$newChallenge = @{
    challengeId = "test-challenge-$(Get-Date -Format 'yyyyMMdd-HHmmss')"
    title = "Test Eco Challenge"
    description = "This is a test eco challenge for API testing"
    category = "Transportation"
    difficulty = "Easy"
    ecoPoints = 100
    carbonSavings = 5.5
    isActive = $true
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri "$baseUrl/api/eco-challenges" -Method POST -Body $newChallenge -ContentType "application/json"
    Write-Host "✅ Success: Eco challenge added" -ForegroundColor Green
    Write-Host "   Challenge ID: $($response.challenge.challengeId)" -ForegroundColor Gray
    Write-Host "   Title: $($response.challenge.title)" -ForegroundColor Gray
    $testChallengeId = $response.challenge.challengeId
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
    $testChallengeId = "test-challenge-$(Get-Date -Format 'yyyyMMdd-HHmmss')"
}
Write-Host ""

# Test 4: Get specific eco challenge
Write-Host "4️⃣ Testing GET /api/eco-challenges/$testChallengeId" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/api/eco-challenges/$testChallengeId" -Method GET
    Write-Host "✅ Success: Found eco challenge" -ForegroundColor Green
    Write-Host "   Title: $($response.title)" -ForegroundColor Gray
    Write-Host "   Category: $($response.category)" -ForegroundColor Gray
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 5: Update eco challenge
Write-Host "5️⃣ Testing PUT /api/eco-challenges/$testChallengeId" -ForegroundColor Cyan
$updateChallenge = @{
    title = "Updated Test Eco Challenge"
    description = "This is an updated test eco challenge"
    category = "Energy"
    difficulty = "Medium"
    ecoPoints = 150
    carbonSavings = 7.5
    isActive = $true
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri "$baseUrl/api/eco-challenges/$testChallengeId" -Method PUT -Body $updateChallenge -ContentType "application/json"
    Write-Host "✅ Success: Eco challenge updated" -ForegroundColor Green
    Write-Host "   New Title: $($response.challenge.title)" -ForegroundColor Gray
    Write-Host "   New Category: $($response.challenge.category)" -ForegroundColor Gray
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 6: Get challenges by category
Write-Host "6️⃣ Testing GET /api/eco-challenges/category/Energy" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/api/eco-challenges/category/Energy" -Method GET
    Write-Host "✅ Success: Found $($response.Count) Energy challenges" -ForegroundColor Green
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 7: Delete eco challenge
Write-Host "7️⃣ Testing DELETE /api/eco-challenges/$testChallengeId" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/api/eco-challenges/$testChallengeId" -Method DELETE
    Write-Host "✅ Success: Eco challenge deleted" -ForegroundColor Green
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

Write-Host "🎉 Eco Challenge API Testing Complete!" -ForegroundColor Green
