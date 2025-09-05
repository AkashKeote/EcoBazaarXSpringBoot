@echo off
echo Deploying Spring Boot Backend to Render...
echo.

REM Copy the backend render config to main render.yaml
copy render-backend.yaml render.yaml

echo Backend deployment configuration ready!
echo.
echo Next steps:
echo 1. Update DATABASE_URL in render-backend.yaml with your MySQL service URL
echo 2. Push to GitHub: git add . && git commit -m "Deploy Backend" && git push
echo 3. Deploy on Render using render.yaml
echo.
echo Note: Make sure MySQL service is deployed first!
echo.
pause
