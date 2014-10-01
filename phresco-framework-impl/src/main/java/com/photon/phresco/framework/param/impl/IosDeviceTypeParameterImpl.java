/**
 * Phresco Framework Implementation
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.framework.param.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.*;
import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.FileListFilter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Utility;
import com.phresco.pom.util.PomProcessor;

public class IosDeviceTypeParameterImpl implements DynamicParameter {

   

    @Override
    public PossibleValues getValues(Map<String, Object> paramMap) throws IOException, ParserConfigurationException, 
             SAXException, ConfigurationException, PhrescoException {
        PossibleValues possibleValues = new PossibleValues();
        try {
        	
        	Value value = new Value();
            value.setValue("iOS Device");
            value.setKey("device");
            value.setDependency("deviceId");
            possibleValues.getValue().add(value);
            
          
           ArrayList<String> deviceListForXcode_6 = new ArrayList<String>();
           deviceListForXcode_6.add("iPad 2");
           deviceListForXcode_6.add("iPad Air");
           deviceListForXcode_6.add("iPad Retina");
           deviceListForXcode_6.add("iPhone 4s");
           deviceListForXcode_6.add("iPhone 5");
           deviceListForXcode_6.add("iPhone 5s");
           deviceListForXcode_6.add("iPhone 6");
           deviceListForXcode_6.add("iPhone 6 Plus");
           deviceListForXcode_6.add("Resizable iPad");
           deviceListForXcode_6.add("Resizable iPhone");
           
           
           ArrayList<String> deviceListForXcode_5 = new ArrayList<String>();
           deviceListForXcode_5.add("iPhone Simulator");
           deviceListForXcode_5.add("iPad Simulator");
           
           ArrayList<String> keyListForXcode_5 = new ArrayList<String>();
           keyListForXcode_5.add("iPhone Retina (4-inch)");
           keyListForXcode_5.add("iPad Retina");
           
           
           Boolean isXcode6 = isXcode6(cureentXcodeVersion() , "6.0");
           
          if(isXcode6){
           for (int i=0;i<deviceListForXcode_6.size();i++){
        	   Value valueSet = new Value();
        	   valueSet.setValue(deviceListForXcode_6.get(i));
        	   valueSet.setKey(deviceListForXcode_6.get(i));
               possibleValues.getValue().add(valueSet);
           }
          }else {
        	  for (int i=0;i<deviceListForXcode_5.size();i++){
           	   Value valueSet = new Value();
           	   valueSet.setValue(deviceListForXcode_5.get(i));
           	   valueSet.setKey(keyListForXcode_5.get(i));
                  possibleValues.getValue().add(valueSet);
              }
         }
        } catch (Exception e) {
        	e.printStackTrace();
            throw new PhrescoException(e);
        }

        return possibleValues;
    }
    
    private boolean isXcode6(String str1, String str2) {
		String[] vals1 = str1.split("\\.");
		String[] vals2 = str2.split("\\.");
		int i=0;
		while(i<vals1.length&&i<vals2.length&&vals1[i].equals(vals2[i])) {
		  i++;
		}

		if (i<vals1.length&&i<vals2.length) {
		    int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
		    return diff<0?false:diff==0?true:true;
		}

		return vals1.length<vals2.length?false:vals1.length==vals2.length?true:true;
	}
    
    private String cureentXcodeVersion() throws IOException  
	{
		String version = "";
		ProcessBuilder pb = new ProcessBuilder( "/bin/sh", "-c" ,"xcodebuild -version");
		pb.redirectErrorStream(true);
		pb.directory(new File("/"));
		Process process = pb.start();
		InputStream is = process.getInputStream();
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String line;
	    while ((line = br.readLine()) != null) {
	      String[] splited = line.split("\\s");
	    	  version = splited[1];
	      break;
	    }
		return version;
	}

}
