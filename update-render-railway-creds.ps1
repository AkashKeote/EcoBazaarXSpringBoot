# PowerShell script to update Render backend with Railway MySQL credentials

Write-Host "🚀 Updating Render Backend with Railway MySQL Credentials..." -ForegroundColor Green

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
Write-Host "📋 Railway MySQL Database Credentials:" -ForegroundColor Cyan
Write-Host "Username: root" -ForegroundColor White
Write-Host "Password: cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr" -ForegroundColor White
Write-Host "Database: ecobazaar_db" -ForegroundColor White
Write-Host "Host: mysql-production-59d9.up.railway.app" -ForegroundColor White
Write-Host "Port: 33060" -ForegroundColor White
Write-Host ""

Write-Host "🔧 Updating Render backend environment variables..." -ForegroundColor Green

# Update environment variables
Write-Host "Setting SPRING_PROFILES_ACTIVE=prod..." -ForegroundColor Yellow
render service update EcoBazaarXSpringBoot-1 --env SPRING_PROFILES_ACTIVE=prod

Write-Host "Setting DATABASE_URL..." -ForegroundColor Yellow
$databaseUrl = "jdbc:mysql://mysql-production-59d9.up.railway.app:33060/ecobazaar_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000"
render service update EcoBazaarXSpringBoot-1 --env DATABASE_URL="$databaseUrl"

Write-Host "Setting DB_USERNAME=root..." -ForegroundColor Yellow
render service update EcoBazaarXSpringBoot-1 --env DB_USERNAME="root"

Write-Host "Setting DB_PASSWORD..." -ForegroundColor Yellow
render service update EcoBazaarXSpringBoot-1 --env DB_PASSWORD="cOkdTzTVvUIwNKaGinyHGFGipGhBqYWr"

Write-Host ""
Write-Host "✅ Environment variables updated successfully!" -ForegroundColor Green
Write-Host "📊 Monitor your deployment at: https://dashboard.render.com" -ForegroundColor Cyan
Write-Host "🔍 Check logs for deployment progress and any issues." -ForegroundColor Yellow
Write-Host ""
Write-Host "📝 After deployment, test your backend:" -ForegroundColor White
Write-Host "   Health endpoint: https://ecobazaarxspringboot-1.onrender.com/api/health" -ForegroundColor Gray
Write-Host "   Migration endpoint: https://ecobazaarxspringboot-1.onrender.com/api/migration/start" -ForegroundColor Gray
Write-Host ""
Write-Host "🎯 Expected results:" -ForegroundColor Green
Write-Host "   ✅ Successful Railway MySQL connection" -ForegroundColor White
Write-Host "   ✅ No more 'Communications link failure' errors" -ForegroundColor White
Write-Host "   ✅ Backend starts successfully" -ForegroundColor White
Write-Host "   ✅ Ready for Firebase data migration" -ForegroundColor White
