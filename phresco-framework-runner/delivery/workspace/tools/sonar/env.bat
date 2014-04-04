@echo off
set PHRESCO_HOME=%CD%\..\..\..
set SONAR_HOME=%CD%\..\sonar-3.5.1
set MAVEN_HOME=%CD%\..\..\..\tools\maven
set JENKINS_HOME=%PHRESCO_HOME%\workspace\tools\jenkins
set M2_HOME=%MAVEN_HOME%
set TFS_HOME=%PHRESCO_HOME%\workspace\tools\native\native
set PATH=%MAVEN_HOME%\bin;%PHRESCO_HOME%\bin;%PHRESCO_HOME%\workspace\tools\phantomjs-1.5.0-win32-static;%TFS_HOME%;%PATH%;
