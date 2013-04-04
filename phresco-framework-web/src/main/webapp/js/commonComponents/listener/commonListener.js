define(["framework/base"], function(){	

	Clazz.createPackage("com.js.widget.listener");

	Clazz.com.js.widget.listener.CommonListener = Clazz.extend(Clazz.Base, {
		
		isClickActionComplete : true,
		
		/***
		 * Change page based on getObjectPage and urlAddress
		 */
		changePage : function(getObjectPage, urlAddress){
			var self = this;
			
			if(urlAddress){
				require(urlAddress, function(){
					Clazz.navigationController.push(getObjectPage());
				});
			}
			else{
				Clazz.navigationController.push(getObjectPage());
			}
		},
		
		/***
		 * Render view based on view parameter
		 */
		render : function(view){
			if(view){
				view.render();
			}
		},
		
		getIsClickActionComplete : function(){
			return this.isClickActionComplete;
		},
		
		setIsClickActionComplete : function(isClickActionComplete){
			this.isClickActionComplete = isClickActionComplete;
		},

		/***
		 * Show alert based on text parameter
		 */
		showAlert : function(text){
			//prevent fastclick
			setTimeout(function() {
				alert(text);	
			}, 200);
		}
	});
	
	return Clazz.com.js.widget.listener.CommonListener;
});	