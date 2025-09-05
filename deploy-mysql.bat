@echo off
echo Deploying MySQL to Render...
echo.

REM Copy the MySQL render config to main render.yaml
copy render-mysql.yaml render.yaml

echo MySQL deployment configuration ready!
echo.
echo Next steps:
echo 1. Push to GitHub: git add . && git commit -m "Deploy MySQL" && git push
echo 2. Deploy on Render using render.yaml
echo 3. Note the MySQL service URL for backend connection
echo.
pause
