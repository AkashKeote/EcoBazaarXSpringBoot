Write-Host "Starting data migration from Firestore to MySQL..." -ForegroundColor Green
Write-Host ""

# Build the project
Write-Host "Building the project..." -ForegroundColor Yellow
& .\mvnw clean compile -DskipTests

if ($LASTEXITCODE -ne 0) {
    Write-Host "Build failed!" -ForegroundColor Red
    exit 1
}

# Run the migration
Write-Host "Running migration..." -ForegroundColor Yellow
& .\mvnw exec:java "-Dexec.mainClass=com.ecobazaar.backend.MigrationRunner" "-Dexec.args=migrate" "-Dspring.profiles.active=prod"

Write-Host ""
Write-Host "Migration completed!" -ForegroundColor Green
Read-Host "Press Enter to continue"
