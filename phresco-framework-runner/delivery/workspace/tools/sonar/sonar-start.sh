
export PHRESCO_HOME=$PWD/../../..
export SONAR_HOME=$PWD/../sonar-3.5.1
export MAVEN_HOME=$PWD/../../../tools/maven
export JENKINS_HOME=$PHRESCO_HOME/workspace/tools/jenkins
export M2_HOME=$MAVEN_HOME
export TFS_HOME=$PHRESCO_HOME/workspace/tools/native/native
export PATH=$MAVEN_HOME/bin:$PHRESCO_HOME/bin:$PHRESCO_HOME/workspace/tools/phantomjs-1.5.0-mac/bin:$TFS_HOME:$PATH
echo "starting sonar"

mvn t7:run-forked