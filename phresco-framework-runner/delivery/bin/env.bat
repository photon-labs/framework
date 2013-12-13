@REM
@REM Framework Runner
@REM
@REM Copyright (C) 1999-2013 Photon Infotech Inc.
@REM
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM         http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM

@echo off
if "%PHRESCO_HOME%" == "%cd%\.." goto :eof
  
set PHRESCO_HOME=%CD%\..
set MAVEN_HOME=%CD%\..\tools\maven
set JENKINS_HOME=%PHRESCO_HOME%\workspace\tools\jenkins
set M2_HOME=%MAVEN_HOME%
set TFS_HOME=%PHRESCO_HOME%\workspace\tools\native\native
set PATH=%MAVEN_HOME%\bin;%PHRESCO_HOME%\bin;%PHRESCO_HOME%\workspace\tools\phantomjs-1.5.0-win32-static;%TFS_HOME%;%PATH%;
