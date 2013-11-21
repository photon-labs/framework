package com.photon.phresco.framework.rest.api.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class BufferMap {
	
		
		private static Map<String, BufferedInputStream> BufferReaderMap = new HashMap<String, BufferedInputStream>();
		
		public static boolean end;

		public static String readBufferReader(String key) throws IOException {
			String line = "";
			
			BufferedInputStream stream = BufferReaderMap.get(key);
			
			int available = stream.available();
        	if (available != 0) {
        		byte[] buf = new byte[available];
                int read = stream.read(buf);
                
                // Empty
                if (read == -1 ||  buf[available-1] == -1) {
                	line = "";
                } else {
                	line = new String(buf, "UTF-8");
                }
        	}
        	
			return line;
		}
		
		public static String readLiquibaseBufferReader(String key) throws IOException {
			
			String line = "";
			if(end) {
				return line;
			}
			int carriageReturn = 13;  
			int lineFeed = 10;  
			int lastRead=-1; // The last char we've read  
			int currentChar = -1;   // currently read char 
			BufferedInputStream stream = BufferReaderMap.get(key);
			StringBuffer sb = new StringBuffer("");  
			if (lastRead != -1) {
				sb.append((char) lastRead);
			}
			currentChar = stream.read();
			while(currentChar != carriageReturn && currentChar != lineFeed) {
				sb.append((char) currentChar);
				String temp = new String(sb);
				if(temp.contains(" Successful") || temp.contains("Update completed for this version") || temp.contains("Communications link failure")) {
					end = true;
					break;
				}
				currentChar = stream.read();
				
			}
//			 Read the next byte and check if it's a LF  
			lastRead = stream.read();
			if (lastRead == lineFeed) {
				lastRead = -1;
			}
			line = new String(sb);
			return line;
		}

		public static void addBufferReader(String key, BufferedInputStream bufferReader) {
			
			BufferReaderMap.put(key, bufferReader);
		}
		
		public static void removeBufferReader(String key) {
			
			BufferReaderMap.remove(key);
		}
		
		public static BufferedInputStream getBufferReader(String key) {
			
			return BufferReaderMap.get(key);
		}
		



}
