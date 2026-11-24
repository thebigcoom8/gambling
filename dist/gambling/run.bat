@echo off
chcp 65001 >nul
cd /d "%~dp0"
if exist gambling.jar.new move /y gambling.jar.new gambling.jar >nul
java -jar gambling.jar %*
pause
