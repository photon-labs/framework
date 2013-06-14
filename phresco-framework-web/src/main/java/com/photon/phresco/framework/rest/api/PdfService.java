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
package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.commons.QualityUtil;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Model.Modules;
import com.phresco.pom.util.PomProcessor;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class PdfService.
 */
@Path("/pdf")
public class PdfService extends RestBase implements FrameworkConstants, Constants, ServiceConstants {

	/**
	 * Download report.
	 *
	 * @param fromPage the from page
	 * @param reportFileName the report file name
	 * @param appDirName the app dir name
	 * @return the response
	 */
	@GET
	@Path("/downloadReport")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadReport(@QueryParam(REST_QUERY_FROM_PAGE) String fromPage,
			@QueryParam(REST_QUERY_REPORT_FILE_NAME) String reportFileName, @QueryParam(REST_QUERY_APPDIR_NAME) String appDirName) {
		FileInputStream fileInputStream = null;
		ResponseInfo responseData = new ResponseInfo();
		try {
			String pdfLOC = "";
			String applicationHome = FrameworkServiceUtil.getApplicationHome(appDirName);
			String archivePath = applicationHome + File.separator + DO_NOT_CHECKIN_DIR + File.separator + ARCHIVES
					+ File.separator;
			if ((FrameworkConstants.ALL).equals(fromPage)) {
				pdfLOC = archivePath + CUMULATIVE + File.separator + reportFileName;
			} else {
				pdfLOC = archivePath + fromPage + File.separator + reportFileName;
			}
			File pdfFile = new File(pdfLOC);
			if (pdfFile.isFile()) {
				fileInputStream = new FileInputStream(pdfFile);
				return Response.status(Status.OK).entity(fileInputStream).header("Access-Control-Allow-Origin", "*")
						.build();
			}
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "download report Failed", null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (FileNotFoundException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, "download report Failed", null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
		ResponseInfo finalOutput = responseDataEvaluation(responseData, null, "Nothing to download", null);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Delete report.
	 *
	 * @param fromPage the from page
	 * @param reportFileName the report file name
	 * @param appDirName the app dir name
	 * @return the response
	 */
	@DELETE
	@Path("/deleteReport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteReport(@QueryParam(REST_QUERY_FROM_PAGE) String fromPage,
			@QueryParam(REST_QUERY_REPORT_FILE_NAME) String reportFileName, @QueryParam(REST_QUERY_APPDIR_NAME) String appDirName) {
		ResponseInfo responseData = new ResponseInfo();
		try {
			String pdfLOC = "";
			String applicationHome = FrameworkServiceUtil.getApplicationHome(appDirName);
			String archivePath = applicationHome + File.separator + DO_NOT_CHECKIN_DIR + File.separator + ARCHIVES
					+ File.separator;
			if ((FrameworkConstants.ALL).equals(fromPage)) {
				pdfLOC = archivePath + CUMULATIVE + File.separator + reportFileName;
			} else {
				pdfLOC = archivePath + fromPage + File.separator + reportFileName;
			}
			File pdfFile = new File(pdfLOC);
			if (pdfFile.isFile()) {
				boolean reportDeleted = pdfFile.delete();
				if (reportDeleted) {
					ResponseInfo finalOutput = responseDataEvaluation(responseData, null, SUCCESS_REPORT_DELETE_STATUS,
							null);
					return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
							.build();
				} else {
					ResponseInfo finalOutput = responseDataEvaluation(responseData, null, ERROR_REPORT_DELETE_STATUS,
							null);
					return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin",
							"*").build();
				}
			}
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, ERROR_REPORT_DELETE_STATUS, null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
		ResponseInfo finalOutput = responseDataEvaluation(responseData, null, SUCCESS_REPORT_DELETE_STATUS, null);
		return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Show generate pdf popup.
	 *
	 * @param appDirName the app dir name
	 * @param fromPage the from page
	 * @return the response
	 */
	@GET
	@Path("/showPopUp")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showGeneratePdfPopup(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,
			@QueryParam(REST_QUERY_FROM_PAGE) String fromPage) {
		ResponseInfo<JSONArray> responseData = new ResponseInfo<JSONArray>();
		try {
			boolean isReportAvailable = false;
			ApplicationInfo appInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();

			// is sonar report available
			isReportAvailable = isSonarReportAvailable(frameworkUtil, appInfo);

			// is test report available
			if (!isReportAvailable) {
				isReportAvailable = isTestReportAvailable(frameworkUtil, appInfo);
			}
			JSONArray existingPDFs = getExistingPDFs(fromPage, appInfo);
			if (existingPDFs != null) {
				return Response.status(Status.OK).entity(existingPDFs).header("Access-Control-Allow-Origin", "*")
						.build();
			}
			ResponseInfo<JSONArray> finalOutput = responseDataEvaluation(responseData, null, "No existing Reports",
					null);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<JSONArray> finalOutput = responseDataEvaluation(responseData, e, "Reports is not available",
					null);
			return Response.status(Status.NOT_FOUND).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Checks if is sonar report available.
	 *
	 * @param frameworkUtil the framework util
	 * @param appInfo the app info
	 * @return true, if is sonar report available
	 * @throws PhrescoException the phresco exception
	 */
	private boolean isSonarReportAvailable(FrameworkUtil frameworkUtil, ApplicationInfo appInfo)
			throws PhrescoException {
		boolean isSonarReportAvailable = false;
		try {
			String isIphone = frameworkUtil.isIphoneTagExists(appInfo);
			if (StringUtils.isEmpty(isIphone)) {
				FrameworkConfiguration frameworkConfig = PhrescoFrameworkFactory.getFrameworkConfig();
				String serverUrl = frameworkUtil.getSonarURL();
				String sonarReportPath = frameworkConfig.getSonarReportPath().replace(FrameworkConstants.FORWARD_SLASH + SONAR, "");
				serverUrl = serverUrl + sonarReportPath;
				PomProcessor processor = frameworkUtil.getPomProcessor(appInfo.getAppDirName());
				Modules pomModules = processor.getPomModule();
				List<String> modules = null;
				if (pomModules != null) {
					modules = pomModules.getModule();
				}

				// check multimodule or not
				List<String> sonarProfiles = frameworkUtil.getSonarProfiles(appInfo);
				if (CollectionUtils.isEmpty(sonarProfiles)) {
					sonarProfiles.add(SONAR_SOURCE);
				}
				sonarProfiles.add(FUNCTIONAL);
				boolean isSonarUrlAvailable = false;
				if (CollectionUtils.isNotEmpty(modules)) {
					for (String module : modules) {
						for (String sonarProfile : sonarProfiles) {
							isSonarUrlAvailable = checkSonarModuleUrl(sonarProfile, serverUrl, module, frameworkUtil,
									appInfo);

							if (isSonarUrlAvailable) {
								isSonarReportAvailable = true;
								break;
							}
						}
					}
				} else {
					for (String sonarProfile : sonarProfiles) {
						isSonarUrlAvailable = checkSonarUrl(sonarProfile, serverUrl, frameworkUtil, appInfo);
						if (isSonarUrlAvailable) {
							isSonarReportAvailable = true;
							break;
						}
					}
				}
			} else {
				StringBuilder sb = new StringBuilder(Utility.getProjectHome()).append(appInfo.getAppDirName()).append(
						File.separatorChar).append(DO_NOT_CHECKIN_DIR).append(File.separatorChar).append(
						STATIC_ANALYSIS_REPORT);
				File indexPath = new File(sb.toString());
				if (indexPath.exists() && indexPath.isDirectory()) {
					File[] listFiles = indexPath.listFiles();
					for (int i = 0; i < listFiles.length; i++) {
						File file = listFiles[i];
						File htmlFileCheck = new File(file, INDEX_HTML);
						if (htmlFileCheck.exists()) {
							isSonarReportAvailable = true;
						}
					}
				}
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return isSonarReportAvailable;
	}

	/**
	 * Check sonar module url.
	 *
	 * @param sonarProfile the sonar profile
	 * @param serverUrl the server url
	 * @param module the module
	 * @param frameworkUtil the framework util
	 * @param appInfo the app info
	 * @return true, if successful
	 * @throws PhrescoException the phresco exception
	 */
	private boolean checkSonarModuleUrl(String sonarProfile, String serverUrl, String module,
			FrameworkUtil frameworkUtil, ApplicationInfo appInfo) throws PhrescoException {
		boolean isSonarReportAvailable = false;
		try {
			if (StringUtils.isNotEmpty(module)) {
				StringBuilder builder = new StringBuilder(Utility.getProjectHome());
				builder.append(appInfo.getAppDirName());
				builder.append(File.separatorChar);

				if (!FUNCTIONALTEST.equals(sonarProfile)) {
					builder.append(module);
				}
				if (StringUtils.isNotEmpty(sonarProfile) && FUNCTIONALTEST.equals(sonarProfile)) {
					builder.append(frameworkUtil.getFunctionalTestDir(appInfo));
				}

				builder.append(File.separatorChar);
				File pomXml = new File(builder.toString() + File.separatorChar + Utility.getPomFileName(appInfo));
				if (pomXml.exists()) {
					builder.append(Utility.getPomFileName(appInfo));
				} else {
					builder.append(Constants.POM_NAME);
				}
				File pomPath = new File(builder.toString());
				StringBuilder sbuild = new StringBuilder();
				if (pomPath.exists()) {
					PomProcessor pomProcessor = new PomProcessor(pomPath);
					String groupId = pomProcessor.getModel().getGroupId();
					String artifactId = pomProcessor.getModel().getArtifactId();

					sbuild.append(groupId);
					sbuild.append(FrameworkConstants.COLON);
					sbuild.append(artifactId);
					if (!REQ_SRC.equals(sonarProfile)) {
						sbuild.append(FrameworkConstants.COLON);
						sbuild.append(sonarProfile);
					}

					String artifact = sbuild.toString();
					String url = serverUrl + artifact;
					if (isSonarAlive(url)) {
						isSonarReportAvailable = true;
					}
				}
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return isSonarReportAvailable;
	}

	/**
	 * Check sonar url.
	 *
	 * @param sonarProfile the sonar profile
	 * @param serverUrl the server url
	 * @param frameworkUtil the framework util
	 * @param appInfo the app info
	 * @return true, if successful
	 * @throws PhrescoException the phresco exception
	 */
	private boolean checkSonarUrl(String sonarProfile, String serverUrl, FrameworkUtil frameworkUtil,
			ApplicationInfo appInfo) throws PhrescoException {
		boolean isSonarReportAvailable = false;
		try {
			if (StringUtils.isNotBlank(sonarProfile)) {
				// get sonar report
				StringBuilder builder = new StringBuilder(Utility.getProjectHome());
				builder.append(appInfo.getAppDirName());
				builder.append(File.separatorChar);

				if (StringUtils.isNotEmpty(sonarProfile) && FUNCTIONALTEST.equals(sonarProfile)) {
					builder.append(frameworkUtil.getFunctionalTestDir(appInfo));
				}

				builder.append(File.separatorChar);
				builder.append(Utility.getPomFileName(appInfo));
				File pomPath = new File(builder.toString());
				StringBuilder sbuild = new StringBuilder();
				if (pomPath.exists()) {
					PomProcessor pomProcessor = new PomProcessor(pomPath);
					String groupId = pomProcessor.getModel().getGroupId();
					String artifactId = pomProcessor.getModel().getArtifactId();

					sbuild.append(groupId);
					sbuild.append(FrameworkConstants.COLON);
					sbuild.append(artifactId);

					if (!SOURCE_DIR.equals(sonarProfile)) {
						sbuild.append(FrameworkConstants.COLON);
						sbuild.append(sonarProfile);
					}
				}

				String artifact = sbuild.toString();
				String url = serverUrl + artifact;
				if (isSonarAlive(url)) {
					isSonarReportAvailable = true;
				}
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return isSonarReportAvailable;
	}

	/**
	 * Checks if is sonar alive.
	 *
	 * @param url the url
	 * @return true, if is sonar alive
	 */
	private boolean isSonarAlive(String url) {
		boolean xmlResultsAvailable = false;
		try {
			URL sonarURL = new URL(url);
			HttpURLConnection connection = null;
			connection = (HttpURLConnection) sonarURL.openConnection();
			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				xmlResultsAvailable = false;
			} else {
				xmlResultsAvailable = true;
			}
		} catch (Exception e) {
			xmlResultsAvailable = false;
		}
		return xmlResultsAvailable;
	}

	/**
	 * Checks if is test report available.
	 *
	 * @param frameworkUtil the framework util
	 * @param appInfo the app info
	 * @return true, if is test report available
	 * @throws PhrescoException the phresco exception
	 */
	private boolean isTestReportAvailable(FrameworkUtil frameworkUtil, ApplicationInfo appInfo) throws PhrescoException {
		boolean xmlResultsAvailable = false;
		File file = null;
		StringBuilder sb = new StringBuilder(Utility.getProjectHome());
		sb.append(appInfo.getAppDirName());
		try {
			String isIphone = frameworkUtil.isIphoneTagExists(appInfo);
			// unit xml check
			if (!xmlResultsAvailable) {
				List<String> moduleNames = new ArrayList<String>();
				PomProcessor processor = frameworkUtil.getPomProcessor(appInfo.getAppDirName());
				Modules pomModules = processor.getPomModule();
				List<String> modules = null;
				// check multimodule or not
				if (pomModules != null) {
					modules = FrameworkServiceUtil.getProjectModules(appInfo.getAppDirName());
					for (String module : modules) {
						if (StringUtils.isNotEmpty(module)) {
							moduleNames.add(module);
						}
					}
					for (String moduleName : moduleNames) {
						String moduleXmlPath = sb.toString() + File.separator + moduleName
								+ frameworkUtil.getUnitTestReportDir(appInfo);
						file = new File(moduleXmlPath);
						xmlResultsAvailable = xmlFileSearch(file, xmlResultsAvailable);
					}
				} else {
					if (StringUtils.isNotEmpty(isIphone)) {
						String unitIphoneTechReportDir = frameworkUtil.getUnitTestReportDir(appInfo);
						file = new File(sb.toString() + unitIphoneTechReportDir);
					} else {
						String unitTechReports = frameworkUtil.getUnitTestReportOptions(appInfo);
						if (StringUtils.isEmpty(unitTechReports)) {
							file = new File(sb.toString() + frameworkUtil.getUnitTestReportDir(appInfo));
						} else {
							List<String> unitTestTechs = Arrays.asList(unitTechReports.split(","));
							for (String unitTestTech : unitTestTechs) {
								unitTechReports = frameworkUtil.getUnitTestReportDir(appInfo, unitTestTech);
								if (StringUtils.isNotEmpty(unitTechReports)) {
									file = new File(sb.toString() + unitTechReports);
									xmlResultsAvailable = xmlFileSearch(file, xmlResultsAvailable);
								}
							}
						}
					}
					xmlResultsAvailable = xmlFileSearch(file, xmlResultsAvailable);
				}
			}

			// functional xml check
			if (!xmlResultsAvailable) {
				file = new File(sb.toString() + frameworkUtil.getFunctionalTestReportDir(appInfo));
				xmlResultsAvailable = xmlFileSearch(file, xmlResultsAvailable);
			}

			// component xml check
			if (!xmlResultsAvailable) {
				String componentDir = frameworkUtil.getComponentTestReportDir(appInfo);
				if (StringUtils.isNotEmpty(componentDir)) {
					file = new File(sb.toString() + componentDir);
					xmlResultsAvailable = xmlFileSearch(file, xmlResultsAvailable);
				}
			}

			// performance xml check
			if (StringUtils.isEmpty(isIphone)) {
				if (!xmlResultsAvailable) {
					boolean isResultFileAvailable = performanceTestResultAvail(appInfo);
					if (isResultFileAvailable) {
						xmlResultsAvailable = true;
					}
				}
			}

			// load xml check
			if (StringUtils.isEmpty(isIphone)) {
				if (!xmlResultsAvailable) {
					boolean isResultFileAvailable = loadTestResultAvail(appInfo);
					if (isResultFileAvailable) {
						xmlResultsAvailable = true;
					}
				}
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return xmlResultsAvailable;
	}

	/**
	 * Xml file search.
	 *
	 * @param file the file
	 * @param xmlResultsAvailable the xml results available
	 * @return true, if successful
	 */
	private boolean xmlFileSearch(File file, boolean xmlResultsAvailable) {
		File[] children = file.listFiles(new XmlNameFileFilter(FILE_EXTENSION_XML));
		if (children != null && children.length > 0) {
			xmlResultsAvailable = true;
		}
		return xmlResultsAvailable;
	}

	/**
	 * Gets the existing pdfs.
	 *
	 * @param fromPage the from page
	 * @param appInfo the app info
	 * @return the existing pd fs
	 * @throws PhrescoException the phresco exception
	 */
	private JSONArray getExistingPDFs(String fromPage, ApplicationInfo appInfo) throws PhrescoException {
		JSONArray jsonarray = null;
		// popup showing list of pdf's already created
		String pdfDirLoc = "";
		try {
			if (StringUtils.isEmpty(fromPage) || FROMPAGE_ALL.equals(fromPage)) {
				pdfDirLoc = Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + DO_NOT_CHECKIN_DIR
						+ File.separator + ARCHIVES + File.separator + CUMULATIVE;
			} else {
				pdfDirLoc = Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + DO_NOT_CHECKIN_DIR
						+ File.separator + ARCHIVES + File.separator + fromPage;
			}
			File pdfFileDir = new File(pdfDirLoc);
			if (pdfFileDir.isDirectory()) {
				File[] children = pdfFileDir.listFiles(new FileNameFileFilter(FrameworkConstants.DOT + PDF));
				QualityUtil util = new QualityUtil();
				if (children != null) {
					util.sortResultFile(children);
				}
				jsonarray = new JSONArray();
				for (File child : children) {
					JSONObject json = new JSONObject();
					// three value
					DateFormat yymmdd = new SimpleDateFormat("MMM dd yyyy HH.mm");
					if (child.toString().contains("detail")) {
						json.put("time", yymmdd.format(child.lastModified()));
						json.put("type", "detail");
						json.put("fileName", child.getName());
					} else if (child.toString().contains("crisp")) {
						json.put("time", yymmdd.format(child.lastModified()));
						json.put("type", "crisp");
						json.put("fileName", child.getName());
					}
					jsonarray.put(json);
				}
			}
			return jsonarray;
		} catch (JSONException e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Performance test result avail.
	 *
	 * @param appInfo the app info
	 * @return true, if successful
	 * @throws PhrescoException the phresco exception
	 */
	public boolean performanceTestResultAvail(ApplicationInfo appInfo) throws PhrescoException {
		boolean isResultFileAvailable = false;
		try {
			String baseDir = Utility.getProjectHome() + appInfo.getAppDirName();
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			List<String> testResultsTypes = new ArrayList<String>();
			testResultsTypes.add("server");
			testResultsTypes.add("database");
			testResultsTypes.add("webservice");
			for (String testResultsType : testResultsTypes) {
				StringBuilder sb = new StringBuilder(baseDir.toString());
				String performanceReportDir = frameworkUtil.getPerformanceTestReportDir(appInfo);
				if (StringUtils.isNotEmpty(performanceReportDir) && StringUtils.isNotEmpty(testResultsType)) {
					Pattern p = Pattern.compile("dir_type");
					Matcher matcher = p.matcher(performanceReportDir);
					performanceReportDir = matcher.replaceAll(testResultsType);
					sb.append(performanceReportDir);
				}
				File file = new File(sb.toString());
				File[] children = file.listFiles(new XmlNameFileFilter(FILE_EXTENSION_XML));
				if (!ArrayUtils.isEmpty(children)) {
					isResultFileAvailable = true;
					break;
				}
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}

		return isResultFileAvailable;
	}

	/**
	 * Load test result avail.
	 *
	 * @param appInfo the app info
	 * @return true, if successful
	 * @throws PhrescoException the phresco exception
	 */
	public boolean loadTestResultAvail(ApplicationInfo appInfo) throws PhrescoException {
		boolean isResultFileAvailable = false;
		try {
			String baseDir = Utility.getProjectHome() + appInfo.getAppDirName();
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			List<String> testResultsTypes = new ArrayList<String>();
			testResultsTypes.add("server");
			testResultsTypes.add("webservice");
			for (String testResultsType : testResultsTypes) {
				StringBuilder sb = new StringBuilder(baseDir.toString());
				String loadReportDir = frameworkUtil.getLoadTestReportDir(appInfo);
				if (StringUtils.isNotEmpty(loadReportDir) && StringUtils.isNotEmpty(testResultsType)) {
					Pattern p = Pattern.compile("dir_type");
					Matcher matcher = p.matcher(loadReportDir);
					loadReportDir = matcher.replaceAll(testResultsType);
					sb.append(loadReportDir);
				}
				File file = new File(sb.toString());
				File[] children = file.listFiles(new XmlNameFileFilter(FILE_EXTENSION_XML));
				if (!ArrayUtils.isEmpty(children)) {
					isResultFileAvailable = true;
					break;
				}
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}

		return isResultFileAvailable;
	}

	/**
	 * The Class XmlNameFileFilter.
	 */
	public class XmlNameFileFilter implements FilenameFilter {
		
		/** The filter_. */
		private String filter_;

		/**
		 * Instantiates a new xml name file filter.
		 *
		 * @param filter the filter
		 */
		public XmlNameFileFilter(String filter) {
			filter_ = filter;
		}
		
		public boolean accept(File dir, String name) {
			return name.endsWith(filter_);
		}
	}

	/**
	 * The Class FileNameFileFilter.
	 */
	public class FileNameFileFilter implements FilenameFilter {
		
		/** The filter_. */
		private String filter_;

		/**
		 * Instantiates a new file name file filter.
		 *
		 * @param filter the filter
		 */
		public FileNameFileFilter(String filter) {
			filter_ = filter;
		}

		public boolean accept(File dir, String name) {
			return name.endsWith(filter_);
		}
	}
}
