package com.photon.phresco.framework.commons;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.photon.phresco.framework.rest.api.ResponseInfo;
import com.photon.phresco.framework.rest.api.RestBase;

public class ErrorHandlerTest extends RestBase {

	@Test
	public void testDoFilter() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterConfig config = new MockFilterConfig();
		
		FilterChain filterChain = new MockFilterChain() {
			@Override
			public void doFilter(ServletRequest req, ServletResponse res) {
				throw new NullPointerException("testDoFilter");
			}
		};
		
		ErrorHandler filter = new ErrorHandler();
		filter.init(config);
		filter.doFilter(request, response, filterChain);
		
		String contentAsString = response.getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ResponseInfo responseInfo = mapper.readValue(new StringReader(contentAsString), ResponseInfo.class);
		String errorCode = responseInfo.getErrorCode();
		Assert.assertEquals("PHR000000", errorCode);
	}

}
