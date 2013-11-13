package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.io.FileUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

public class MockServer {

	HttpsServer httpsServer;
	String fileName;
	HashMap<String, String> respMap;
	HashMap<String, ArrayList<Integer>> respMap1;
	HashMap<String, Integer> respMap2;
	HashMap<String, String> respMap3;
	HashMap<String, Class<?> > respMap4;
	int ctr=0;
	boolean respFlag=true;
	MockServer(int portNo, String host) throws IOException{
		respMap =new HashMap<String, String>();
		respMap1 =new HashMap<String, ArrayList<Integer>>();
		respMap2 =new HashMap<String, Integer>();
		respMap3 =new HashMap<String, String>();
		respMap4 =new HashMap<String, Class<?> >();
		populateMap();
		try{
			httpsServer = HttpsServer.create(new InetSocketAddress(host,portNo), 0);

			for (final Map.Entry<String, String> entry : respMap.entrySet()) {
				HttpHandler handler = new HttpHandler() {
					public void handle(HttpExchange exchange) throws IOException {
						String data=FileUtils.readFileToString(new File(this.getClass().getResource("/"+entry.getValue()).getFile()));
						exchange.getResponseHeaders().set("Content-Type", "text/xml");
						exchange.sendResponseHeaders(200,data.length());
						exchange.getResponseBody().write(data.getBytes());
						exchange.close();
					}	
				};
				httpsServer.createContext(entry.getKey(), handler);
			}
			char[] passphrase = "changeit".toCharArray();
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream(new File(this.getClass().getResource("/server_keystore.ks").getFile())), passphrase);
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, passphrase);
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(ks);
			SSLContext ssl = SSLContext.getInstance("TLS");
			ssl.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			httpsServer.setHttpsConfigurator(new HttpsConfigurator(ssl) {

				public void configure(HttpsParameters params) {            
					InetSocketAddress remote = params.getClientAddress();
					SSLContext c = getSSLContext();
					SSLParameters sslparams = c.getDefaultSSLParameters();
					params.setSSLParameters (sslparams);
				}

			});

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void startServer() { //start server
		httpsServer.start();
	}

	public void stopServer() { //stop server
		httpsServer.stop(0);
	}

	private void populateMap() { //response json
		respMap.put("/services/auth/login", "splunkLogin.xml");
		respMap.put("/services/server/info", "splunkServerInfo.xml");
		respMap.put("/services/search/jobs", "oneShotSearch.json");
	}  
}
