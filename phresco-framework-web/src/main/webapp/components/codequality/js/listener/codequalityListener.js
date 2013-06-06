define(["codequality/api/codequalityAPI"], function() {

	Clazz.createPackage("com.components.codequality.js.listener");

	Clazz.com.components.codequality.js.listener.CodequalityListener = Clazz.extend(Clazz.Widget, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		codequalityAPI : null,
		mavenServiceListener : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			if(self.codequalityAPI === null)
				self.codequalityAPI = new Clazz.com.components.codequality.js.api.CodeQualityAPI();

			if(self.mavenServiceListener === null)	{
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
				});
			}				
		},
		
		codeValidate : function() {
			var self = this;
			var ipjson = $("#codeValidateForm").serialize();
			var appdetails = self.codequalityAPI.localVal.getJson('appdetails');
			var queryString = '';
			appId = appdetails.data.appInfos[0].id;
			projectId = appdetails.data.id;
			customerId = appdetails.data.customerIds[0];
			username = self.codequalityAPI.localVal.getSession('username');
						
			if(appdetails != null){
				queryString ="username="+username+"&appId="+appId+"&customerId="+customerId+"&goal=validate-code&phase=validate-code&projectId="+projectId+"&"+ipjson;
			}
			$('#iframePart').html('');
			if(self.mavenServiceListener === null)	{
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
					
					self.mavenServiceListener.mvnCodeValidation(queryString, '#iframePart', function(returnVal){
						callback(returnVal);
					});
				});
			}else{
				self.mavenServiceListener.mvnCodeValidation(queryString, '#iframePart', function(returnVal){
					//callback(returnVal);
				});
			}			
			/*header.requestMethod ="POST";
			header.webserviceurl = commonVariables.webserviceurl+"app/codeValidate?username="+username+"&appId="+appId+"&customerId="+customerId+"&goal=validate-code&phase=validate-code&projectId="+projectId+"&"+inputData;
			
			 self.codequalityAPI.codequality(self.getRequestHeader(ipjson , "validate-code"), function(response) {
				$('#iframePart').html('');
				$('#iframePart').append(response.log);
				$('#iframePart').append("<br>") ;
				if(response.status == "STARTED"){
					self.readLog(response);
				}			
				else if(response.status == 'ERROR'){
					$('#iframePart').append(response.service_exception) ;
				}
			}); */
		},
		

		onPrgoress : function(clicked) {
			var check = $(clicked).attr('data-flag');
			var value = $('.build_info').width();
			var value1 = $('.build_progress').width();
			if(check == "true") {
				$('.build_info').animate({width: '97%'},500);
				$('.build_progress').animate({right: -value1},500);
				$('.build_close').animate({right: '0px'},500);
				$(clicked).attr('data-flag','false');
			} else {
				$('.build_info').animate({width: window.innerWidth/1.6},500);
				$('.build_progress').animate({right: '10px'},500);
				$('.build_close').animate({right: value1+10},500);
				$(clicked).attr('data-flag','true');
				$(window).resize();
			}
		},
		
		readLog : function(resDatt){
			var self = this;
			self.codequalityAPI.codequality(self.getRequestHeader(resDatt , "readlog"), function(response) {
				var logStr = response.log;
				var logColor = "black";
				if(logStr.match("ERROR")){
					logColor = "red";
				}else if(logStr.match("WARNING")){
					logColor = "yellow";
				}
				
				$('#iframePart').append("<font style=color:"+logColor+">"+response.log+"</font>") ;
				$('#iframePart').append("<br>");
				if(response.status == 'INPROGRESS'){
					self.readLog(resDatt);
				}else if(response.status == 'COMPLETED'){
					var validateAgainst = $("#sonar").val();
					self.getIframeReport(validateAgainst);
				}
			});
		},
		
		getRequestHeader : function(inputData, action) {
			var self=this, header, username, appId, customerId, projectId;
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: '',
				data: ''
			}
			if(action == "validate-code"){
				var appdetails = self.codequalityAPI.localVal.getJson('appdetails');
				appId = appdetails.data.appInfos[0].id;
				projectId = appdetails.data.id;
				customerId = appdetails.data.customerIds[0];
				username = self.codequalityAPI.localVal.getSession('username');
				
				header.requestMethod ="POST";
				header.webserviceurl = commonVariables.webserviceurl+"app/codeValidate?username="+username+"&appId="+appId+"&customerId="+customerId+"&goal=validate-code&phase=validate-code&projectId="+projectId+"&"+inputData;
			}
			if(action == "readlog"){
				var uniquekey = inputData.uniquekey;
				header.webserviceurl = commonVariables.webserviceurl+"app/readlog?uniquekey="+uniquekey;
			}
			
			if(action == "reporttypes"){
				var appDirName = this.codequalityAPI.localVal.getSession('appDirName');
				console.info('appDirName = ' , appDirName);
				header.webserviceurl = commonVariables.webserviceurl+"parameter/codeValidationReportTypes?appDirName="+appDirName+"&goal=validate-code";
			}
			
			if(action == "iframereport"){
				var appDirName = this.codequalityAPI.localVal.getSession('appDirName');
				//console.info('appDirName = ' , appDirName + 'inputData = '  + inputData);
				username = self.codequalityAPI.localVal.getSession('username');
				header.webserviceurl = commonVariables.webserviceurl+"parameter/iFrameReport?appDirName="+appDirName+"&validateAgainst="+inputData+"&customerId=photon&userId="+username;
			}

			return header;
		},
				
		getReportTypes : function(header, callback) {
			var self = this;
			try {
				commonVariables.loadingScreen.showLoading();
				self.codequalityAPI.codequality(header,
					function(response) {
						if (response !== null) {
							callback(response);
							commonVariables.loadingScreen.removeLoading();
						} else {
							commonVariables.loadingScreen.removeLoading();
							callback({ "status" : "service failure"});
						}
					},

					function(textStatus) {
						commonVariables.loadingScreen.removeLoading();
						var data = $.parseJSON(textStatus);
						$('#content_div').html(data.message);

					}
				);
			} catch(exception) {
				$('#content_div').html(exception);
				commonVariables.loadingScreen.removeLoading();
			}
		},

		constructHtml : function(response){
			var self = this;
			if(response.message == "Dependency returned successfully"){
				var typeLi = '';
				$.each(response.data, function(index, resdata) {
					var innerUl = '';
					if(resdata.options == null){
						typeLi += "<li name='selectType' key="+resdata.validateAgainst.key+" data="+resdata.validateAgainst.value+">"+resdata.validateAgainst.value+"</li>";
					}else{
						$.each(resdata.options, function(index, optvalue) {
							innerUl += "<li name='selectType' key="+resdata.validateAgainst.key+" data="+resdata.validateAgainst.value+">"+optvalue.value+"</li>";
						});
						typeLi += "<li disabled='disabled' key="+resdata.validateAgainst.key+" data="+resdata.validateAgainst.value+">"+resdata.validateAgainst.value+'<ul>'+innerUl+"</ul></li>";
					}
				});
				var dropdownLi = '<ul class="nav"><li id="fat-menu" class="dropdown"><a href="#" id="drop5" role="button" class="dropdown-toggle" data-toggle="dropdown"><b id="repTypes" >'+response.data[0].validateAgainst.value+'</b><b class="caret"></b></a> <div class="dropdown-menu cust_sel code_test_opt" role="menu" aria-labelledby="drop5"> <ul id="reportUl">'+typeLi+'</ul></div></li></ul>';
				$("#codereportTypes").append(dropdownLi);
				self.onProjects();
				self.getIframeReport(response.data[0].validateAgainst.key);
			}else {
				$('#iframePart').html('');
				$('#iframePart').append(response.log);			
			}
		},
		
		onProjects : function() {
			var self = this, validateAgainst ;
			$("#reportUl li[name=selectType]").click(function() {
				validateAgainst = $(this).attr('key');
				$("#repTypes").html($(this).attr('data'));
				self.getIframeReport(validateAgainst);
			});
		},
		
		getIframeReport : function(validateAgainst){
			var self = this;
			$('#content_div').html('Sonar Report will appear here');
			try {
				self.codequalityAPI.codequality(self.getRequestHeader(validateAgainst , "iframereport"), 
					function(iframereport) {
						if(iframereport.data != null){
							var iframedata = "<iframe src="+iframereport.data+" width=98% height=100%></iframe>";
							$('#content_div').html(iframedata);
						}else{
							$('#content_div').html(iframereport.message);
						}
					},
					function(textStatus) {
						var data = $.parseJSON(textStatus);
						$('#content_div').html(data.message);
					}
				);
			} catch(exception) {
				//console.info('exception = ' , exception);
				$('#content_div').html('tres');
			}	
		}
		
	});

	return Clazz.com.components.codequality.js.listener.CodequalityListener;
});