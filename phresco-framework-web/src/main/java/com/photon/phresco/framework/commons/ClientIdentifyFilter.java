package com.photon.phresco.framework.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.itextpdf.text.log.SysoLogger;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.VersionInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.util.ClientIdentifyFilterConstants;
import com.photon.phresco.framework.rest.api.ResponseInfo;
import com.photon.phresco.framework.rest.api.util.ActionFunction;
import com.photon.phresco.service.client.impl.ClientHelper;
import com.photon.phresco.service.client.impl.RestClient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse.Status;

public class ClientIdentifyFilter implements Filter , ClientIdentifyFilterConstants{
	
	private static final Logger S_LOGGER= Logger.getLogger(ActionFunction.class);
	private static boolean isDebugEnabled = S_LOGGER.isDebugEnabled();

	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
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
             	
             	JSONObject cutomer_theme = listApptypeTechInfo(cutomername);
        		PrintWriter  out = response.getWriter();
        		if(cutomer_theme != null) {
        			
	        		String customer_json_theme = "";
	        		customer_json_theme = new Gson().toJson(cutomer_theme);
	        		
	        		if(!("".equalsIgnoreCase(customer_json_theme))) {
	        			
	        			if (isDebugEnabled) {
	        				S_LOGGER.debug("<script type=\"text/javascript\"> localStorage.setItem(\"customertheme\",JSON.stringify("+customer_json_theme+")); </script> ");
	        			}
	        			
	        			out.print("<script type=\"text/javascript\"> localStorage.setItem(\""+ THEME_KEY +"\",JSON.stringify("+ customer_json_theme +")); </script> ");
	        		
	        		}
	        		
	        		String customer_logo="";
	        		try {
	        			
	        			customer_logo = getimage((String)cutomer_theme.get("customerid"));
	        			
					} catch (PhrescoException e) {
						
						S_LOGGER.error(e.getMessage());
					}
	        		if(!("".equalsIgnoreCase(customer_logo))) {
	        			
	        			if (isDebugEnabled) {
	        				S_LOGGER.debug("<script type=\"text/javascript\"> localStorage.setItem(\"customertheme\",JSON.stringify("+customer_json_theme+")); </script> ");
	        			}
	        			
	        			out.print("<script type=\"text/javascript\"> localStorage.setItem(\""+ LOGO_KEY +"\",\""+ customer_logo +"\"); </script> ");	
	        		}
        		}
        		
        		req.getRequestDispatcher(ROUTE_URL).include(req, response);
             	
            }
		
		
		
	}
	
	
	
	public JSONObject listApptypeTechInfo(String customerName) {
		ResponseInfo<Customer> responseData = new ResponseInfo<Customer>();
		try {
			Client client = ClientHelper.createClient();
			FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
	        WebResource resource = client.resource(configuration.getServerPath() + "/admin/customers");
	        System.out.println("url hit is -->"+configuration.getServerPath() + "/admin/customers");
	        resource = resource.queryParam("customerName", customerName);
	        resource.accept(MediaType.APPLICATION_JSON);
	        ClientResponse response = resource.get(ClientResponse.class);
	        GenericType<Customer> genericType = new GenericType<Customer>() {};
	        Customer customer = response.getEntity(genericType);
	        if (!customerName.equalsIgnoreCase("Photon")) {
		        Map<String, String> theme = customer.getFrameworkTheme();
				String brandingColor = theme.get("brandingColor");
				String accordionBackGroundColor =  theme.get("accordionBackGroundColor");
				String bodyBackGroundColor =  theme.get("bodyBackGroundColor");
				String buttonColor =  theme.get("ButtonColor");
				String pageHeaderColor =  theme.get("PageHeaderColor");
				String copyRightColor =  theme.get("CopyRightColor");
				String labelColor =  theme.get("LabelColor");
				String menufontColor =  theme.get("MenufontColor"); 
				String menuBackGround =  theme.get("MenuBackGround");
//				String subMenuBackGround =  theme.get("SubMenuBackGround");
				String copyRight =  theme.get("CopyRight");
				String disabledLabelColor =  theme.get("DisabledLabelColor");
				String loginLogo =  theme.get("loginlogo");
				String logoPadding =  theme.get("logopadding");
			
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
						if (customercolor.contains("brandingColor") && StringUtils.isNotEmpty(brandingColor)) {
							fields.put(key,  customercolor.replace("brandingColor", brandingColor) + " !important");
						}
						if (customercolor.equals("menuBackGround") && StringUtils.isNotEmpty(menuBackGround)) {
							fields.put(key,  customercolor.replace("menuBackGround", menuBackGround) + " !important");
						}
						if (customercolor.contains("backgroundColor") && StringUtils.isNotEmpty(bodyBackGroundColor)) {
							fields.put(key,  customercolor.replace("backgroundColor", bodyBackGroundColor) + " !important");
						}
						if (customercolor.contains("accordionBackGroundColor") && StringUtils.isNotEmpty(accordionBackGroundColor)) {
							fields.put(key,  customercolor.replace("accordionBackGroundColor", accordionBackGroundColor) + " !important");
						}
						if (customercolor.contains("bodyBackGroundColor") && StringUtils.isNotEmpty(bodyBackGroundColor)) {
							fields.put(key,  customercolor.replace("bodyBackGroundColor", bodyBackGroundColor) + " !important");
						}
						if (customercolor.contains("buttonColor") && StringUtils.isNotEmpty(buttonColor)) {
							fields.put(key,  customercolor.replace("buttonColor", buttonColor) + " !important");
						}
						if (customercolor.contains("pageHeaderColor") && StringUtils.isNotEmpty(pageHeaderColor)) {
							fields.put(key,  customercolor.replace("pageHeaderColor", pageHeaderColor) + " !important");
						}
						if (customercolor.contains("copyRightColor") && StringUtils.isNotEmpty(copyRightColor)) {
							fields.put(key,  customercolor.replace("copyRightColor", copyRightColor) + " !important");
						}
						if (customercolor.contains("labelColor") && StringUtils.isNotEmpty(labelColor)) {
							fields.put(key,  customercolor.replace("labelColor", labelColor) + " !important");
						}
						if (customercolor.contains("menufontColor") && StringUtils.isNotEmpty(menufontColor)) {
							fields.put(key,  customercolor.replace("menufontColor", menufontColor) + " !important");
						}
						if (customercolor.contains("copyRight") && StringUtils.isNotEmpty(copyRight)) {
							fields.put(key,  customercolor.replace("copyRight", copyRight) + " !important");
						}
						if (customercolor.contains("disabledLabelColor") && StringUtils.isNotEmpty(disabledLabelColor)) {
							fields.put(key,  customercolor.replace("disabledLabelColor", disabledLabelColor) + " !important");
						}
						if (customercolor.contains("loginlogo") && StringUtils.isNotEmpty(loginLogo)) {
							fields.put(key,  customercolor.replace("loginlogo", loginLogo) + " !important");
						}
						if (customercolor.contains("logopadding")&& StringUtils.isNotEmpty(logoPadding)) {
							fields.put(key,  customercolor.replace("logopadding", logoPadding) + " !important");
						}
					}
				}
				
				jsonObject.put("customerid", customer.getId());
				
				return jsonObject;
				
	        }
	        
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
