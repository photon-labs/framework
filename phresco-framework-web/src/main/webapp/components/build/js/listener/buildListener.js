define(["framework/widget"], function() {

	Clazz.createPackage("com.components.build.js.listener");

	Clazz.com.components.build.js.listener.BuildListener = Clazz.extend(Clazz.Widget, {
				
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
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
				$('.build_info').animate({width: window.innerWidth/2.5},500);
				$('.build_progress').animate({right: '10px'},500);
				$('.build_close').animate({right: value1+10},500);
				$(clicked).attr('data-flag','true');
				$(window).resize();
			}
		}
		
	});

	return Clazz.com.components.build.js.listener.BuildListener;
});