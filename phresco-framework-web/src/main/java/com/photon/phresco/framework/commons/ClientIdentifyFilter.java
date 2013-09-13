package com.photon.phresco.framework.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.plexus.util.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.util.ClientIdentifyFilterConstants;
import com.photon.phresco.framework.model.ClientIdentifyModel;
import com.photon.phresco.framework.rest.api.util.ActionFunction;
import com.photon.phresco.service.client.impl.ClientHelper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class ClientIdentifyFilter implements Filter , ClientIdentifyFilterConstants {
	
	private static final Logger S_LOGGER= Logger.getLogger(ActionFunction.class);
	private static boolean isDebugEnabled = S_LOGGER.isDebugEnabled();

	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
			try {
				HttpServletRequest req = null;
				String url="";
				if (request instanceof HttpServletRequest) {
					req = (HttpServletRequest)request;
					url = req.getRequestURL().toString();

					if (isDebugEnabled) {
						S_LOGGER.debug("url : "+url);
					}
				}

				if(	(url.contains(LOCALES)) || (url.contains(JSON)) || (url.contains(INDEX_HTML)) || (url.contains(REST_CALL)) || (url.contains(COMPONENTS)) || (url.contains(JS)) || (url.contains(LIB)) || (url.contains(THEMES))) {
					chain.doFilter(req, response);
				}
				else {
					
					String forcompare = url;
				 	int location = forcompare.lastIndexOf("/");
				 	String cutomername = forcompare.substring(location);
				 	cutomername = cutomername.substring(1);
					try {
						PrintWriter  out = response.getWriter();
						out.print("<script type=\"text/javascript\"> localStorage.removeItem(\""+ THEME_KEY +"\"); </script> ");
						out.print("<script type=\"text/javascript\"> localStorage.removeItem(\""+ LOGO_KEY +"\"); </script> ");
						out.print("<script type=\"text/javascript\"> localStorage.removeItem(\""+ STATUS_MESSAGE +"\"); </script> ");
						out.print("<script type=\"text/javascript\"> localStorage.removeItem(\""+ CUSTOMER_NAME_KEY +"\"); </script> ");
						ClientIdentifyModel customerdetails = getCustomerProperties(cutomername);
				    	if(customerdetails.getStatus()) {
				    		JSONObject cutomer_theme = processCustomerProperties(customerdetails.getCustomer());
				        	String customer_json_theme = new Gson().toJson(cutomer_theme);
				        	String customer_logo= customerdetails.getCustomerlogo();
							String customername= customerdetails.getCustomer().getName();
				    		out.print("<script type=\"text/javascript\"> localStorage.setItem(\""+ THEME_KEY +"\",JSON.stringify("+ customer_json_theme +")); </script> ");
				    		out.print("<script type=\"text/javascript\"> localStorage.setItem(\""+ LOGO_KEY +"\",\""+ customer_logo +"\"); </script> ");
							out.print("<script type=\"text/javascript\"> localStorage.setItem(\""+ CUSTOMER_NAME_KEY +"\",\""+ customername +"\"); </script> ");							
				    	}
				    	else {
				    			out.print("<script type=\"text/javascript\"> localStorage.setItem(\""+ STATUS_MESSAGE +"\",\""+ customerdetails.getMessage() +"\"); </script> ");	
				    	}
					} catch (PhrescoException e) {
						S_LOGGER.error("Exception occured in the Client properties obatining process"+e.getMessage());
					} catch (Exception e) {
						S_LOGGER.error("Exception occured in the Client properties obatining process"+e.getMessage());
						e.printStackTrace();
					}
					req.getRequestDispatcher(ROUTE_URL).include(req, response);
				}
			} catch (Exception e) {
				S_LOGGER.error("A major exception occured in the Client identify filter "+e.getMessage());
				chain.doFilter(request, response);
			}
	}
	
	public ClientIdentifyModel getCustomerProperties(String cutomername) throws PhrescoException, IOException {
		Customer customer = null;
		InputStream iconStream = null;
		FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
		ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJsonProvider.class);
		Client c = Client.create(config);
		
	    WebResource resource = c.resource(configuration.getServerPath() +REST_API_CUSTOMER_ICON);
	    resource = resource.queryParam("context", cutomername);
	    resource.accept(org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE);
	    ClientResponse iconResponse = resource.get(ClientResponse.class);
        resource = c.resource(configuration.getServerPath() + REST_API_CUSTOMERINFO);
	    resource = resource.queryParam("context", cutomername);
	    resource.accept(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
	    ClientResponse customerResponse = resource.get(ClientResponse.class);
	    if(iconResponse.getStatus() != 200 && customerResponse.getStatus() != 200) {
	       ClientIdentifyModel data = new ClientIdentifyModel();
	       data.setStatus(false);
	       data.setMessage(INVALID_CUSTOMER);
	       return data;
		}
	    if(iconResponse.getStatus() != 200) {
		    ClientIdentifyModel data = new ClientIdentifyModel();
		    data.setStatus(false);
		    data.setMessage(NO_IMAGE);
		    return data;
		}
	    if(customerResponse.getStatus() != 200) {
			if (isDebugEnabled) {
				S_LOGGER.debug("Response Status :"+Response.Status.PRECONDITION_FAILED.getReasonPhrase());
			}
			ClientIdentifyModel data = new ClientIdentifyModel();
		    data.setStatus(false);
		    data.setMessage(NO_ID);
		    return data;
		}
	    if(iconResponse.getStatus() == 200 && customerResponse.getStatus() == 200) {
	    	iconStream = iconResponse.getEntityInputStream();
		    customer = customerResponse.getEntity(Customer.class);
			byte[] imgByte = null;
			imgByte = IOUtils.toByteArray(iconStream);
		    byte[] encodedImage = Base64.encodeBase64(imgByte);
	        String encodeImg = new String(encodedImage);
	        ClientIdentifyModel data = new ClientIdentifyModel();
	        data.setCustomer(customer);
	        data.setCustomerlogo(encodeImg);
	        data.setStatus(true);
	        return data;
	    }
//		else {
//			if (isDebugEnabled) {
//				S_LOGGER.debug("Response Status :"+INVALID);
//			}
//			ClientIdentifyModel data = new ClientIdentifyModel();
//		    data.setStatus(false);
//		    data.setMessage(INVALID);
//			return null;
//			}
		return null;
	}
	
	public JSONObject processCustomerProperties(Customer customer) {
		
		String customerName = customer.getName();
		try {
	        	Map<String, String> theme = customer.getFrameworkTheme();
				String loginLogoMargin = theme.get("loginLogoMargin");
				String pageLogoPadding = theme.get("pageLogoPadding");
				String headerLinkColor = theme.get("headerLinkColor");
				String headerActiveLinkColor = theme.get("headerActiveLinkColor");
				String editNavigationLink = theme.get("editNavigationLink");
				String buttonBackGroundColor = theme.get("buttonBackGroundColor");
				String consoleHeaderColor = theme.get("consoleHeaderColor");
				String copyrightLabelColor = theme.get("copyrightLabelColor");
				String headerBackGroundcolorTop = theme.get("headerBackGroundcolorTop");
				String headerBackGroundcolorBottom = theme.get("headerBackGroundcolorBottom");
				String footerBackGroundcolorTop = theme.get("footerBackGroundcolorTop");
				String footerBackGroundcolorBottom = theme.get("footerBackGroundcolorBottom");
				String pageTitleBackGroundTop = theme.get("pageTitleBackGroundTop");
				String pageTitleBackGroundBottom = theme.get("pageTitleBackGroundBottom");
				String editNavigationActiveBackGroundTop = theme.get("editNavigationActiveBackGroundTop");
				String editNavigationActiveBackGroundBottom = theme.get("editNavigationActiveBackGroundBottom");
				String bottomButtonPanelTop = theme.get("bottomButtonPanelTop");
				String bottomButtonPanelBottom = theme.get("bottomButtonPanelBottom");
				String customerBaseColor = theme.get("customerBaseColor");
				String welcomeUserIcon = theme.get("welcomeUserIcon");
				String pageTitleColor = theme.get("pageTitleColor");
				String copyRightLabel = theme.get("copyRightLabel");
				String customerTitle = theme.get("customerTitle");
				
			    InputStream stream =   this.getClass().getClassLoader().getResourceAsStream("customercss.xml");
			    String result = getStringFromInputStream(stream);
				
			    JSONParser parser = new JSONParser();
				JSONObject jsonObject = (JSONObject) parser.parse(result);
			
				Set keySet = jsonObject.keySet();
				for (Object object : keySet) {
					JSONObject fields = (JSONObject)jsonObject.get(object.toString());
					Set fieldSet = fields.keySet();
					for (Object key : fieldSet) {
						Object value = fields.get(key);
						String customercolor = value.toString();
						if (customercolor.contains("loginLogoMargin") && StringUtils.isNotEmpty(loginLogoMargin)) {
							fields.put(key,  customercolor.replace("pageLogoPadding", pageLogoPadding) + " !important");
						}
						if (customercolor.contains("pageTitleColor") && StringUtils.isNotEmpty(pageTitleColor)) {
							fields.put(key,  customercolor.replace("pageTitleColor", pageTitleColor) + " !important");
						}
						if (customercolor.equals("pageLogoPadding") && StringUtils.isNotEmpty(pageLogoPadding)) {
							fields.put(key,  customercolor.replace("pageLogoPadding", pageLogoPadding) + " !important");
						}
						if (customercolor.equals("copyrightLabelColor") && StringUtils.isNotEmpty(copyrightLabelColor)) {
							fields.put(key,  customercolor.replace("copyrightLabelColor", copyrightLabelColor) + " !important");
						}
						if (customercolor.contains("headerLinkColor") && StringUtils.isNotEmpty(headerLinkColor)) {
							fields.put(key,  customercolor.replace("headerLinkColor", headerLinkColor) + " !important");
						}
						if (customercolor.contains("headerActiveLinkColor") && StringUtils.isNotEmpty(headerActiveLinkColor)) {
							fields.put(key,  customercolor.replace("headerActiveLinkColor", headerActiveLinkColor) + " !important");
						}
						if (customercolor.contains("editNavigationLink") && StringUtils.isNotEmpty(editNavigationLink)) {
							fields.put(key,  customercolor.replace("editNavigationLink", editNavigationLink) + " !important");
						}
						if (customercolor.contains("welcomeUserIcon") && StringUtils.isNotEmpty(welcomeUserIcon)) {
							fields.put(key,  customercolor.replace("welcomeUserIcon", welcomeUserIcon) + " !important");
						}
						if (customercolor.contains("buttonBackGroundColor") && StringUtils.isNotEmpty(buttonBackGroundColor)) {
							fields.put(key,  customercolor.replace("buttonBackGroundColor", buttonBackGroundColor) + " !important");
						}
						if (customercolor.contains("consoleHeaderColor") && StringUtils.isNotEmpty(consoleHeaderColor)) {
							fields.put(key,  customercolor.replace("consoleHeaderColor", consoleHeaderColor) + " !important");
						}
						if (customercolor.contains("headerBackGroundcolorTop") && StringUtils.isNotEmpty(headerBackGroundcolorTop)) {
							fields.put(key,  customercolor.replace("headerBackGroundcolorTop", headerBackGroundcolorTop).replaceAll("headerBackGroundcolorBottom", headerBackGroundcolorBottom));
						}
						if (customercolor.contains("footerBackGroundcolorTop") && StringUtils.isNotEmpty(footerBackGroundcolorTop)) {
							fields.put(key,  customercolor.replace("footerBackGroundcolorTop", footerBackGroundcolorTop).replaceAll("footerBackGroundcolorBottom", footerBackGroundcolorBottom));
						}
						if (customercolor.contains("pageTitleBackGroundTop") && StringUtils.isNotEmpty(pageTitleBackGroundTop)) {
							fields.put(key,  customercolor.replace("pageTitleBackGroundTop", pageTitleBackGroundTop).replaceAll("pageTitleBackGroundBottom", pageTitleBackGroundBottom));
						}
						if (customercolor.contains("editNavigationActiveBackGroundTop") && StringUtils.isNotEmpty(editNavigationActiveBackGroundTop)) {
							fields.put(key,  customercolor.replace("editNavigationActiveBackGroundTop", editNavigationActiveBackGroundTop).replaceAll("editNavigationActiveBackGroundBottom", editNavigationActiveBackGroundBottom));
						}
						if (customercolor.contains("bottomButtonPanelTop") && StringUtils.isNotEmpty(bottomButtonPanelTop)) {
							fields.put(key,  customercolor.replace("bottomButtonPanelTop", bottomButtonPanelTop).replaceAll("bottomButtonPanelBottom", bottomButtonPanelBottom));
						}
						if (customercolor.contains("customerBaseColor") && StringUtils.isNotEmpty(customerBaseColor)) {
							fields.put(key,  customercolor.replace("customerBaseColor", customerBaseColor) + " !important");
						}
					}
				}
				jsonObject.put("customerid", customer.getId());
				jsonObject.put("copyRightLabel", copyRightLabel);
				jsonObject.put("customerTitle", customerTitle);
				return jsonObject;
		} catch (PhrescoException e) {
			S_LOGGER.error(e.getMessage());
		} catch (ParseException e) {
			S_LOGGER.error(e.getMessage());
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	
	private static String getStringFromInputStream(InputStream is) throws Exception {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	
	private String getimage(String id) throws PhrescoException, IOException {
		
		Client client = ClientHelper.createClient();
		FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
        WebResource resource = client.resource(configuration.getServerPath() + "/admin/icon");
        
        if (isDebugEnabled) {
			S_LOGGER.debug("url hit is -->"+configuration.getServerPath() + "/admin/icon");
		}
        
        resource = resource.queryParam("id", id);
        resource.accept(MediaType.MULTIPART_FORM_DATA);
        ClientResponse response = resource.get(ClientResponse.class);
        InputStream fileInputStream = response.getEntityInputStream();
		byte[] imgByte = null;
		imgByte = IOUtils.toByteArray(fileInputStream);
	    byte[] encodedImage = Base64.encodeBase64(imgByte);
        String encodeImg = new String(encodedImage);
		return encodeImg;
	}
	
	
	public void destroy() {
	}
	

}
