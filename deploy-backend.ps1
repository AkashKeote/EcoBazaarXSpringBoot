# PowerShell script to deploy EcoBazaarX Backend to Render
# This script deploys the backend service to connect to the existing database

Write-Host "🚀 Deploying EcoBazaarX Backend to Render..." -ForegroundColor Green

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
Write-Host "📋 Deployment Options:" -ForegroundColor Cyan
Write-Host "1. Deploy backend only (recommended - database is already running)" -ForegroundColor White
Write-Host "2. Deploy complete configuration (database + backend)" -ForegroundColor White
Write-Host ""

$choice = Read-Host "Enter your choice (1 or 2)"

switch ($choice) {
    "1" {
        Write-Host "🚀 Deploying backend service only..." -ForegroundColor Green
        Write-Host "Using configuration: render-backend-fixed.yaml" -ForegroundColor Yellow
        
        if (Test-Path "render-backend-fixed.yaml") {
            render deploy --config render-backend-fixed.yaml
        } else {
            Write-Host "❌ render-backend-fixed.yaml not found!" -ForegroundColor Red
            exit 1
        }
    }
    "2" {
        Write-Host "🚀 Deploying complete configuration..." -ForegroundColor Green
        Write-Host "Using configuration: render-complete.yaml" -ForegroundColor Yellow
        
        if (Test-Path "render-complete.yaml") {
            render deploy --config render-complete.yaml
        } else {
            Write-Host "❌ render-complete.yaml not found!" -ForegroundColor Red
            exit 1
        }
    }
    default {
        Write-Host "❌ Invalid choice. Please run the script again and choose 1 or 2." -ForegroundColor Red
        exit 1
    }
}

Write-Host ""
Write-Host "✅ Deployment initiated!" -ForegroundColor Green
Write-Host "📊 Monitor your deployment at: https://dashboard.render.com" -ForegroundColor Cyan
Write-Host "🔍 Check logs for deployment progress and any issues." -ForegroundColor Yellow
Write-Host ""
Write-Host "📝 After deployment, test your backend:" -ForegroundColor White
Write-Host "   Health endpoint: https://your-backend-url.onrender.com/api/health" -ForegroundColor Gray
Write-Host "   API endpoints: https://your-backend-url.onrender.com/api/" -ForegroundColor Gray