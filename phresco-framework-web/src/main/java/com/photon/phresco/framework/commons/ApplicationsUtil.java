/**
 * Framework Web Archive
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
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
package com.photon.phresco.framework.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;

public class ApplicationsUtil implements FrameworkConstants {

	private static final Logger S_LOGGER = Logger.getLogger(ApplicationsUtil.class);
    private static Boolean s_debugEnabled  = S_LOGGER.isDebugEnabled();
	
    private static Collection<String> dependentVersions = new ArrayList<String>(1);
    public static Collection<String> getDependentVersions() {
    	return dependentVersions;
    }
    
    public static Map<String, String> stringToMap(String str) throws PhrescoException {
    	if (s_debugEnabled) {
    		S_LOGGER.debug("Entering into ApplicationsUtil.stringToMap()");
    	}
    	
    	Map<String, String> map = new HashMap<String, String>(5);
    	try {
			str = str.substring(1).substring(0, str.length() - 2).trim();
			
			String[] split = str.split(", ");
			for (String item : split) {
				String[] keyValue = item.split("=");
				map.put(keyValue[0], keyValue[1]);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		
		return map;
    }
    
    public static String getCsvFromStringArray(String[] strArray) {
    	if (s_debugEnabled) {
    		S_LOGGER.debug("Entering into ApplicationsUtil.getCsvFromStringArray()");
    	}
    	if (strArray == null) {
    		return null;
    	}
    	String csv = "";
    	for (String id : strArray) {
			csv = csv + id + ",";
		}
    	csv = csv.substring(0, csv.length() - 1); 
    	return csv;
    }
    
	public static Document getDocument(File file) throws PhrescoException {
		if (s_debugEnabled) {
    		S_LOGGER.debug("Entering into ApplicationsUtil.getDocument()");
    	}
		InputStream fis = null;
        DocumentBuilder builder = null;
        try {
            fis = new FileInputStream(file);
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(false);
            builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(fis);
            return doc;
        } catch (FileNotFoundException e) {
        	throw new PhrescoException(e);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		} catch (SAXException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
        	if (fis != null) {
        		try {
					fis.close();
				} catch (IOException e) {
					throw new PhrescoException(e);
				}
            }
        }
	}
	
	public static List<Integer> getArrayListFromStrArr(String[] strArr) {
		if (s_debugEnabled) {
    		S_LOGGER.debug("Entering into ApplicationsUtil.getArrayListFromStrArr()");
    	}
		List<Integer> list = new ArrayList<Integer>();
		if (strArr != null && strArr.length > 0) {
			for (int i = 0; i < strArr.length; i++) {
				list.add(Integer.parseInt(strArr[i]));
			}
		}
		return list;
	}
}