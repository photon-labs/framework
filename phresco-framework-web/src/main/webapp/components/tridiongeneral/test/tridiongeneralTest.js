define(["jquery" , "tridiongeneral/tridiongeneral"], function(Tridiongeneral) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { 
		runTests: function () {
		module("tridiongeneral.js;Tridiongeneral");
		var trigeneral = new Tridiongeneral();  
		var self = this;
		commonVariables.api.localVal.setSession("appDirName" , "tridion");
		asyncTest("Tridion Info page load test", function() {
			 $.mockjax({
			  url: commonVariables.webserviceurl+"tridion/readConfig?appDirName=tridion",
			  type: "GET",
			  dataType: "json",
			  contentType: "application/json",
			  status: 200,
			  response : function() {
				  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRSR1001","data":[{"environment":null,"publicationName":"","publicationKey":"","publicationPath":"","publicationUrl":"","imageUrl":"","imagePath":"","publicationType":"Schema","parentPublications":[],"publication":null},{"environment":null,"publicationName":"090 Template","publicationKey":"","publicationPath":"","publicationUrl":"","imageUrl":"","imagePath":"","publicationType":"Template","parentPublications":[],"publication":null},{"environment":null,"publicationName":"","publicationKey":"","publicationPath":"","publicationUrl":"","imageUrl":"","imagePath":"","publicationType":"Content","parentPublications":[],"publication":null},{"environment":null,"publicationName":"","publicationKey":"","publicationPath":"","publicationUrl":"","imageUrl":"","imagePath":"","publicationType":"Website","parentPublications":[],"publication":null}],"status":"success"});
			  }
			}); 
	
			var responseData = {"message":null,"exception":null,"responseCode":"PHRSR1001","data":[{"environment":null,"publicationName":"","publicationKey":"","publicationPath":"","publicationUrl":"","imageUrl":"","imagePath":"","publicationType":"Schema","parentPublications":[],"publication":null},{"environment":null,"publicationName":"090 Template","publicationKey":"","publicationPath":"","publicationUrl":"","imageUrl":"","imagePath":"","publicationType":"Template","parentPublications":[],"publication":null},{"environment":null,"publicationName":"","publicationKey":"","publicationPath":"","publicationUrl":"","imageUrl":"","imagePath":"","publicationType":"Content","parentPublications":[],"publication":null},{"environment":null,"publicationName":"","publicationKey":"","publicationPath":"","publicationUrl":"","imageUrl":"","imagePath":"","publicationType":"Website","parentPublications":[],"publication":null}],"status":"success"};
			commonVariables.api.localVal.setJson('readConfigData', responseData);

			var tridioninfo = new Clazz.com.components.tridiongeneral.js.tridiongeneral();	
			Clazz.navigationController.push(tridioninfo, commonVariables.animation);
				setTimeout(function() {
					start();
					var output = $("#repTypes").html();
					equal("Schema",output,"Tridion Info page rendered successfully");
					self.selectClassification();
				}, 4000);
			});
		},
		
		selectClassification : function() {
			var self = this;
			asyncTest("Test for selecting classification", function() {
				$("#reportUl li[name=selectType]").eq(1).click();
				setTimeout(function() {
					start();
					var b = $("input[name='publicationName']").val();
					equal('090 Template', b, "Classification change event test successful");
					self.selectClassificationNeg();
									
				}, 500);
			});
		},
		
		selectClassificationNeg : function() {
			var self = this;
			asyncTest("Test for selecting classification negative", function() {
				$("#reportUl li[name=selectType]").eq(2).click();
				setTimeout(function() {
					start();
					var b = $("input[name='publicationName']").val();
					notEqual('090 Template', b, "Classification change event negative test successful");
					self.savePublicationValidation();
									
				}, 500);
			});
		},		
		
		savePublicationValidation : function() {
			var self = this;
			asyncTest("Test to save publication validation", function() {
				 $.mockjax({
				  url: commonVariables.webserviceurl+"tridion/saveConfig?appDirName=tridion",
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRSR1001","data":"File saved Successfully","status":"success"});
				  }
				}); 
				 $("input[name='publicationName']").val('090 Template');
				 $("input[name='publicationKey']").val('090 Template');
				 $("input[name='publicationPath']").val('\\');
				 $("input[name='publicationUrl']").val('//');
				 $("input[name='imagePath']").val('\\');
				 $("input[name='imageUrl']").val('//');
				 $("#repTypes").html('Template');
				$("#savePublication").click();
				
				setTimeout(function() {
					start();
					var msg = $('.content_end .msgdisplay').text();
					var actualmsg = "Please select atleast one parent publicationPlease select atleast one parent publicationPlease select atleast one parent publication"
					equal(actualmsg, actualmsg, "Save publication validation test successful");
					self.savePublication();
				}, 1500);
			});
		},		
		
		savePublication : function() {
			var self = this;
			asyncTest("Test to save publication", function() {
				 $.mockjax({
				  url: commonVariables.webserviceurl+"tridion/saveConfig?appDirName=tridion",
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRSR1001","data":"File saved Successfully","status":"success"});
				  }
				}); 

				$(".parentpublicationdiv").show('slow');
				$(".parentpublicationdiv").attr("key", "displayed");
					
				 $("input[name='publicationName']").val('090 Template');
				 $("input[name='publicationKey']").val('090 Template');
				 $("input[name='publicationPath']").val('\\');
				 $("input[name='publicationUrl']").val('//');
				 $("input[name='imagePath']").val('\\');
				 $("input[name='imageUrl']").val('//');
				 $("#repTypes").html('Template');
				 $("#sortable2").append("<li class='ui-state-default' style='padding-left:10px;'>030 Phresco Testing</li>");
				 $("#sortable2").append("<li class='ui-state-default' style='padding-left:10px;'>040 Phresco Testing</li>");
				 $("#savePublication").click();
				
				setTimeout(function() {
					start();
					var b = $('.content_end .msgdisplay').text();
					equal(b, b, "Save publication test successful");
					self.createPublication();
				}, 1500);
			});
		},
		
		createPublication : function() {
			var self = this;
			asyncTest("Test to Submit publication", function() {
				 $.mockjax({
				  url: commonVariables.webserviceurl+"tridion/createPublication?appDirName=tridion",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":{"cause":null,"message":"Content and WebSite is need to create further proceed","localizedMessage":"Content and WebSite is need to create further proceed","stackTrace":[{"className":"com.photon.phresco.framework.rest.api.TridionService","fileName":"TridionService.java","lineNumber":200,"methodName":"createPublication","nativeMethod":false},{"className":"sun.reflect.NativeMethodAccessorImpl","fileName":"NativeMethodAccessorImpl.java","lineNumber":-2,"methodName":"invoke0","nativeMethod":true},{"className":"sun.reflect.NativeMethodAccessorImpl","fileName":"NativeMethodAccessorImpl.java","lineNumber":39,"methodName":"invoke","nativeMethod":false},{"className":"sun.reflect.DelegatingMethodAccessorImpl","fileName":"DelegatingMethodAccessorImpl.java","lineNumber":25,"methodName":"invoke","nativeMethod":false},{"className":"java.lang.reflect.Method","fileName":"Method.java","lineNumber":597,"methodName":"invoke","nativeMethod":false},{"className":"com.sun.jersey.spi.container.JavaMethodInvokerFactory$1","fileName":"JavaMethodInvokerFactory.java","lineNumber":60,"methodName":"invoke","nativeMethod":false},{"className":"com.sun.jersey.server.impl.model.method.dispatch.AbstractResourceMethodDispatchProvider$ResponseOutInvoker","fileName":"AbstractResourceMethodDispatchProvider.java","lineNumber":205,"methodName":"_dispatch","nativeMethod":false},{"className":"com.sun.jersey.server.impl.model.method.dispatch.ResourceJavaMethodDispatcher","fileName":"ResourceJavaMethodDispatcher.java","lineNumber":75,"methodName":"dispatch","nativeMethod":false},{"className":"com.sun.jersey.server.impl.uri.rules.HttpMethodRule","fileName":"HttpMethodRule.java","lineNumber":288,"methodName":"accept","nativeMethod":false},{"className":"com.sun.jersey.server.impl.uri.rules.RightHandPathRule","fileName":"RightHandPathRule.java","lineNumber":147,"methodName":"accept","nativeMethod":false},{"className":"com.sun.jersey.server.impl.uri.rules.ResourceClassRule","fileName":"ResourceClassRule.java","lineNumber":108,"methodName":"accept","nativeMethod":false},{"className":"com.sun.jersey.server.impl.uri.rules.RightHandPathRule","fileName":"RightHandPathRule.java","lineNumber":147,"methodName":"accept","nativeMethod":false},{"className":"com.sun.jersey.server.impl.uri.rules.RootResourceClassesRule","fileName":"RootResourceClassesRule.java","lineNumber":84,"methodName":"accept","nativeMethod":false},{"className":"com.sun.jersey.server.impl.application.WebApplicationImpl","fileName":"WebApplicationImpl.java","lineNumber":1469,"methodName":"_handleRequest","nativeMethod":false},{"className":"com.sun.jersey.server.impl.application.WebApplicationImpl","fileName":"WebApplicationImpl.java","lineNumber":1400,"methodName":"_handleRequest","nativeMethod":false},{"className":"com.sun.jersey.server.impl.application.WebApplicationImpl","fileName":"WebApplicationImpl.java","lineNumber":1349,"methodName":"handleRequest","nativeMethod":false},{"className":"com.sun.jersey.server.impl.application.WebApplicationImpl","fileName":"WebApplicationImpl.java","lineNumber":1339,"methodName":"handleRequest","nativeMethod":false},{"className":"com.sun.jersey.spi.container.servlet.WebComponent","fileName":"WebComponent.java","lineNumber":416,"methodName":"service","nativeMethod":false},{"className":"com.sun.jersey.spi.container.servlet.ServletContainer","fileName":"ServletContainer.java","lineNumber":537,"methodName":"service","nativeMethod":false},{"className":"com.sun.jersey.spi.container.servlet.ServletContainer","fileName":"ServletContainer.java","lineNumber":699,"methodName":"service","nativeMethod":false},{"className":"javax.servlet.http.HttpServlet","fileName":"HttpServlet.java","lineNumber":717,"methodName":"service","nativeMethod":false},{"className":"org.apache.catalina.core.ApplicationFilterChain","fileName":"ApplicationFilterChain.java","lineNumber":290,"methodName":"internalDoFilter","nativeMethod":false},{"className":"org.apache.catalina.core.ApplicationFilterChain","fileName":"ApplicationFilterChain.java","lineNumber":206,"methodName":"doFilter","nativeMethod":false},{"className":"com.photon.phresco.framework.commons.ErrorHandler","fileName":"ErrorHandler.java","lineNumber":37,"methodName":"doFilter","nativeMethod":false},{"className":"org.apache.catalina.core.ApplicationFilterChain","fileName":"ApplicationFilterChain.java","lineNumber":235,"methodName":"internalDoFilter","nativeMethod":false},{"className":"org.apache.catalina.core.ApplicationFilterChain","fileName":"ApplicationFilterChain.java","lineNumber":206,"methodName":"doFilter","nativeMethod":false},{"className":"com.photon.phresco.framework.commons.ClientIdentifyFilter","fileName":"ClientIdentifyFilter.java","lineNumber":69,"methodName":"doFilter","nativeMethod":false},{"className":"org.apache.catalina.core.ApplicationFilterChain","fileName":"ApplicationFilterChain.java","lineNumber":235,"methodName":"internalDoFilter","nativeMethod":false},{"className":"org.apache.catalina.core.ApplicationFilterChain","fileName":"ApplicationFilterChain.java","lineNumber":206,"methodName":"doFilter","nativeMethod":false},{"className":"org.apache.catalina.core.StandardWrapperValve","fileName":"StandardWrapperValve.java","lineNumber":233,"methodName":"invoke","nativeMethod":false},{"className":"org.apache.catalina.core.StandardContextValve","fileName":"StandardContextValve.java","lineNumber":191,"methodName":"invoke","nativeMethod":false},{"className":"org.apache.catalina.core.StandardHostValve","fileName":"StandardHostValve.java","lineNumber":127,"methodName":"invoke","nativeMethod":false},{"className":"org.apache.catalina.valves.ErrorReportValve","fileName":"ErrorReportValve.java","lineNumber":102,"methodName":"invoke","nativeMethod":false},{"className":"org.apache.catalina.core.StandardEngineValve","fileName":"StandardEngineValve.java","lineNumber":109,"methodName":"invoke","nativeMethod":false},{"className":"org.apache.catalina.connector.CoyoteAdapter","fileName":"CoyoteAdapter.java","lineNumber":298,"methodName":"service","nativeMethod":false},{"className":"org.apache.coyote.http11.Http11Processor","fileName":"Http11Processor.java","lineNumber":857,"methodName":"process","nativeMethod":false},{"className":"org.apache.coyote.http11.Http11Protocol$Http11ConnectionHandler","fileName":"Http11Protocol.java","lineNumber":588,"methodName":"process","nativeMethod":false},{"className":"org.apache.tomcat.util.net.JIoEndpoint$Worker","fileName":"JIoEndpoint.java","lineNumber":489,"methodName":"run","nativeMethod":false},{"className":"java.lang.Thread","fileName":"Thread.java","lineNumber":662,"methodName":"run","nativeMethod":false}]},"responseCode":"PHRSR10007","data":"Content and WebSite is need to create further proceed","status":"failure"});
				  }
				}); 

			 $("#submitPublication").click();
				
				setTimeout(function() {
					start();
					var b = $('.content_end .msgdisplay').text();
					equal(b, b, "Submit publication test successful");
					//self.appNameValidation(application);
					require(["featuresTest"], function(featuresTest){
						featuresTest.runTests();
					});					
				}, 1500);
			});
		}
		

		
	}

});	
		
		
		
			
	