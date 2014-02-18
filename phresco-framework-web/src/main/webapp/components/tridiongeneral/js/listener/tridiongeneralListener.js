define([], function() {

	Clazz.createPackage("com.components.tridiongeneral.js.listener");

	Clazz.com.components.tridiongeneral.js.listener.tridiongeneralListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
		},
		
		parentsearch : function (txtSearch, divId){
			var self = this;
			if (txtSearch !== "" && txtSearch !== undefined) {
				$("#"+divId+" li").hide();//To hide all the ul
				var hasRecord = false;				
				var i=0;
				$("#"+divId+" li").each(function() {//To search for the txtSearch and search option thru all td
					var tdText = $(this).text();
					if (tdText.match(txtSearch)) {
						$(this).show();
						hasRecord = true;
						i++;
					}
				});
				if (hasRecord === false) {
					if(divId === "moduleContent"){
						//$("#norecord1").show();
					}			
				} else {
					//$("#norecord1").hide();		
				}
			}
			else {	
				$("#"+divId+" li").show();
				//$("#norecord1").hide();
			}			
		},

		
		getRequestHeader : function(data , action) {
			var self=this, header, userId, appDirName;
			userId = commonVariables.api.localVal.getSession('username');
			appDirName = commonVariables.api.localVal.getSession("appDirName");
			
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: '',
				data: ''
			};
			if(action === 'getAllParents'){
				header.webserviceurl = commonVariables.webserviceurl+"tridion/getPublicationsList?appDirName="+appDirName+"&type=publicationList";
			}				
			
			if(action === 'saveConfig'){
				header.requestMethod ="POST";
				header.requestPostBody = data;
				header.webserviceurl = commonVariables.webserviceurl+"tridion/saveConfig?appDirName="+appDirName;
			}
			
			if(action === 'readConfig'){
				header.webserviceurl = commonVariables.webserviceurl+"tridion/readConfig?appDirName="+appDirName;
			}
			
			if(action === 'createPublication'){
				header.webserviceurl = commonVariables.webserviceurl+"tridion/createPublication?appDirName="+appDirName;
			}	
	/* 		if(action === 'editApplication'){
				var displayName="", userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
				if (userInfo !== null) {
					displayName = userInfo.displayName;
				}
				moduleParam = self.isBlank($('.moduleName').val()) ? "" : '&rootModule='+$('.rootModule').val()+'&moduleName='+$('.moduleName').val();
				oldAppDirName = self.isBlank($('.moduleName').val()) ? oldAppDirName : $('.moduleName').val();
				header.requestMethod ="PUT";
				header.requestPostBody = appDirName;
				header.webserviceurl = commonVariables.webserviceurl+"project/updateApplication?userId="+userId+"&oldAppDirName="+oldAppDirName+"&customerId=photon&displayName="+displayName+moduleParam;
			}
			if (action === 'getApplicableOptions') {
				header.requestMethod ="GET";
				header.webserviceurl = commonVariables.webserviceurl+"util/techOptions?userId="+userId+"&techId="+techId;
			} */
			return header;
		},
		
				
		getData : function(header, callback) {
			var self = this;
			try {
				//commonVariables.loadingScreen.showLoading();
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							callback(response);
							//commonVariables.loadingScreen.removeLoading();
							//commonVariables.api.showError(response.responseCode ,"success", true);
						} else {
							commonVariables.api.showError(response.responseCode ,"error", true, false, true);
						}
					},

					function(textStatus) {
						//commonVariables.loadingScreen.removeLoading();
						commonVariables.api.showError("serviceerror" ,"error", true);
					}
				);
			} catch(exception) {
				//commonVariables.loadingScreen.removeLoading();
			}
		},

		getAllParents : function(action){
			var self = this;
			var availableParents = "";
			var assignedParents = "";
			var publicationType = $("#repTypes").html();
			$("#sortable1 li").remove();
			$("#sortable2 li").remove();
			//if(commonVariables.api.localVal.getJson("availableParents") === "" || commonVariables.api.localVal.getJson("availableParents") === null || commonVariables.api.localVal.getJson("availableParents") === undefined){
				self.getData(self.getRequestHeader('', action), function(response) {
					if(response.data !== null && response.data.length !== 0) {
						if(response.responseCode === "PHRSR1001"){
							commonVariables.api.localVal.setJson('availableParents', response.data);
							var  readConfigData = commonVariables.api.localVal.getJson('readConfigData');
							$.each(response.data, function(index, value){
								 var allparent = $.trim(value.item);
								 var parentValue = true;
								  $.each(readConfigData, function(index, parantvalue){
									if(parantvalue.publicationType === publicationType){
										$.each(parantvalue.parentPublications, function(indexval, parent){
											if(allparent === parent.name){
												parentValue = false;
											 }	
										});
									}
								 });
								
								if(parentValue){
									availableParents += '<li class="ui-state-default" style="padding-left:10px;">'+ allparent +'</li>';
								} else {
									assignedParents += '<li class="ui-state-default" style="padding-left:10px;">'+ allparent +'</li>';
								}
							});
						}
						$("#sortable1").append(availableParents);
						$("#sortable2").append(assignedParents);
					}
				});
		/* 	} else {
				$.each(commonVariables.api.localVal.getJson("availableParents"), function(index, value){
					returnVal += '<li class="ui-state-default">'+ $.trim(value.item) +'</li>';
				});
				$("#sortable1").append(returnVal);
			} */
		},
		
		savePublication : function(){
			var self = this;
			if(!self.validation()) {
				 var publicationInfo = {};
				 var parentPublications = [];
				 var publicationName = $("input[name='publicationName']").val();
				 var publicationKey = $("input[name='publicationKey']").val();
				 var publicationPath = $("input[name='publicationPath']").val();
				 var publicationUrl = $("input[name='publicationUrl']").val();
				 var imagePath = $("input[name='imagePath']").val();
				 var imageUrl = $("input[name='imageUrl']").val();
				 var publicationType = $("#repTypes").html();
				
				if($(".parentpublicationdiv").attr("key") === "displayed"){
					var selectedJobLiObj = $("#sortable2 li");
					$(selectedJobLiObj).each(function(index, value) {
						var parentJson = {};
						parentJson.priority = index;
						parentJson.name = $(this).text();
						parentPublications.push(parentJson);
					});
				} else {
					var readConfigData = commonVariables.api.localVal.getJson('readConfigData');
					$.each(readConfigData, function(index, value){
						if(value.publicationType === publicationType){
							parentPublications = value.parentPublications
						}
					});
					
				}
				 publicationInfo.environment = null;
				 publicationInfo.publicationName = publicationName;
				 publicationInfo.publicationKey = publicationKey;
				 publicationInfo.publicationType = publicationType;
				 publicationInfo.publicationPath = publicationPath;
				 publicationInfo.publicationUrl = publicationUrl;
				 publicationInfo.imagePath = imagePath;
				 publicationInfo.imageUrl = imageUrl;
				 publicationInfo.parentPublications = parentPublications;
				 publicationInfo.publication = null;
				//&& (publicationType === 'Website' || publicationType === 'Content') 
				//console.info('parentPublications = ' , parentPublications);
				 if(parentPublications.length === 0 ){
					commonVariables.api.showError("PHRTR1002" ,"error", true, false, true);
					return false;
				 }

 				self.getData(self.getRequestHeader(JSON.stringify(publicationInfo), "saveConfig"), function(response) {
					if(response.data !== null && response.data.length !== 0) {
						if(response.responseCode === "PHRSR1001"){
							commonVariables.api.showError("PHRTR0001" ,"success", true, false, true);
							self.readConfigData(publicationType , 'readConfig');
						} else {
							commonVariables.api.showError("PHRTR1001" ,"error", true, false, true);
						}
					}
				});				 
 			}	
		},
		validation : function() {
		
			 var flag1=0,flag2=0,flag3=0;
			 var publicationName = $("input[name='publicationName']").val();
			 var publicationKey = $("input[name='publicationKey']").val();
			 var publicationPath = $("input[name='publicationPath']").val();
			 var publicationUrl = $("input[name='publicationUrl']").val();
			 var imagePath = $("input[name='imagePath']").val();
			 var imageUrl = $("input[name='imageUrl']").val();
			 self.hasError = false;

			 if(publicationName === ""){
					$("input[name='publicationName']").focus();
					$("input[name='publicationName']").attr('placeholder','Enter Name');
					$("input[name='publicationName']").addClass("errormessage");
					$("input[name='publicationName']").bind('keypress', function() {
						$(this).removeClass("errormessage");
					});
					self.hasError = true;
			   } else if(publicationKey === ""){
					$("input[name='publicationKey']").focus();
					$("input[name='publicationKey']").attr('placeholder','Enter Publication Key');
					$("input[name='publicationKey']").addClass("errormessage");
					$("input[name='publicationKey']").bind('keypress', function() {
						$(this).removeClass("errormessage");
					});
					self.hasError = true;
			   } else if(publicationPath === ""){
					$("input[name='publicationPath']").focus();
					$("input[name='publicationPath']").attr('placeholder','Enter Publication Path');
					$("input[name='publicationPath']").addClass("errormessage");
					$("input[name='publicationPath']").bind('keypress', function() {
						$(this).removeClass("errormessage");
					});
					self.hasError = true;
			   } else if(publicationUrl === ""){
					$("input[name='publicationUrl']").focus();
					$("input[name='publicationUrl']").attr('placeholder','Enter Publication Url');
					$("input[name='publicationUrl']").addClass("errormessage");
					$("input[name='publicationUrl']").bind('keypress', function() {
						$(this).removeClass("errormessage");
					});
					self.hasError = true;
			   } else if(imagePath === ""){
					$("input[name='imagePath']").focus();
					$("input[name='imagePath']").attr('placeholder','Enter Images Path');
					$("input[name='imagePath']").addClass("errormessage");
					$("input[name='imagePath']").bind('keypress', function() {
						$(this).removeClass("errormessage");
					});
					self.hasError = true;
			   } else if(imageUrl === ""){
					$("input[name='imageUrl']").focus();
					$("input[name='imageUrl']").attr('placeholder','Enter Images Url');
					$("input[name='imageUrl']").addClass("errormessage");
					$("input[name='imageUrl']").bind('keypress', function() {
						$(this).removeClass("errormessage");
					});
					self.hasError = true;
			   }
			  return self.hasError;	
		},
		
		readConfigData : function(publicationType , action){
			var self = this;
			//var publicationType = $("#classificationId").text();
			self.getData(self.getRequestHeader('', action), function(response) {
				if(response.data !== null && response.data.length !== 0) {
					if(response.responseCode === "PHRSR1001"){
						commonVariables.api.localVal.setJson('readConfigData', response.data);
							$.each(response.data, function(index, value){
								//returnVal += '<li class="ui-state-default" style="padding-left:10px;">'+ value.item +'</li>';
								var parentPublications = '';
								if(value.publicationType === publicationType){
									$("input[name='publicationName']").val(value.publicationName);
									$("input[name='publicationKey']").val(value.publicationKey);
									$("input[name='publicationPath']").val(value.publicationPath);
									$("input[name='publicationUrl']").val(value.publicationUrl);
									$("input[name='imagePath']").val(value.imagePath);
									$("input[name='imageUrl']").val(value.imageUrl);
									$.each(value.parentPublications, function(indexval, parent){
										parentPublications += parent.name + ", ";
									});
									$("#parentPublications").text(parentPublications);
								}				
							});
					}
				}
			});
		},
		
		submitPublication : function (){
			var self = this;
			self.getData(self.getRequestHeader('', "createPublication"), function(response) {
				/* if(response.responseCode === "PHRSR10007"){
					commonVariables.api.showError(response.responseCode ,"error", true, false, true);
				} */
			});	
		}
	});
	return Clazz.com.components.tridiongeneral.js.listener.tridiongeneralListener;
});