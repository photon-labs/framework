export PHRESCO_HOME=$PWD/..
export JENKINS_HOME=$PHRESCO_HOME/workspace/tools/jenkins
export JMETER_HOME=$PHRESCO_HOME/workspace/tools/apache-jmeter-2.9
export MAVEN_HOME=$PHRESCO_HOME/tools/maven
export SONAR_HOME=$PHRESCO_HOME/workspace/tools/sonar-3.1.1
export CHECKER_HOME=$PHRESCO_HOME/workspace/tools/checker
export CHIEF_HOME=$PHRESCO_HOME/workspace/tools/chief
export M2_HOME=$MAVEN_HOME
export WAXSIM_HOME=$PHRESCO_HOME/workspace/tools/waxsim/usr/local/bin/waxsim
export PATH=$WAXSIM_HOME:$CHIEF_HOME:$CHECKER_HOME:$SONAR_HOME:$MAVEN_HOME/bin:$PHRESCO_HOME/bin:$PATH
export OCUNIT2JUNIT_HOME=$PHRESCO_HOME/workspace/tools/ocunit2junit/ocunit2junit.rb
export PATH=$OCUNIT2JUNIT_HOME:$WAXSIM_HOME:$CHIEF_HOME:$CHECKER_HOME:$SONAR_HOME:$JMETER_HOME:$MAVEN_HOME/bin:$PHRESCO_HOME/bin:$PATH
mvn clean validate