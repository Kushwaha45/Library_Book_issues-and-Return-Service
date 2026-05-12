@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    https://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM Begin all REM://
@echo off

@REM Set title
title %0

set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

@REM Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.
goto error

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.
goto error

:init
@REM Find project base dir
set MAVEN_PROJECTBASEDIR=%~dp0

@REM Find the maven-wrapper.jar or download
set WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar"

if exist %WRAPPER_JAR% (
    goto runMaven
) else (
    goto downloadWrapper
)

:downloadWrapper
@REM Download wrapper jar
set WRAPPER_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
echo Downloading Maven Wrapper...

powershell -Command "&{"^
    "$webclient = new-object System.Net.WebClient;"^
    "if (-not ([string]::IsNullOrEmpty('%MVNW_USERNAME%') -and [string]::IsNullOrEmpty('%MVNW_PASSWORD%'))) {"^
    "$webclient.Credentials = new-object System.Net.NetworkCredential('%MVNW_USERNAME%', '%MVNW_PASSWORD%');"^
    "}"^
    "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; $webclient.DownloadFile('%WRAPPER_URL%', '%WRAPPER_JAR%')"^
    "}"

if %ERRORLEVEL% neq 0 (
    echo Failed to download Maven wrapper. Falling back to direct download...
    @REM Try with curl
    curl -o %WRAPPER_JAR% %WRAPPER_URL%
)

:runMaven
@REM Provide a way to override
set MAVEN_OPTS=%MAVEN_OPTS%

@REM Find the distribution URL
for /f "usebackq tokens=1,2 delims==" %%a in ("%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.properties") do (
    if "%%a"=="distributionUrl" set DISTRIBUTION_URL=%%b
)

@REM Check if maven is already downloaded
set MAVEN_HOME=%USERPROFILE%\.m2\wrapper\dists
set DIST_NAME=apache-maven-3.9.6

@REM Extract to user home
if exist "%MAVEN_HOME%\%DIST_NAME%\bin\mvn.cmd" (
    set MVN_CMD="%MAVEN_HOME%\%DIST_NAME%\bin\mvn.cmd"
    goto execute
)

@REM Download and extract Maven
echo Downloading Apache Maven 3.9.6...
set MVN_ZIP="%MAVEN_HOME%\maven.zip"
if not exist "%MAVEN_HOME%" mkdir "%MAVEN_HOME%"

powershell -Command "&{"^
    "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12;"^
    "$webclient = new-object System.Net.WebClient;"^
    "$webclient.DownloadFile('%DISTRIBUTION_URL%', '%MVN_ZIP%');"^
    "Expand-Archive -Path '%MVN_ZIP%' -DestinationPath '%MAVEN_HOME%' -Force;"^
    "Remove-Item '%MVN_ZIP%'"^
    "}"

set MVN_CMD="%MAVEN_HOME%\%DIST_NAME%\bin\mvn.cmd"

:execute
%MVN_CMD% %*
if %ERRORLEVEL% neq 0 goto error
goto end

:error
set ERROR_CODE=1

:end
exit /b %ERROR_CODE%
