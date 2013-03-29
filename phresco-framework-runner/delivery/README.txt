====
    Framework Runner

    Copyright (C) 1999-2013 Photon Infotech Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
====

Introduction:

---------------

Phresco framework can be run on any platform. It requires Java to be present on the machine. Check if JAVA_HOME ( E.g., Dir:\Java\jdk1.6.0_18) is set and Java bin is included in the PATH (Dir:\Java\jdk1.6.0_18\bin).


Steps to start the Phresco Framework

-------------------------------------

In Windows,

	1.Unzip Phresco-build-<version>.zip containing Service war, Mongo DB dump and Phresco Framework -<version>.zip  
	2.Unzip the Phresco-framework -<version>.zip
	2.Open the command prompt and run the batch script , Dir:\phresco\phresco-framework\bin>start-framework-server.bat
        to start the framework.

In Mac or Linux,

	1.Unzip Phresco-build-<version>.zip containing Service war, Mongo DB dump and Phresco Framework -<version>.zip  
	2.Unzip the Phresco-framework -<version>.zip
	2. Open the terminal and run the shell script , i.e., "sh /Users/admin/phresco/phresco-framework/bin/start-framework        -server.sh" to start the framework

For Android development,

	1. Install Android SDK.
	2. Set ANDROID_HOME ., Dir:\Android\android-sdk
	3. PATH : %ANDROID_HOME%\platform-tools;%ANDROID_HOME%\tools;


For Nodejs development,

	Install the correct version of nodejs from the phresco downloads.

For Iphone development,

	Install Xcode.

For Blackberry:

	Refer to section "5.17 Blackberry prerequisites" in the Phresco Framework 2.0 user guide.
	
For Windows Phone:

	Refer to section "5.18 Prerequisites for Windows Phone " in the Phresco Framework 2.0 user guide.

For Windows Metro:

	Refer to section "5.19 Prerequisites for Windows Metro" in the Phresco Framework 2.0 user guide.
	

Pear Setup:

Pear is used for performing code validation for PHP , Drupal and Wordpress projects. The setup can be downloaded from the below:

In Windows,

	1. Go to the Download tab option.
	2. click the Others dropdown List.
	3. For Wamp,
	         3.1 Download Php_Drupal_code_validation_WAMP_setup file and unzip in to local directory
		 3.2 just follow the instruction in instruction.txt file
	4. For Xampp,
		 4.1 Download php-drupal-validation-Xamp_setup file and unzip in to local directory
		 4.2 Just follow the instruction in instruction.txt file.

In Mac & Linux,


	1. Download php-drupal-code-validation-mac-setup file and unzip in to local directory.
	2. just follow the instruction in instruction.txt file.

Drupal & Wordpress Standards:

	1. Go to phresco downloads and download Drupal & Wordpress standards which is mandatory for performing code validation. This is applicable for both Windows and Mac.

In Windows,

	1. For Wamp,

		Unzip and place the folder in "Dir:\wamp\bin\php\php5.3.5\PEAR\PHP\CodeSniffer\Standards" 
	2. For Xampp,

		Unzip and place the folder in "Dir:\xampp\php\PEAR\PHP\CodeSniffer\Standards" 


In Mac,


	1. Unzip and place the folder in "/usr/local/pear/share/pear/PHP/CodeSniffer/Standards" 







