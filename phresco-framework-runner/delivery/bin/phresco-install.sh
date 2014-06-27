

#
# Framework Runner
#
# Copyright (C) 1999-2014 Photon Infotech Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#         http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
export JENKINS_HOME=$PHRESCO_HOME/workspace/tools/jenkins
export JMETER_HOME=$PHRESCO_HOME/workspace/tools/apache-jmeter-2.9
export MAVEN_HOME=$PHRESCO_HOME/tools/maven-3.2.1
export SONAR_HOME=$PHRESCO_HOME/workspace/tools/sonar-3.5.1
export CHECKER_HOME=$PHRESCO_HOME/workspace/tools/checker
export CHIEF_HOME=$PHRESCO_HOME/workspace/tools/chief
export M2_HOME=$MAVEN_HOME
export WAXSIM_HOME=$PHRESCO_HOME/workspace/tools/waxsim/usr/local/bin/waxsim
export PATH=$WAXSIM_HOME:$CHIEF_HOME:$CHECKER_HOME:$SONAR_HOME:$MAVEN_HOME/bin:$PHRESCO_HOME/bin:$PATH
export OCUNIT2JUNIT_HOME=$PHRESCO_HOME/workspace/tools/ocunit2junit/ocunit2junit.rb
export PATH=$OCUNIT2JUNIT_HOME:$WAXSIM_HOME:$CHIEF_HOME:$CHECKER_HOME:$SONAR_HOME:$JMETER_HOME:$MAVEN_HOME/bin:$PHRESCO_HOME/bin:$PATH

NAME=phresco
FILE=phresco-pom.xml

START_DAEMON="mvn clean validate"


function pomcheck {
           
                read -p "Do you wish Interactive mode? (y/n) " RESP
                     if [ -f $FILE ]; then
                      if [ "$RESP" = "y" ]; then
                      mvn phresco:$1 -Dinteractive=true -f phresco-pom.xml -e
                      else
                      mvn phresco:$1 -f phresco-pom.xml -e
                      fi
                      
                      else
                      if [ "$RESP" = "y" ]; then
                      
                      mvn phresco:$1 -Dinteractive=true -e -N
                      else
                      mvn phresco:$1 -e -N
                      fi
                      fi
               
                  #  echo $1 
           }  
		   
function noninteractive
            {
                      if [ -f $FILE ]; then
                      
                      mvn phresco:$1 -f phresco-pom.xml -e
                      else
                      mvn phresco:$1 -e
                      fi

            }

case "$1" in

		install-ui)
				cd $PHRESCO_HOME/bin
				$START_DAEMON
				;;

		version)
				cd $PHRESCO_HOME/bin
				var=$(cut -d'=' -f2 ../../downloads/version.properties)
                echo "Phresco $var";
                var1=$(cat ../../downloads/version.properties)
                
				echo "Phresco $var1";
				;;

     	create)
            	echo "Creating New Project";
				cd $PHRESCO_HOME/bin
				echo "Enter ServerProperties file name";
                read property
                echo "Enter ProjectProperties file name";
                read projectproperty
                read -p "Do you wish Interactive mode? (y/n) " RESP
                if [ "$RESP" = "y" ]; then
                mvn phresco:create -Dservice.properties=$property.properties -Dproject.properties=$projectproperty.properties -Dinteractive=true -f pom-create.xml -e
                else
                mvn phresco:create -Dservice.properties=$property.properties -Dproject.properties=$projectproperty.properties -Dinteractive=false -f pom-create.xml -e
                fi
                ;;

      	build)
                if [ -d ".phresco/" ];
                then
                pomcheck package
                else
                echo "Invalid Location";
                fi
                ;;

        run-source)
				 if [ -d ".phresco/" ];
                then
                pomcheck start
                else
                echo "Invalid Location";
                fi
                ;;
				
		run-source-stop)
				 if [ -d ".phresco/" ];
                then
                mvn phresco:stop
                else
                echo "Invalid Location";
                fi
                ;;
		
     	deploy)
             shopt -s nullglob
             files=(do_not_checkin/build/*.zip)
             if [[ "${#files[@]}" -gt 0 ]] ; then
                if [ -d ".phresco/" ];
                   then
                   pomcheck deploy
                else
                   echo "Invalid Location";
                   fi
              else 
                echo "No Builds available to deploy ";
              fi
                ;;

      unit-test)
                if [ -d ".phresco/" ];
                then
                pomcheck unit-test
                else
                echo "Invalid Location";
                fi
                ;;

      start-hub)
               if [ -d ".phresco/" ];
                then
                read -p "Do you want to run functional script? (y/n) " RESP
                if [ "$RESP" = "y" ]; then
                mvn phresco:start-hub -f phresco-pom.xml -e
                else
                echo "Start hub not applicable to project";
                fi
                else
                echo "Invalid Location";
                fi
                ;;

      start-node)
                if [ -d ".phresco/" ];
                then
                read -p "Do you want to run functional script? (y/n) " RESP
                if [ "$RESP" = "y" ]; then
                mvn phresco:start-node -f phresco-pom.xml -e
                else
                echo "Start node not applicable to project";
                fi
                else
                echo "Invalid Location";
                fi
                ;;


      stop-hub)
              if [ -d ".phresco/" ];
                then
                mvn phresco:stop-hub
                else
                echo "Invalid Location";
                fi
                ;;

      stop-node)
              if [ -d ".phresco/" ];
                then
                mvn phresco:stop-node
                else
                echo "Invalid Location";
                fi
                ;;

      	functional-test)
                if [ -d ".phresco/" ];
                then
                noninteractive functional-test
                else
                echo "Invalid Location";
                fi
                ;;	

		performance)
                if [ -d ".phresco/" ];
                then
               noninteractive performance-test 

                else
                echo "Invalid Location";
                fi
                ;;

        load)
                if [ -d ".phresco/" ];
                then
                noninteractive load-test
                else
                echo "Invalid Location";
                fi
                ;;

        validate)
                if [ -d ".phresco/" ];
                then
					if curl --output /dev/null --silent --head --fail "http://localhost:9000/sonar"; then
						echo "Sonar Started"
						pomcheck validate-code 
					else
						echo "Sonar not Started"
					fi
                else
                echo "Invalid Location";
                fi
                ;;

     sonar-setup)
          result=${PWD}
          cd $PHRESCO_HOME/bin
          cd ../workspace/tools/sonar/
          mvn clean install
          cd $result
          ;;

      sonar-start)
          result=${PWD}
          cd $PHRESCO_HOME/bin
          cd ../workspace/tools/sonar/
          mvn t7:run-forked
          cd $result
          ;;

      sonar-stop)
         result=${PWD}
          cd $PHRESCO_HOME/bin
          cd ../workspace/tools/sonar/
          mvn t7:stop-forked
          cd $result
          ;;

        pdf-report)
				if [ -d ".phresco/" ];
                then
                noninteractive pdf-report
                else
                echo "Invalid Location";
                fi
                ;;

		site-report)
				if [ -d ".phresco/" ];
                then
                mvn clean site
                else
                echo "Invalid Location";
                fi
                ;;

 

			help)
				cd $PHRESCO_HOME/bin
				cat help.txt
				;;

            test)
               # echo $PATH
                ;;
	*)
echo "Usage: $NAME { install-ui|version|create|build|deploy|run-source|run-source-stop|start-hub|start-node|stop-hub|stop-node|sonar-setup|sonar-start|sonar-stop|validate|unit-test|functional-test|performance|load|pdf-report|site-report|help|test }" >&2
        exit 1
        ;;
esac
