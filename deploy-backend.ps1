Write-Host "Deploying Spring Boot Backend to Render..." -ForegroundColor Green
Write-Host ""

# Copy the backend render config to main render.yaml
Copy-Item "render-backend.yaml" "render.yaml"

Write-Host "Backend deployment configuration ready!" -ForegroundColor Yellow
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Cyan
Write-Host "1. Update DATABASE_URL in render-backend.yaml with your MySQL service URL"
Write-Host "2. Push to GitHub: git add . && git commit -m 'Deploy Backend' && git push"
Write-Host "3. Deploy on Render using render.yaml"
Write-Host ""
Write-Host "Note: Make sure MySQL service is deployed first!" -ForegroundColor Red
Write-Host ""
Read-Host "Press Enter to continue"
