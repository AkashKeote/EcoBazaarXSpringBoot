# PowerShell script for complete Firebase to Railway MySQL migration setup

Write-Host "🚀 Complete Firebase to Railway MySQL Migration Setup" -ForegroundColor Green
Write-Host "=================================================" -ForegroundColor Green

# Check if we're in the right directory
if (-not (Test-Path "src/main/java/com/ecobazaar/backend/service/DataMigrationService.java")) {
    Write-Host "❌ Please run this script from the EcoBazaarX-Backend root directory" -ForegroundColor Red
    exit 1
}

Write-Host "✅ Found DataMigrationService.java" -ForegroundColor Green

# Create exports directory
$exportsDir = "src/main/resources/firebase-exports"
if (-not (Test-Path $exportsDir)) {
    New-Item -ItemType Directory -Path $exportsDir -Force
    Write-Host "✅ Created exports directory: $exportsDir" -ForegroundColor Green
} else {
    Write-Host "✅ Exports directory already exists: $exportsDir" -ForegroundColor Green
}

Write-Host ""
Write-Host "📋 Collections to migrate from Firebase:" -ForegroundColor Cyan
Write-Host "1. users" -ForegroundColor White
Write-Host "2. products" -ForegroundColor White
Write-Host "3. stores" -ForegroundColor White
Write-Host "4. wishlists" -ForegroundColor White
Write-Host "5. wishlistItems" -ForegroundColor White
Write-Host "6. carts" -ForegroundColor White
Write-Host "7. ecoChallenges" -ForegroundColor White
Write-Host "8. paymentTransactions" -ForegroundColor White
Write-Host "9. userOrders" -ForegroundColor White
Write-Host "10. userSettings" -ForegroundColor White

Write-Host ""
Write-Host "🔧 Step 1: Export Data from Firebase Console" -ForegroundColor Yellow
Write-Host "1. Go to Firebase Console: https://console.firebase.google.com" -ForegroundColor White
Write-Host "2. Select your project" -ForegroundColor White
Write-Host "3. Go to Firestore Database" -ForegroundColor White
Write-Host "4. For each collection above:" -ForegroundColor White
Write-Host "   - Click on collection name" -ForegroundColor Gray
Write-Host "   - Export as JSON (if available)" -ForegroundColor Gray
Write-Host "   - Or copy data manually" -ForegroundColor Gray

Write-Host ""
Write-Host "📁 Step 2: Place Export Files" -ForegroundColor Yellow
Write-Host "Place your Firebase export files in: $exportsDir" -ForegroundColor White
Write-Host "Required files:" -ForegroundColor White
Write-Host "  - users.json" -ForegroundColor Gray
Write-Host "  - products.json" -ForegroundColor Gray
Write-Host "  - stores.json" -ForegroundColor Gray
Write-Host "  - wishlists.json" -ForegroundColor Gray
Write-Host "  - wishlistItems.json" -ForegroundColor Gray
Write-Host "  - carts.json" -ForegroundColor Gray
Write-Host "  - ecoChallenges.json" -ForegroundColor Gray
Write-Host "  - paymentTransactions.json" -ForegroundColor Gray
Write-Host "  - userOrders.json" -ForegroundColor Gray
Write-Host "  - userSettings.json" -ForegroundColor Gray

Write-Host ""
$proceed = Read-Host "Have you placed the Firebase export files in $exportsDir? (y/n)"

if ($proceed -ne "y" -and $proceed -ne "Y") {
    Write-Host "❌ Please place the Firebase export files first." -ForegroundColor Red
    Write-Host "   Then run this script again." -ForegroundColor Yellow
    exit 1
}

# Check if export files exist
$requiredFiles = @("users.json", "products.json", "stores.json", "wishlists.json", "wishlistItems.json", "carts.json", "ecoChallenges.json", "paymentTransactions.json", "userOrders.json", "userSettings.json")
$missingFiles = @()

foreach ($file in $requiredFiles) {
    if (-not (Test-Path "$exportsDir/$file")) {
        $missingFiles += $file
    }
}

if ($missingFiles.Count -gt 0) {
    Write-Host "⚠️  Missing export files:" -ForegroundColor Yellow
    foreach ($file in $missingFiles) {
        Write-Host "   - $file" -ForegroundColor Red
    }
    Write-Host ""
    $continue = Read-Host "Continue with available files? (y/n)"
    if ($continue -ne "y" -and $continue -ne "Y") {
        exit 1
    }
}

Write-Host ""
Write-Host "🔧 Step 3: Update Render Environment Variables" -ForegroundColor Yellow
Write-Host "1. Go to Render Dashboard: https://dashboard.render.com" -ForegroundColor White
Write-Host "2. Open your backend service: EcoBazaarXSpringBoot-1" -ForegroundColor White
Write-Host "3. Click Environment tab" -ForegroundColor White
Write-Host "4. Add these variables:" -ForegroundColor White
Write-Host ""
Write-Host "   SPRING_PROFILES_ACTIVE = prod" -ForegroundColor Gray
Write-Host "   DATABASE_URL = jdbc:mysql://mysql-production-59d9.up.railway.app:33060/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000" -ForegroundColor Gray
Write-Host "   DB_USERNAME = [your_railway_username]" -ForegroundColor Gray
Write-Host "   DB_PASSWORD = [your_railway_password]" -ForegroundColor Gray

Write-Host ""
$envUpdated = Read-Host "Have you updated the Render environment variables? (y/n)"

if ($envUpdated -ne "y" -and $envUpdated -ne "Y") {
    Write-Host "❌ Please update the Render environment variables first." -ForegroundColor Red
    Write-Host "   Then run this script again." -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "🚀 Step 4: Deploy Backend" -ForegroundColor Yellow

# Add files to git
Write-Host "Adding files to git..." -ForegroundColor Yellow
git add .

# Commit changes
Write-Host "Committing changes..." -ForegroundColor Yellow
git commit -m "Add Firebase export files for Railway MySQL migration

- Added Firebase export files for data migration
- Ready to migrate from Firebase to Railway MySQL
- Backend configured for Railway MySQL connection"

# Push to trigger deployment
Write-Host "Pushing to GitHub to trigger Render deployment..." -ForegroundColor Yellow
git push origin main

Write-Host ""
Write-Host "✅ Deployment initiated!" -ForegroundColor Green
Write-Host "📊 Monitor deployment at: https://dashboard.render.com" -ForegroundColor Cyan
Write-Host "🔍 Check logs for deployment progress and any issues." -ForegroundColor Yellow

Write-Host ""
Write-Host "🔧 Step 5: Run Data Migration" -ForegroundColor Yellow
Write-Host "After deployment completes, run migration:" -ForegroundColor White
Write-Host ""
Write-Host "   curl -X POST https://ecobazaarxspringboot-1.onrender.com/api/migration/start" -ForegroundColor Gray
Write-Host ""
Write-Host "Or test health endpoint:" -ForegroundColor White
Write-Host "   curl https://ecobazaarxspringboot-1.onrender.com/api/health" -ForegroundColor Gray

Write-Host ""
Write-Host "🎯 Expected Results:" -ForegroundColor Green
Write-Host "   ✅ Database connected to Railway MySQL" -ForegroundColor White
Write-Host "   ✅ All Firebase data migrated to MySQL" -ForegroundColor White
Write-Host "   ✅ Backend running successfully" -ForegroundColor White
Write-Host "   ✅ API endpoints responding" -ForegroundColor White

Write-Host ""
Write-Host "🚀 Migration setup complete! Monitor the deployment and run migration when ready." -ForegroundColor Green
