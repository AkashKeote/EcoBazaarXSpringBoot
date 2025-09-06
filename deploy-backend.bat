@echo off
REM Batch script to deploy EcoBazaarX Backend to Render
REM This script deploys the backend service to connect to the existing database

echo 🚀 Deploying EcoBazaarX Backend to Render...

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
echo 📋 Deployment Options:
echo 1. Deploy backend only (recommended - database is already running)
echo 2. Deploy complete configuration (database + backend)
echo.

set /p choice="Enter your choice (1 or 2): "

if "%choice%"=="1" (
    echo 🚀 Deploying backend service only...
    echo Using configuration: render-backend-fixed.yaml
    
    if exist "render-backend-fixed.yaml" (
        render deploy --config render-backend-fixed.yaml
    ) else (
        echo ❌ render-backend-fixed.yaml not found!
        pause
        exit /b 1
    )
) else if "%choice%"=="2" (
    echo 🚀 Deploying complete configuration...
    echo Using configuration: render-complete.yaml
    
    if exist "render-complete.yaml" (
        render deploy --config render-complete.yaml
    ) else (
        echo ❌ render-complete.yaml not found!
        pause
        exit /b 1
    )
) else (
    echo ❌ Invalid choice. Please run the script again and choose 1 or 2.
    pause
    exit /b 1
)

echo.
echo ✅ Deployment initiated!
echo 📊 Monitor your deployment at: https://dashboard.render.com
echo 🔍 Check logs for deployment progress and any issues.
echo.
echo 📝 After deployment, test your backend:
echo    Health endpoint: https://your-backend-url.onrender.com/api/health
echo    API endpoints: https://your-backend-url.onrender.com/api/

pause