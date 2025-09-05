@echo off
echo EcoBazaarDb - Manual Deployment to Render
echo =============================================
echo.

echo This script will help you deploy MySQL database to Render manually.
echo.

echo Prerequisites:
echo 1. Git installed and configured
echo 2. GitHub account
echo 3. Render account
echo.

echo Steps to follow:
echo 1. Test locally (optional)
echo 2. Create GitHub repository
echo 3. Push code to GitHub
echo 4. Deploy on Render
echo.

set /p choice="Do you want to test MySQL locally first? (y/n): "

if /i "%choice%"=="y" (
    echo.
    echo Testing MySQL locally...
    echo This will start MySQL container and test connection.
    echo.
    set /p testChoice="Continue with local test? (y/n): "
    
    if /i "!testChoice!"=="y" (
        echo Starting local MySQL test...
        docker-compose up -d
        
        echo.
        echo Waiting for MySQL to start...
        timeout 30
        
        echo.
        echo Testing MySQL connection...
        docker exec ecobazaardb mysql -u root -prootpassword -e "SHOW DATABASES;"
        
        echo.
        set /p stopChoice="Stop local MySQL container? (y/n): "
        if /i "!stopChoice!"=="y" (
            docker-compose down
            echo Local MySQL stopped.
        )
    )
)

echo.
echo Next steps for manual deployment:
echo 1. Create GitHub repository named 'EcobazaarDb'
echo 2. Run these commands:
echo.
echo    git init
echo    git add .
echo    git commit -m "Initial MySQL database service"
echo    git remote add origin https://github.com/YOUR_USERNAME/EcobazaarDb.git
echo    git push -u origin main
echo.
echo 3. Go to render.com and create new Web Service
echo 4. Connect your GitHub repository
echo 5. Configure service with these settings:
echo    - Name: ecobazaardb
echo    - Environment: Docker
echo    - Dockerfile Path: ./Dockerfile
echo    - Plan: Free
echo.
echo 6. Deploy and note the service URL
echo.

echo Service URL will be: https://ecobazaardb.onrender.com
echo.

echo For detailed instructions, see MANUAL_DEPLOYMENT.md
echo.

pause
