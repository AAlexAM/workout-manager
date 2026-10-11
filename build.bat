@echo off
REM Builds a self-contained Windows app (no Java or JavaFX needed to run it).
REM Run from the project root. Requires JDK 25 and the JavaFX *jmods* (not the SDK jars).

REM === EDIT THESE TWO PATHS ===
set JAVAFX_LIB=C:\openjfx-26.0.2_windows-x64_bin-sdk\javafx-sdk-26.0.2\lib
set JAVAFX_JMODS=C:\openjfx-26.0.2_windows-x64_bin-jmods\javafx-jmods-26.0.2
REM ============================

if exist build rmdir /s /q build
mkdir build\classes
mkdir build\input

javac --module-path "%JAVAFX_LIB%" --add-modules javafx.controls -cp lib\gson-2.14.0.jar -d build\classes src\WorkoutManager\*.java
if errorlevel 1 goto error

jar --create --file build\input\workout-manager.jar -C build\classes .
copy lib\gson-2.14.0.jar build\input\ >nul

jpackage --type app-image --name WorkoutManager --input build\input --main-jar workout-manager.jar --main-class WorkoutManager.WorkoutManagerApp --module-path "%JAVAFX_JMODS%" --add-modules javafx.controls --app-content exercises.csv --dest build\dist
if errorlevel 1 goto error

echo.
echo Done. Your app is in build\dist\WorkoutManager\ (run WorkoutManager.exe)
goto end

:error
echo.
echo Build failed, see the messages above.

:end
pause
