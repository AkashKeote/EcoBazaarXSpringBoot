Write-Host "EcoBazaarDb - Manual Deployment to Render" -ForegroundColor Green
Write-Host "=============================================" -ForegroundColor Green
Write-Host ""

Write-Host "This script will help you deploy MySQL database to Render manually." -ForegroundColor Yellow
Write-Host ""

Write-Host "Prerequisites:" -ForegroundColor Cyan
Write-Host "1. Git installed and configured" -ForegroundColor White
Write-Host "2. GitHub account" -ForegroundColor White
Write-Host "3. Render account" -ForegroundColor White
Write-Host ""

Write-Host "Steps to follow:" -ForegroundColor Cyan
Write-Host "1. Test locally (optional)" -ForegroundColor White
Write-Host "2. Create GitHub repository" -ForegroundColor White
Write-Host "3. Push code to GitHub" -ForegroundColor White
Write-Host "4. Deploy on Render" -ForegroundColor White
Write-Host ""

$choice = Read-Host "Do you want to test MySQL locally first? (y/n)"

if ($choice -eq "y" -or $choice -eq "Y") {
    Write-Host ""
    Write-Host "Testing MySQL locally..." -ForegroundColor Yellow
    Write-Host "This will start MySQL container and test connection." -ForegroundColor White
    Write-Host ""
    
    $testChoice = Read-Host "Continue with local test? (y/n)"
    
    if ($testChoice -eq "y" -or $testChoice -eq "Y") {
        Write-Host "Starting local MySQL test..." -ForegroundColor Yellow
        docker-compose up -d
        
        Write-Host ""
        Write-Host "Waiting for MySQL to start..." -ForegroundColor Yellow
        Start-Sleep -Seconds 30
        
        Write-Host ""
        Write-Host "Testing MySQL connection..." -ForegroundColor Yellow
        try {
            docker exec ecobazaardb mysql -u root -prootpassword -e "SHOW DATABASES;"
            Write-Host "✅ MySQL is working correctly!" -ForegroundColor Green
        } catch {
            Write-Host "❌ MySQL test failed. Check logs." -ForegroundColor Red
        }
        
        Write-Host ""
        $stopChoice = Read-Host "Stop local MySQL container? (y/n)"
        if ($stopChoice -eq "y" -or $stopChoice -eq "Y") {
            docker-compose down
            Write-Host "Local MySQL stopped." -ForegroundColor Yellow
        }
    }
}

Write-Host ""
Write-Host "Next steps for manual deployment:" -ForegroundColor Cyan
Write-Host "1. Create GitHub repository named 'EcobazaarDb'" -ForegroundColor White
Write-Host "2. Run these commands:" -ForegroundColor White
Write-Host ""
Write-Host "   git init" -ForegroundColor Gray
Write-Host "   git add ." -ForegroundColor Gray
Write-Host "   git commit -m 'Initial MySQL database service'" -ForegroundColor Gray
Write-Host "   git remote add origin https://github.com/YOUR_USERNAME/EcobazaarDb.git" -ForegroundColor Gray
Write-Host "   git push -u origin main" -ForegroundColor Gray
Write-Host ""
Write-Host "3. Go to render.com and create new Web Service" -ForegroundColor White
Write-Host "4. Connect your GitHub repository" -ForegroundColor White
Write-Host "5. Configure service with these settings:" -ForegroundColor White
Write-Host "   - Name: ecobazaardb" -ForegroundColor Gray
Write-Host "   - Environment: Docker" -ForegroundColor Gray
Write-Host "   - Dockerfile Path: ./Dockerfile" -ForegroundColor Gray
Write-Host "   - Plan: Free" -ForegroundColor Gray
Write-Host ""
Write-Host "6. Deploy and note the service URL" -ForegroundColor White
Write-Host ""

Write-Host "Service URL will be: https://ecobazaardb.onrender.com" -ForegroundColor Magenta
Write-Host ""

Write-Host "For detailed instructions, see MANUAL_DEPLOYMENT.md" -ForegroundColor Yellow
Write-Host ""

Read-Host "Press Enter to continue"