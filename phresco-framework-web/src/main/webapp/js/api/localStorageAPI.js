define(["framework/base"], function(){
	Clazz.createPackage("com.js.api");

	Clazz.com.js.api.LocalStorageAPI = Clazz.extend(Clazz.Base, {
		emptyString : "",
		emptyArray : [],
		
		getSession : function(key){
			if (key !== '') {
				return localStorage.getItem(key);
			}
		},
		
		setSession : function(key, value){
			if(key !== '') {
				localStorage.setItem(key,value);
			}
		},
		
		deleteSession : function(key){
			if(key !== '') {
				localStorage.removeItem(key);
			}
		},
		
		clearSession : function(){
			localStorage.clear();
		},
		
		getJson : function(key){
			if (key !== '') {
				return JSON.parse(localStorage.getItem(key));
			}
		},
		
		setJson : function(key, value){
			if(key !== '' && value !== '' && value !== undefined) {
				localStorage.setItem(key, JSON.stringify(value));
			}
		},
		
		getProjectInfo : function(){
			if ($('.hProjectId').val() !== undefined && $('.hProjectId').val() !== null &&
			    $('.hProjectId').val() !== '') {
				return JSON.parse(localStorage.getItem($('.hProjectId').val()));
			}else{
				return JSON.parse('appdetails');
			}
		},
		
		setProjectInfo : function(value){
			if ($('.hProjectId').val() !== undefined && $('.hProjectId').val() !== null &&
			    $('.hProjectId').val() !== '') {
				localStorage.setItem($('.hProjectId').val(), JSON.stringify(value));
			}else{
				localStorage.setItem('appdetails', JSON.stringify(value));
			}
		}
	});
	
	return Clazz.com.js.api.LocalStorageAPI;
});