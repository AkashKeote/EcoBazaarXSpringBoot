@echo off
REM Batch script to update backend environment variables for PostgreSQL
REM Run this after creating the PostgreSQL database service

echo 🔧 Updating Backend Environment Variables for PostgreSQL...

REM Check if render CLI is installed
render --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Render CLI not found. Please install it first:
    echo    npm install -g @render/cli
    echo    Or download from: https://render.com/docs/cli
    pause
    exit /b 1
)

echo ✅ Render CLI found

REM Check if user is logged in
render whoami >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Not logged in to Render. Please login first:
    echo    render login
    pause
    exit /b 1
)

echo ✅ Logged in to Render

echo.
echo 📋 Before running this script, make sure you have:
echo 1. Created a PostgreSQL database service on Render
echo 2. Copied the database connection details
echo.

set /p proceed="Have you created the PostgreSQL database service? (y/n): "

if /i not "%proceed%"=="y" (
    echo ❌ Please create the PostgreSQL database service first.
    echo    Go to Render Dashboard → New + → PostgreSQL
    pause
    exit /b 1
)

echo.
echo 🔧 Updating backend service environment variables...

REM Update environment variables
echo Setting SPRING_PROFILES_ACTIVE=postgresql...
render service update EcoBazaarXSpringBoot-1 --env SPRING_PROFILES_ACTIVE=postgresql

echo.
echo 📝 Now you need to manually set these environment variables in Render Dashboard:
echo 1. Go to your backend service: EcoBazaarXSpringBoot-1
echo 2. Click 'Environment' tab
echo 3. Add these variables:
echo.
echo    DATABASE_URL = [Copy from your PostgreSQL service]
echo    DB_USERNAME = [Copy from your PostgreSQL service]
echo    DB_PASSWORD = [Copy from your PostgreSQL service]
echo.
echo 4. Save the changes
echo 5. The service will automatically redeploy

echo.
echo ✅ Environment variable update initiated!
echo 📊 Monitor your deployment at: https://dashboard.render.com
echo 🔍 Check logs for deployment progress and any issues.
echo.
echo 📝 After deployment, test your backend:
echo    Health endpoint: https://ecobazaarxspringboot-1.onrender.com/api/health
echo    API endpoints: https://ecobazaarxspringboot-1.onrender.com/api/

pause
