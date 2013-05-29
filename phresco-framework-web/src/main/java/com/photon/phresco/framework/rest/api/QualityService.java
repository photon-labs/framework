package com.photon.phresco.framework.rest.api;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/quality")
public class QualityService extends RestBase implements ServiceConstants, FrameworkConstants {
	@GET
	@Path("/unit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response unit(@QueryParam("appDirName") String appDirName, @QueryParam("userId") String userId) {
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
		try {
			List<String> unitReportOptions = getUnitReportOptions(appDirName);
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null, "Parameter returned successfully", unitReportOptions);
			return Response.ok(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, e, "Unable to get unit test report options", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	private List<String> getUnitReportOptions(String appDirName) throws PhrescoException {
        try {
	        ApplicationInfo appInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
	        FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
	        String unitTestReportOptions = frameworkUtil.getUnitTestReportOptions(appInfo);
	        if (StringUtils.isNotEmpty(unitTestReportOptions)) {
	        	return Arrays.asList(unitTestReportOptions.split(","));
	        }
		} catch (Exception e) {
            throw new PhrescoException(e);
		}
		return null;
	}
}