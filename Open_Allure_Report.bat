@echo off

REM Get the Chrome executable path from the registry
set "CHROME_PATH="
for /f "tokens=2,*" %%A in (
    'reg query "HKEY_LOCAL_MACHINE\SOFTWARE\Clients\StartMenuInternet\Google Chrome\shell\open\command" /ve 2^>nul'
) do set "CHROME_PATH=%%~B"
if not defined CHROME_PATH (
    for /f "tokens=2,*" %%A in (
        'reg query "HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\Clients\StartMenuInternet\Google Chrome\shell\open\command" /ve 2^>nul'
    ) do set "CHROME_PATH=%%~B"
)

REM Check if Chrome was found
if not defined CHROME_PATH (
    echo Google Chrome executable not found.
    exit /b
)

REM Set the path to the Allure report
set "ALLURE_REPORT_PATH=%cd%\target\site\allure-maven-plugin\index.html"

REM Check if the Allure report exists
if not exist "%ALLURE_REPORT_PATH%" (
    echo Allure report not found. Please ensure it has been generated.
    exit /b
)

REM Launch Chrome with the local file access flag to open the Allure report
start "" "%CHROME_PATH%" --allow-file-access-from-files "%ALLURE_REPORT_PATH%"