define(["api/api"], function(){
	Clazz.createPackage("com.js.api");

	Clazz.com.js.api.LocalStorageAPI = Clazz.extend(Clazz.com.js.api.API, {
		emptyString : "",
		emptyArray : [],
	});
	
	return Clazz.com.js.api.LocalStorageAPI;
});