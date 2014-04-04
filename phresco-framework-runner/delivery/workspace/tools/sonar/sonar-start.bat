@echo off
echo "before env.bat stating sonar"
call env.bat
echo "stating sonar"
mvn clean install
mvn t7:run-forked