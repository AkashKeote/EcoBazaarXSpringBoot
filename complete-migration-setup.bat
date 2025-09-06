@echo off
REM Batch script for complete Firebase to Railway MySQL migration setup

echo 🚀 Complete Firebase to Railway MySQL Migration Setup
echo =================================================

REM Check if we're in the right directory
if not exist "src\main\java\com\ecobazaar\backend\service\DataMigrationService.java" (
    echo ❌ Please run this script from the EcoBazaarX-Backend root directory
    pause
    exit /b 1
)

echo ✅ Found DataMigrationService.java

REM Create exports directory
if not exist "src\main\resources\firebase-exports" (
    mkdir "src\main\resources\firebase-exports"
    echo ✅ Created exports directory: src\main\resources\firebase-exports
) else (
    echo ✅ Exports directory already exists: src\main\resources\firebase-exports
)

echo.
echo 📋 Collections to migrate from Firebase:
echo 1. users
echo 2. products
echo 3. stores
echo 4. wishlists
echo 5. wishlistItems
echo 6. carts
echo 7. ecoChallenges
echo 8. paymentTransactions
echo 9. userOrders
echo 10. userSettings

echo.
echo 🔧 Step 1: Export Data from Firebase Console
echo 1. Go to Firebase Console: https://console.firebase.google.com
echo 2. Select your project
echo 3. Go to Firestore Database
echo 4. For each collection above:
echo    - Click on collection name
echo    - Export as JSON (if available)
echo    - Or copy data manually

echo.
echo 📁 Step 2: Place Export Files
echo Place your Firebase export files in: src\main\resources\firebase-exports
echo Required files:
echo   - users.json
echo   - products.json
echo   - stores.json
echo   - wishlists.json
echo   - wishlistItems.json
echo   - carts.json
echo   - ecoChallenges.json
echo   - paymentTransactions.json
echo   - userOrders.json
echo   - userSettings.json

echo.
set /p proceed="Have you placed the Firebase export files in src\main\resources\firebase-exports? (y/n): "

if /i not "%proceed%"=="y" (
    echo ❌ Please place the Firebase export files first.
    echo    Then run this script again.
    pause
    exit /b 1
)

echo.
echo 🔧 Step 3: Update Render Environment Variables
echo 1. Go to Render Dashboard: https://dashboard.render.com
echo 2. Open your backend service: EcoBazaarXSpringBoot-1
echo 3. Click Environment tab
echo 4. Add these variables:
echo.
echo    SPRING_PROFILES_ACTIVE = prod
echo    DATABASE_URL = jdbc:mysql://mysql-production-59d9.up.railway.app:33060/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
echo    DB_USERNAME = [your_railway_username]
echo    DB_PASSWORD = [your_railway_password]

echo.
set /p envUpdated="Have you updated the Render environment variables? (y/n): "

if /i not "%envUpdated%"=="y" (
    echo ❌ Please update the Render environment variables first.
    echo    Then run this script again.
    pause
    exit /b 1
)

echo.
echo 🚀 Step 4: Deploy Backend

REM Add files to git
echo Adding files to git...
git add .

REM Commit changes
echo Committing changes...
git commit -m "Add Firebase export files for Railway MySQL migration

- Added Firebase export files for data migration
- Ready to migrate from Firebase to Railway MySQL
- Backend configured for Railway MySQL connection"

REM Push to trigger deployment
echo Pushing to GitHub to trigger Render deployment...
git push origin main

echo.
echo ✅ Deployment initiated!
echo 📊 Monitor deployment at: https://dashboard.render.com
echo 🔍 Check logs for deployment progress and any issues.

echo.
echo 🔧 Step 5: Run Data Migration
echo After deployment completes, run migration:
echo.
echo    curl -X POST https://ecobazaarxspringboot-1.onrender.com/api/migration/start
echo.
echo Or test health endpoint:
echo    curl https://ecobazaarxspringboot-1.onrender.com/api/health

echo.
echo 🎯 Expected Results:
echo    ✅ Database connected to Railway MySQL
echo    ✅ All Firebase data migrated to MySQL
echo    ✅ Backend running successfully
echo    ✅ API endpoints responding

echo.
echo 🚀 Migration setup complete! Monitor the deployment and run migration when ready.

pause
