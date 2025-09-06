# PowerShell script to update Render backend for Railway MySQL database

Write-Host "🚀 Updating Render Backend for Railway MySQL Database..." -ForegroundColor Green

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
Write-Host "📋 Railway MySQL Database Information:" -ForegroundColor Cyan
Write-Host "Public Domain: mysql-production-59d9.up.railway.app" -ForegroundColor White
Write-Host "Public Port: 33060" -ForegroundColor White
Write-Host "Proxy Connection: centerbeam.proxy.rlwy.net:36676" -ForegroundColor White
Write-Host ""

Write-Host "🔧 Before proceeding, make sure you have:" -ForegroundColor Yellow
Write-Host "1. Railway MySQL database credentials (username/password)" -ForegroundColor White
Write-Host "2. Database name (ecobazaar_db)" -ForegroundColor White
Write-Host ""

$proceed = Read-Host "Do you have the Railway database credentials? (y/n)"

if ($proceed -ne "y" -and $proceed -ne "Y") {
    Write-Host "❌ Please get your Railway database credentials first." -ForegroundColor Red
    Write-Host "   Go to Railway → Your MySQL service → Variables tab" -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "📝 Please provide your Railway database credentials:" -ForegroundColor Cyan

$dbUsername = Read-Host "Enter Railway MySQL username"
$dbPassword = Read-Host "Enter Railway MySQL password" -AsSecureString
$dbPasswordPlain = [Runtime.InteropServices.Marshal]::PtrToStringAuto([Runtime.InteropServices.Marshal]::SecureStringToBSTR($dbPassword))

Write-Host ""
Write-Host "🔧 Updating Render backend environment variables..." -ForegroundColor Green

# Update environment variables
Write-Host "Setting SPRING_PROFILES_ACTIVE=prod..." -ForegroundColor Yellow
render service update EcoBazaarXSpringBoot-1 --env SPRING_PROFILES_ACTIVE=prod

Write-Host "Setting DATABASE_URL..." -ForegroundColor Yellow
$databaseUrl = "jdbc:mysql://mysql-production-59d9.up.railway.app:33060/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000"
render service update EcoBazaarXSpringBoot-1 --env DATABASE_URL="$databaseUrl"

Write-Host "Setting DB_USERNAME..." -ForegroundColor Yellow
render service update EcoBazaarXSpringBoot-1 --env DB_USERNAME="$dbUsername"

Write-Host "Setting DB_PASSWORD..." -ForegroundColor Yellow
render service update EcoBazaarXSpringBoot-1 --env DB_PASSWORD="$dbPasswordPlain"

Write-Host ""
Write-Host "✅ Environment variables updated successfully!" -ForegroundColor Green
Write-Host "📊 Monitor your deployment at: https://dashboard.render.com" -ForegroundColor Cyan
Write-Host "🔍 Check logs for deployment progress and any issues." -ForegroundColor Yellow
Write-Host ""
Write-Host "📝 After deployment, test your backend:" -ForegroundColor White
Write-Host "   Health endpoint: https://ecobazaarxspringboot-1.onrender.com/api/health" -ForegroundColor Gray
Write-Host "   API endpoints: https://ecobazaarxspringboot-1.onrender.com/api/" -ForegroundColor Gray
Write-Host ""
Write-Host "🎯 Expected results:" -ForegroundColor Green
Write-Host "   ✅ Successful database connection" -ForegroundColor White
Write-Host "   ✅ No more 'Communications link failure' errors" -ForegroundColor White
Write-Host "   ✅ Backend starts successfully" -ForegroundColor White
