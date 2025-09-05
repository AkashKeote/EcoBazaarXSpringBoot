Write-Host "Deploying MySQL to Render..." -ForegroundColor Green
Write-Host ""

# Copy the MySQL render config to main render.yaml
Copy-Item "render-mysql.yaml" "render.yaml"

Write-Host "MySQL deployment configuration ready!" -ForegroundColor Yellow
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Cyan
Write-Host "1. Push to GitHub: git add . && git commit -m 'Deploy MySQL' && git push"
Write-Host "2. Deploy on Render using render.yaml"
Write-Host "3. Note the MySQL service URL for backend connection"
Write-Host ""
Read-Host "Press Enter to continue"
