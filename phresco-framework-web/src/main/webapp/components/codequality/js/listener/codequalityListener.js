define(["codequality/api/codequalityAPI"], function() {

	Clazz.createPackage("com.components.codequality.js.listener");

	Clazz.com.components.codequality.js.listener.CodequalityListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
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
			$('#content_div').html('Sonar Report will appear here');
			self.openConsole();//To open the console
			
			/* var value1 = $('.build_progress').width();
			$('.build_info').animate({width: window.innerWidth/1.6},500);
			$('.build_progress').animate({right: '10px'},500);
			$('.build_close').animate({right: value1+10},500);
			$(window).resize(); */

				
			if(self.mavenServiceListener === null)	{
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
					self.mavenServiceListener.mvnCodeValidation(queryString, '#iframePart', function(returnVal){
						//callback(returnVal);
						var validateAgainst = $("#src").find(':selected').val();
						self.getIframeReport(validateAgainst);
					});
				});
			}else{
				self.mavenServiceListener.mvnCodeValidation(queryString, '#iframePart', function(returnVal){
					var validateAgainst = $("#src").find(':selected').val();
					self.getIframeReport(validateAgainst);
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


		/* showHideConsole : function() {
			var self = this;
			alert('img');
			var check = $('#consoleImg').attr('data-flag');
			if (check == "true") {
				alert('if');
				self.openConsole();
			} else {
			alert('else');
				self.closeConsole();
			}
		},
		 */
		onPrgoress : function(clicked) {
			var self = this;
			var check = $(clicked).attr('data-flag');
			/* var value = $('.build_info').width();
			var value1 = $('.build_progress').width(); */
			if(check == "true") {
				/* $('.build_info').animate({width: '97%'},500);
				$('.build_progress').animate({right: -value1},500);
				$('.build_close').animate({right: '0px'},500); */
				self.openConsole();
				$(clicked).attr('data-flag','false');
			} else {
				/* $('.build_info').animate({width: window.innerWidth/1.6},500);
				$('.build_progress').animate({right: '10px'},500);
				$('.build_close').animate({right: value1+10},500); */
				self.closeConsole();
				$(clicked).attr('data-flag','true');
				$(window).resize();
			}
		},
		
		getRequestHeader : function(inputData, action) {
			var self=this, header, username, appId, customerId, projectId;
			var customerId = self.getCustomer();
			customerId = (customerId == "") ? "photon" : customerId;
			
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
				//customerId = appdetails.data.customerIds[0];
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
				//console.info('appDirName = ' , appDirName);
				header.webserviceurl = commonVariables.webserviceurl+"parameter/codeValidationReportTypes?appDirName="+appDirName+"&goal=validate-code";
			}
			
			if(action == "iframereport"){
				var appDirName = this.codequalityAPI.localVal.getSession('appDirName');
				//console.info('appDirName = ' , appDirName + 'inputData = '  + inputData);
				username = self.codequalityAPI.localVal.getSession('username');
				header.webserviceurl = commonVariables.webserviceurl+"parameter/iFrameReport?appDirName="+appDirName+"&validateAgainst="+inputData+"&customerId="+customerId+"&userId="+username;
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
							commonVariables.loadingScreen.removeLoading();
							callback(response);
							//console.info('response = ' , response);
							$('#codeAnalysis').show();
						} else {
							commonVariables.loadingScreen.removeLoading();
							callback({ "status" : "service failure"});
						}
					},

					function(textStatus) {
						commonVariables.loadingScreen.removeLoading();
						var data = $.parseJSON(textStatus);
						$('#content_div').html(data.message);
						$('#codeAnalysis').hide();
					}
				);
			} catch(exception) {
				commonVariables.loadingScreen.removeLoading();
				$('#content_div').html(exception);
				$('#codeAnalysis').hide();
			}
		},

		constructHtml : function(response){
			var self = this;
			//console.info('response test = ' , response);
			if(response.message == "Dependency returned successfully"){
				var typeLi = '';
				var validateAgainst = response.data[0].validateAgainst.key;
				var repTypesData = response.data[0].validateAgainst.value;
				$.each(response.data, function(index, resdata) {
					var innerUl = '';
					if(resdata.options == null){
						typeLi += "<li name='selectType' key="+resdata.validateAgainst.key+" data="+resdata.validateAgainst.value+" style='padding-left:8px;cursor:pointer;'>"+resdata.validateAgainst.value+"</li>";
					}else{
						validateAgainst = resdata.options[0].key;
						 repTypesData = resdata.options[0].value;
						$.each(resdata.options, function(index, optvalue) {
							innerUl += "<li name='selectType' key="+optvalue.key+" data="+optvalue.value+" style='padding-left:8px;cursor:pointer;'>"+optvalue.value+"</li>";
						});
						typeLi += "<li disabled='disabled' key="+resdata.validateAgainst.key+" data="+resdata.validateAgainst.value+" style='padding-left:4px;'>"+resdata.validateAgainst.value+'<ul>'+innerUl+"</ul></li>";
					}
				});
				var dropdownLi = '<ul class="nav"><li id="fat-menu" class="dropdown"><a href="#" id="drop5" role="button" class="dropdown-toggle" data-toggle="dropdown"><b id="repTypes" >'+repTypesData+'</b><b class="caret"></b></a> <div class="dropdown-menu cust_sel code_test_opt" role="menu" aria-labelledby="drop5"> <ul id="reportUl">'+typeLi+'</ul></div></li></ul>';
				$("#codereportTypes").append(dropdownLi);

				self.onProjects();
				self.getIframeReport(validateAgainst);
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
			self.closeConsole();
			try {
				self.codequalityAPI.codequality(self.getRequestHeader(validateAgainst , "iframereport"), 
					function(iframereport) {
						//console.info('iframereport = ' , JSON.stringify(iframereport));
						if(iframereport.data != null){
/* 						var value1 = $('.build_progress').width();
							$('.build_info').animate({width: '97%'},500);
							$('.build_progress').animate({right: -value1},500);
							$('.build_close').animate({right: '0px'},500); */
							
							var iframedata = "<iframe src="+iframereport.data+" style=width:96%;height:450px;></iframe>";
							//$('#content_div').html('<div class="widget-maincontent-div">'+iframedata+'</div>');
							$('#content_div').html(iframedata);
						}else{
							$('#content_div').html(iframereport.message);
						}
					},
					function(textStatus) {
						var data = $.parseJSON(textStatus);
						//console.info('textStatus = ' , textStatus);
						//console.info('data = ' , data);
						$('#content_div').html(data.message);
					}
				);
			} catch(exception) {
				//console.info('exception = ' , exception);
				//console.info('exception = ' , exception);
				$('#content_div').html(exception);
			}	
		},
		
					
			/* getCustomer : function() {
				var selectedcustomer = $("#selectedCustomer").text();
				var customerId = "";
				$.each($("#customer").children(), function(index, value){
					var customerText = $(value).children().text();
					if(customerText === selectedcustomer){
						customerId = $(value).children().attr('id');
					}
				});
				
				return customerId;
			},
		 */
	});

	return Clazz.com.components.codequality.js.listener.CodequalityListener;
});