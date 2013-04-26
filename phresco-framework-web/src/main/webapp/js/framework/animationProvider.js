require(["framework/base"], function() {

	Clazz.AnimationProvider = Clazz.extend(
		Clazz.Base,
		{
			isNative : null,	
			container : null,
			
			initialize  : function(config) {
				if(config.container !== null) {
					this.isNative = config.isNative;
					this.container = config.container;
				}
			},
			
			animate : function(type, callback) {
				if(type === Clazz.ANIMATION_TYPE.SLIDE_LEFT) {
					this.animateLeft(callback);
				} else if(type === Clazz.ANIMATION_TYPE.SLIDE_RIGHT) {
					this.animateRight(callback);
				} else if(type === Clazz.ANIMATION_TYPE.SLIDE_UP) {
					this.animateUp(callback);
				} else if(type === Clazz.ANIMATION_TYPE.SLIDE_DOWN) {
					this.animateDown(callback);
				} else if(type === Clazz.ANIMATION_TYPE.FADE_IN) {
					this.fadeIn(callback);
				} else if(type === Clazz.ANIMATION_TYPE.FADE_OUT) {
					this.fadeOut(callback);					
				}else if(type === Clazz.ANIMATION_TYPE.FADE_OUT_QUICK) {
					this.fadeOutQuick(callback);
				}  
			},
			
			animateLeft : function(callback) {
				var container =  $(this.container);
				if(!this.isNative) {
					container.animate({
						  left: "-=1250px"
					}, {
						duration : 400, 
						complete: function() {
							callback(container);
						}		
					});
				} else {
					this.doNativeTransition({type:"push",direction:"fromRight"});
					callback(container);
				}
			},
			
			animateRight : function(callback) {
				var container =  $(this.container);
				if(!this.isNative) {
					container.animate({
						  left: "+=1250px"
					}, {
						duration : 1000, 
						complete: function() {
							callback(container);
						}		
					});
				} else {
					this.doNativeTransition({type:"push",direction:"fromLeft"});
					callback(container);
				}
			},
			
			animateUp : function(callback) {
				var container =  $(this.container);
				if(!this.isNative) {
					container.animate({
						  top: "-=1250px"
					}, {
						duration : 1000, 
						complete: function() {
							callback(container);
						}		
					});
				} else {
					this.doNativeTransition({type:"push",direction:"fromTop"});
					callback(container);
				}
			},
			
			animateDown : function(callback) {
				var container =  $(this.container);
				if(!this.isNative) {
					container.animate({
						  top: "+=1250px"
					}, {
						duration : 1000, 
						complete: function() {
							callback(container);
						}		
					});
				} else {
					this.doNativeTransition({type:"push",direction:"fromBottom"});
					callback(container);
				}
			},
			
			fadeIn : function(callback) {
				var container =  $(this.container);
				if(!this.isNative) {
					container.css({ opacity: 0 });
					
					container.animate({
						  opacity: 1
					}, {
						duration : 1800, 
						complete: function() {
							callback(container);
						}		
					});
				}
				else {
					this.doNativeTransition({type:"fade"});
					callback(container);
				}
			}, 
			
			fadeOut : function(callback) {
				var container =  $(this.container);
				if(!this.isNative) {
					container.css({ opacity: 1 });
					
					container.animate({
						opacity: 0
					}, {
						duration : 900, 
						complete: function() {
							callback(container);
						}		
					});
				}
				else {
					this.doNativeTransition({type:"fade"});
					callback(container);
				}
			},
			
			fadeOutQuick : function(callback) {
				var container =  $(this.container);
				if(!this.isNative) {
					container.css({ opacity: 1 });
					
					container.animate({
						opacity: 1
					}, {
						duration : 0, 
						complete: function() {
							callback(container);
						}		
					});
				}
				else {
					this.doNativeTransition({type:"fade"});
					callback(container);
				}
			},
			
			
			
			
			doNativeTransition : function(options){
				var callbackFunction = function(param) {
					console.log(param);
				};
				var className = "Transition";
				var functionName = "transition";
				cordova.exec(callbackFunction,callbackFunction,className,functionName,[options]);
			}
		}
	);

	Clazz.ANIMATION_TYPE = {
		SLIDE_LEFT : 1,
		SLIDE_RIGHT : 2,
		SLIDE_UP : 3,
		SLIDE_DOWN : 4,
		FADE_IN : 5,
		FADE_OUT : 6,
		FADE_OUT_QUICK : 7
	};

});