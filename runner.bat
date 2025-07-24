@echo off
SETLOCAL

echo Running Maven tests...
mvn clean test
echo Generating and launching Allure report...
allure serve allure-results
echo Test run and report generation completed successfully.
echo Press any key to exit...
pause >nul

ENDLOCAL
