define(["api/api"], function(){
	Clazz.createPackage("com.js.api");

	Clazz.com.js.api.LocalStorageAPI = Clazz.extend(Clazz.com.js.api.API, {
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
		}
	});
	
	return Clazz.com.js.api.LocalStorageAPI;
});