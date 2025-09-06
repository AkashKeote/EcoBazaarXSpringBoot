# PowerShell script to update backend environment variables for PostgreSQL
# Run this after creating the PostgreSQL database service

Write-Host "🔧 Updating Backend Environment Variables for PostgreSQL..." -ForegroundColor Green

# Check if render CLI is installed
try {
    $renderVersion = render --version
    Write-Host "✅ Render CLI found: $renderVersion" -ForegroundColor Green
} catch {
    Write-Host "❌ Render CLI not found. Please install it first:" -ForegroundColor Red
    Write-Host "   npm install -g @render/cli" -ForegroundColor Yellow
    Write-Host "   Or download from: https://render.com/docs/cli" -ForegroundColor Yellow
    exit 1
}

# Check if user is logged in
try {
    $user = render whoami
    Write-Host "✅ Logged in as: $user" -ForegroundColor Green
} catch {
    Write-Host "❌ Not logged in to Render. Please login first:" -ForegroundColor Red
    Write-Host "   render login" -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "📋 Before running this script, make sure you have:" -ForegroundColor Cyan
Write-Host "1. Created a PostgreSQL database service on Render" -ForegroundColor White
Write-Host "2. Copied the database connection details" -ForegroundColor White
Write-Host ""

$proceed = Read-Host "Have you created the PostgreSQL database service? (y/n)"

if ($proceed -ne "y" -and $proceed -ne "Y") {
    Write-Host "❌ Please create the PostgreSQL database service first." -ForegroundColor Red
    Write-Host "   Go to Render Dashboard → New + → PostgreSQL" -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "🔧 Updating backend service environment variables..." -ForegroundColor Green

# Update environment variables
Write-Host "Setting SPRING_PROFILES_ACTIVE=postgresql..." -ForegroundColor Yellow
render service update EcoBazaarXSpringBoot-1 --env SPRING_PROFILES_ACTIVE=postgresql

Write-Host ""
Write-Host "📝 Now you need to manually set these environment variables in Render Dashboard:" -ForegroundColor Cyan
Write-Host "1. Go to your backend service: EcoBazaarXSpringBoot-1" -ForegroundColor White
Write-Host "2. Click 'Environment' tab" -ForegroundColor White
Write-Host "3. Add these variables:" -ForegroundColor White
Write-Host ""
Write-Host "   DATABASE_URL = [Copy from your PostgreSQL service]" -ForegroundColor Gray
Write-Host "   DB_USERNAME = [Copy from your PostgreSQL service]" -ForegroundColor Gray
Write-Host "   DB_PASSWORD = [Copy from your PostgreSQL service]" -ForegroundColor Gray
Write-Host ""
Write-Host "4. Save the changes" -ForegroundColor White
Write-Host "5. The service will automatically redeploy" -ForegroundColor White

Write-Host ""
Write-Host "✅ Environment variable update initiated!" -ForegroundColor Green
Write-Host "📊 Monitor your deployment at: https://dashboard.render.com" -ForegroundColor Cyan
Write-Host "🔍 Check logs for deployment progress and any issues." -ForegroundColor Yellow
Write-Host ""
Write-Host "📝 After deployment, test your backend:" -ForegroundColor White
Write-Host "   Health endpoint: https://ecobazaarxspringboot-1.onrender.com/api/health" -ForegroundColor Gray
Write-Host "   API endpoints: https://ecobazaarxspringboot-1.onrender.com/api/" -ForegroundColor Gray
