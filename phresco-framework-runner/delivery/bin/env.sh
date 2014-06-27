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

export PHRESCO_HOME=$PWD/..
export MAVEN_HOME=$PHRESCO_HOME/tools/maven-3.2.1
export M2_HOME=$MAVEN_HOME
export CHECKER_HOME=$PHRESCO_HOME/workspace/tools/checker
export CHIEF_HOME=$PHRESCO_HOME/workspace/tools/chief
export JMETER_HOME=$PHRESCO_HOME/workspace/tools/apache-jmeter-2.9
export WAXSIM_HOME=$PHRESCO_HOME/workspace/tools/waxsim/usr/local/bin/waxsim
export IOS_SIM_HOME=$PHRESCO_HOME/workspace/tools/ios-sim/build/Release
export TFS_HOME=$PHRESCO_HOME/workspace/tools/native/native
export GYP_GENERATORS=ninja
export GYP_DEFINES='clang=1 disable_nacl=1'
export PATH=$IOS_SIM_HOME:$WAXSIM_HOME:$CHIEF_HOME:$CHECKER_HOME:$MAVEN_HOME/bin:$PHRESCO_HOME/bin:$PHRESCO_HOME/workspace/tools/phantomjs-1.5.0/bin:$TFS_HOME:$PHRESCO_HOME/workspace/tools/depot_tools:$PATH

