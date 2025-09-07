# Test script for Eco Challenge API endpoints
# Make sure the backend is running on port 10000

$baseUrl = "http://localhost:10000/api/eco-challenges"

Write-Host "Testing Eco Challenge API Endpoints..." -ForegroundColor Green
Write-Host "Base URL: $baseUrl" -ForegroundColor Yellow
Write-Host ""

# Test 1: Get all challenges
Write-Host "1. Testing GET /api/eco-challenges" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl" -Method GET
    Write-Host "✓ Success: Found $($response.Count) challenges" -ForegroundColor Green
    if ($response.Count -gt 0) {
        Write-Host "   First challenge: $($response[0].title)" -ForegroundColor Gray
    }
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 2: Get active challenges
Write-Host "2. Testing GET /api/eco-challenges/active" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/active" -Method GET
    Write-Host "✓ Success: Found $($response.Count) active challenges" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 3: Get currently active challenges
Write-Host "3. Testing GET /api/eco-challenges/currently-active" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/currently-active" -Method GET
    Write-Host "✓ Success: Found $($response.Count) currently active challenges" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 4: Get challenges by category
Write-Host "4. Testing GET /api/eco-challenges/category/Sustainability" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/category/Sustainability" -Method GET
    Write-Host "✓ Success: Found $($response.Count) Sustainability challenges" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 5: Get challenges by difficulty
Write-Host "5. Testing GET /api/eco-challenges/difficulty/EASY" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/difficulty/EASY" -Method GET
    Write-Host "✓ Success: Found $($response.Count) EASY challenges" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 6: Get challenges with available spots
Write-Host "6. Testing GET /api/eco-challenges/available" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/available" -Method GET
    Write-Host "✓ Success: Found $($response.Count) challenges with available spots" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 7: Search challenges by title
Write-Host "7. Testing GET /api/eco-challenges/search?title=plastic" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/search?title=plastic" -Method GET
    Write-Host "✓ Success: Found $($response.Count) challenges matching 'plastic'" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 8: Filter challenges by criteria
Write-Host "8. Testing GET /api/eco-challenges/filter?category=Energy&minPoints=50" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/filter?category=Energy&minPoints=50" -Method GET
    Write-Host "✓ Success: Found $($response.Count) Energy challenges with 50+ points" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 9: Get specific challenge by ID (if challenges exist)
Write-Host "9. Testing GET /api/eco-challenges/1" -ForegroundColor Cyan
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/1" -Method GET
    Write-Host "✓ Success: Found challenge with ID 1: $($response.title)" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

Write-Host "Eco Challenge API Testing Complete!" -ForegroundColor Green
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Yellow
Write-Host "1. Test user challenge endpoints (join, progress, complete)" -ForegroundColor Gray
Write-Host "2. Test challenge creation (admin endpoints)" -ForegroundColor Gray
Write-Host "3. Integrate with frontend application" -ForegroundColor Gray
