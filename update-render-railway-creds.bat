@echo off
REM Batch script to update Render backend with Railway MySQL credentials

echo 🚀 Updating Render Backend with Railway MySQL Credentials...

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
echo 📋 Railway MySQL Database Credentials:
echo Username: root
echo Password: cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr
echo Database: ecobazaar_db
echo Host: mysql-production-59d9.up.railway.app
echo Port: 33060
echo.

echo 🔧 Updating Render backend environment variables...

REM Update environment variables
echo Setting SPRING_PROFILES_ACTIVE=prod...
render service update EcoBazaarXSpringBoot-1 --env SPRING_PROFILES_ACTIVE=prod

echo Setting DATABASE_URL...
set databaseUrl=jdbc:mysql://mysql-production-59d9.up.railway.app:33060/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000
render service update EcoBazaarXSpringBoot-1 --env DATABASE_URL="%databaseUrl%"

echo Setting DB_USERNAME=root...
render service update EcoBazaarXSpringBoot-1 --env DB_USERNAME="root"

echo Setting DB_PASSWORD...
render service update EcoBazaarXSpringBoot-1 --env DB_PASSWORD="cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr"

echo.
echo ✅ Environment variables updated successfully!
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
