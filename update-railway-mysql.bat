@echo off
REM Batch script to update Render backend for Railway MySQL database

echo 🚀 Updating Render Backend for Railway MySQL Database...

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
echo 📋 Railway MySQL Database Information:
echo Public Domain: mysql-production-59d9.up.railway.app
echo Public Port: 33060
echo Proxy Connection: centerbeam.proxy.rlwy.net:36676
echo.

echo 🔧 Before proceeding, make sure you have:
echo 1. Railway MySQL database credentials (username/password)
echo 2. Database name (ecobazaar_db)
echo.

set /p proceed="Do you have the Railway database credentials? (y/n): "

if /i not "%proceed%"=="y" (
    echo ❌ Please get your Railway database credentials first.
    echo    Go to Railway → Your MySQL service → Variables tab
    pause
    exit /b 1
)

echo.
echo 📝 Please provide your Railway database credentials:

set /p dbUsername="Enter Railway MySQL username: "
set /p dbPassword="Enter Railway MySQL password: "

echo.
echo 🔧 Updating Render backend environment variables...

REM Update environment variables
echo Setting SPRING_PROFILES_ACTIVE=prod...
render service update EcoBazaarXSpringBoot-1 --env SPRING_PROFILES_ACTIVE=prod

echo Setting DATABASE_URL...
set databaseUrl=jdbc:mysql://mysql-production-59d9.up.railway.app:33060/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
render service update EcoBazaarXSpringBoot-1 --env DATABASE_URL="%databaseUrl%"

echo Setting DB_USERNAME...
render service update EcoBazaarXSpringBoot-1 --env DB_USERNAME="%dbUsername%"

echo Setting DB_PASSWORD...
render service update EcoBazaarXSpringBoot-1 --env DB_PASSWORD="%dbPassword%"

echo.
echo ✅ Environment variables updated successfully!
echo 📊 Monitor your deployment at: https://dashboard.render.com
echo 🔍 Check logs for deployment progress and any issues.
echo.
echo 📝 After deployment, test your backend:
echo    Health endpoint: https://ecobazaarxspringboot-1.onrender.com/api/health
echo    API endpoints: https://ecobazaarxspringboot-1.onrender.com/api/
echo.
echo 🎯 Expected results:
echo    ✅ Successful database connection
echo    ✅ No more 'Communications link failure' errors
echo    ✅ Backend starts successfully

pause
