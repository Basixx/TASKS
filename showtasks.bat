call C:\PROJECTS\tasks\runcrud.bat
if "%ERRORLEVEL%" == "0" goto openbrowser
echo Cannot call runcrud
goto fail

:openbrowser
start chrome http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo Cannot open browser
goto fail

:fail
echo.
echo There were errors

:end
echo.
echo Tasks are shown