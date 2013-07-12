package com.photon.phresco.framework.rest.api.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BufferMapTest {
	private static final String T_E_S_T_S = "T E S T S";
	private static final String BUILD_SUCCESS = "BUILD SUCCESS";
	private static final String BUILDING_JWS_1_0 = "Building JWS 1.0";
	String uniqueKey = "";
	BufferedInputStream reader = null;
	
	@Before
	public void setUp() throws Exception {
		uniqueKey = "20bd6122-2a3d-4f56-8604-0c92250a0023";
		InputStream resourceAsStream = BufferMapTest.class.getResourceAsStream("/logText.log");
		reader = new BufferedInputStream(new MyWrapper(resourceAsStream));
	}

	@After
	public void tearDown() throws Exception {
		if (reader != null) {
			reader = null;
		}
		if (uniqueKey != null) {
			uniqueKey = null;
		}
	}

	@Test
	public void testAddBufferReader() throws IOException {
		BufferMap.addBufferReader(uniqueKey, reader);
		BufferedInputStream bufferReader = BufferMap.getBufferReader(uniqueKey);
		Assert.assertNotNull(bufferReader);
	}

	@Test
	public void testGetBufferReader() throws IOException {
		BufferedInputStream bufferReader = BufferMap.getBufferReader(uniqueKey);
		String logs = readStream(bufferReader);
		Assert.assertTrue(StringUtils.countMatches(logs, BUILDING_JWS_1_0) == 2 && StringUtils.countMatches(logs, BUILD_SUCCESS) == 2 && StringUtils.countMatches(logs, T_E_S_T_S) == 1);
	}
	
	@Test
	public void testRemoveBufferReader() {
		BufferMap.removeBufferReader(uniqueKey);
		BufferedInputStream bufferReader = BufferMap.getBufferReader(uniqueKey);
		Assert.assertNull(bufferReader);
	}
	
	private String readStream(BufferedInputStream bufferReader) throws IOException {
		StringBuffer sb = new StringBuffer();
		int available = bufferReader.available();
		while (available != 0) {
			byte[] buf = new byte[available];
            int read = bufferReader.read(buf);
            if (read == -1 ||  buf[available-1] == -1) {
            	break;
            } else {
            	String logText = new String(buf);
            	sb.append(logText);
            }
            available = bufferReader.available();
		}
		return sb.toString();
	}
	
	static class MyWrapper extends PushbackInputStream {
	    MyWrapper(InputStream in) {
	        super(in);
	    }
	
	    @Override
	    public int available() throws IOException {
	        int b = super.read();
	        super.unread(b);
	        return super.available();
	    }
	}
}
