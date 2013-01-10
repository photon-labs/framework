export PHRESCO_HOME=$PWD/..
export MAVEN_HOME=$PHRESCO_HOME/tools/maven
export PATH=$SONAR_HOME:$MAVEN_HOME/bin:$PHRESCO_HOME/bin:$PATH
mvn $1 $2 $3 $4 $5 $6 $7 $8 $9
