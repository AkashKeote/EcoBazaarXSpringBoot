-- Fix Eco Challenges Database Structure
-- This script will clean up the old eco_challenges table and let Hibernate recreate it

-- Drop the existing eco_challenges table to remove old structure
DROP TABLE IF EXISTS eco_challenges;

-- The table will be automatically recreated by Hibernate with the correct structure
-- when the application starts due to ddl-auto=update

-- Note: This will remove all existing eco challenges data
-- The DataInitializationService will recreate sample data automatically
