package com.photon.phresco.framework.commons;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.rest.api.ResponseInfo;
import com.photon.phresco.framework.rest.api.RestBase;

public class ErrorHandler extends RestBase implements Filter {

	private static final String UTF_8 = "UTF-8";
	private static final String APPLICATION_JSON = "application/json";
	private static final Logger S_LOGGER= Logger.getLogger(ErrorHandler.class);
	private static boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			if (isDebugEnabled) {
				S_LOGGER.debug("Entering into the method of ErrorHandler.doFilter()");
			}
			PhrescoException phrescoException = new PhrescoException(e);
			ResponseInfo<Object> responseData = new ResponseInfo<Object>();
			ResponseInfo<Object> finalOuptut = responseDataEvaluation(responseData, phrescoException, "Internal Server Error", null);
			
			ObjectMapper mapper = new ObjectMapper();
			String objectToReturn = mapper.writeValueAsString(finalOuptut);
			
            HttpServletResponse res = (HttpServletResponse)response;
			res.setStatus(500);
			
			response.setContentType(APPLICATION_JSON);
			response.setCharacterEncoding(UTF_8);
			PrintWriter out = response.getWriter();
			out.print(objectToReturn);
			out.flush();
		}
	}

	@Override
	public void destroy() {
	}
}