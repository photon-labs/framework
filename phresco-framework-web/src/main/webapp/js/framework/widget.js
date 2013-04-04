define(["framework/base"], function() {

	/***
	 * A class to deal with custom placement of the objects. Note that this rely on jQuery
	 * to function correctly though other framework can be used along with it
	 */
	Clazz.Widget = Clazz.extend(
		Clazz.Base,
		{
			/***
			 * A call to render the UI fragment implemented in renderUI method
			 * @param whereToRender A placeholder to hold the fragment, this would typically
			 * be a div tag or other valid HTML element. 
			 */
			render : function(whereToRender) {
				// call renderUI to get the element
				var fragment = this.renderUI();
				
				// get the placement using jQuery
				// and embed it 
				$(whereToRender).html(fragment);
				
				// bind all elements once rendered to provide events
				this.bindUI();
			},
			
			/**
			 * Function that every subclass needs to override to provide
			 * a fragment that render will call
			 */
			renderUI : function() {
			},
			
			/****
			 * Bind the UI elements with appropriate events
			 */
			bindUI : function() {
			},
		 
			/****
			 * If NavigationController is used, this function is called automatically
			 * when a new view is pushed to the stack and this object is about to dissapear
			 * from the view
			 */
			onPause : function() {
			},
			
			/***
			 * If NavigationController is used, this function is called automatically
			 * when a view is popped from the stack and this object become visible again
			 */
			onResume : function() {
			}
		}
	);

	return Clazz.Widget;
});