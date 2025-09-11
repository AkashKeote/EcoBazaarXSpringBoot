# PowerShell script to deploy CORS fix to Render
# This script builds and deploys the updated backend with CORS fixes

Write-Host "🚀 Deploying CORS Fix to Render..." -ForegroundColor Green

# Build the application
Write-Host "📦 Building application..." -ForegroundColor Yellow
./mvnw clean package -DskipTests

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Build failed!" -ForegroundColor Red
    exit 1
}

Write-Host "✅ Build successful!" -ForegroundColor Green

# Check if we're in a git repository
if (Test-Path ".git") {
    Write-Host "📝 Committing CORS fixes..." -ForegroundColor Yellow
    
    # Add all changes
    git add .
    
    # Commit with descriptive message
    git commit -m "Fix CORS configuration for frontend domain

- Updated application-prod.properties to include https://ecobazzarx.web.app
- Reordered CORS origins in SecurityConfig to prioritize frontend domain
- Removed conflicting @CrossOrigin annotations from controllers
- Added HEAD method and maxAge to CORS configuration
- Fixed CORS conflicts between global and method-level configurations

This should resolve the CORS errors preventing frontend from accessing backend APIs."

    # Push to trigger Render deployment
    Write-Host "🚀 Pushing to trigger Render deployment..." -ForegroundColor Yellow
    git push origin main
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Successfully pushed to repository!" -ForegroundColor Green
        Write-Host "🔄 Render should now be deploying the updated backend..." -ForegroundColor Cyan
        Write-Host "⏳ Please wait 2-3 minutes for deployment to complete" -ForegroundColor Yellow
        Write-Host "🌐 Check your Render dashboard for deployment status" -ForegroundColor Cyan
    } else {
        Write-Host "❌ Failed to push to repository!" -ForegroundColor Red
        exit 1
    }
} else {
    Write-Host "⚠️  Not in a git repository. Please manually deploy the built JAR file." -ForegroundColor Yellow
    Write-Host "📁 Built JAR location: target/ecobazaar-backend-1.0.0.jar" -ForegroundColor Cyan
}

Write-Host "🎉 CORS fix deployment process completed!" -ForegroundColor Green
Write-Host "🔗 Backend URL: https://ecobazaarxspringboot-1.onrender.com" -ForegroundColor Cyan
Write-Host "🔗 Frontend URL: https://ecobazzarx.web.app" -ForegroundColor Cyan
