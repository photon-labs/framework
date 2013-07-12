package com.photon.phresco.framework.rest.api.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class BufferMap {
	
		
		private static Map<String, BufferedInputStream> BufferReaderMap = new HashMap<String, BufferedInputStream>();

		public static String readBufferReader(String key) throws IOException {
			String line = "";
			
			BufferedInputStream stream = BufferReaderMap.get(key);
			
//			line = stream.readLine();
			int available = stream.available();
        	if (available != 0) {
        		byte[] buf = new byte[available];
                int read = stream.read(buf);
                
                // Empty
                if (read == -1 ||  buf[available-1] == -1) {
                	line = null;
                } else {
                	line = new String(buf, "UTF-8");
                }
        	}
        	
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
