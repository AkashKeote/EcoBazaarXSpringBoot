@echo off
REM Batch script to update Render backend with CORRECT Railway MySQL configuration

echo 🚀 Updating Render Backend with CORRECT Railway MySQL Configuration...

REM Check if render CLI is installed
render --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Render CLI not found. Please install it first:
    echo    npm install -g @render/cli
    echo    Or download from: https://render.com/docs/cli
    echo.
    echo 📝 Manual Setup Required:
    echo 1. Go to: https://dashboard.render.com
    echo 2. Open: EcoBazaarXSpringBoot-1
    echo 3. Environment tab
    echo 4. Add these variables:
    echo.
    echo    SPRING_PROFILES_ACTIVE = prod
    echo    DATABASE_URL = jdbc:mysql://mysql-production-59d9.up.railway.app:3306/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
    echo    DB_USERNAME = root
    echo    DB_PASSWORD = cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr
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
echo 📋 CORRECT Railway MySQL Configuration:
echo Database: railway
echo Username: root
echo Password: cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr
echo Host: mysql-production-59d9.up.railway.app
echo Port: 3306
echo.

echo 🔧 Updating Render backend environment variables...

REM Update environment variables
echo Setting SPRING_PROFILES_ACTIVE=prod...
render service update EcoBazaarXSpringBoot-1 --env SPRING_PROFILES_ACTIVE=prod

echo Setting CORRECT DATABASE_URL...
set databaseUrl=jdbc:mysql://mysql-production-59d9.up.railway.app:3306/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
render service update EcoBazaarXSpringBoot-1 --env DATABASE_URL="%databaseUrl%"

echo Setting DB_USERNAME=root...
render service update EcoBazaarXSpringBoot-1 --env DB_USERNAME="root"

echo Setting DB_PASSWORD...
render service update EcoBazaarXSpringBoot-1 --env DB_PASSWORD="cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr"

echo.
echo ✅ Environment variables updated with CORRECT configuration!
echo 📊 Monitor your deployment at: https://dashboard.render.com
echo 🔍 Check logs for deployment progress and any issues.
echo.
echo 📝 After deployment, test your backend:
echo    Health endpoint: https://ecobazaarxspringboot-1.onrender.com/api/health
echo    Migration endpoint: https://ecobazaarxspringboot-1.onrender.com/api/migration/start
echo.
echo 🎯 Expected results:
echo    ✅ Successful Railway MySQL connection
echo    ✅ No more 'Communications link failure' errors
echo    ✅ Backend starts successfully
echo    ✅ Ready for Firebase data migration

pause
