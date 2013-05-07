package com.photon.phresco.framework.rest.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class BufferMap {
	
		
		public static Map<String, BufferedReader> BufferReaderMap = new HashMap<String, BufferedReader>();

		public static String readBufferReader(String key) throws IOException {
			String line="";
			
			BufferedReader temp = BufferReaderMap.get(key);
			
			line = temp.readLine();
			
			return line;
		}

		public static void addBufferReader(String key,BufferedReader bufferReader) {
			
			BufferReaderMap.put(key, bufferReader);
		}
		
		public static void removeBufferReader(String key,BufferedReader bufferReader) {
			
			BufferReaderMap.remove(key);
		}
		



}
