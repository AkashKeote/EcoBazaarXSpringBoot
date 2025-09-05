@echo off
echo Starting data migration from Firestore to MySQL...
echo.

REM Build the project
echo Building the project...
call mvnw clean compile -DskipTests

REM Run the migration
echo Running migration...
call mvnw exec:java -Dexec.mainClass="com.ecobazaar.backend.MigrationRunner" -Dexec.args="migrate" -Dspring.profiles.active=prod

echo.
echo Migration completed!
pause
