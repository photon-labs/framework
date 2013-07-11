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
                } else if(type === Clazz.ANIMATION_TYPE.FADE_OUT_QUICK) {
                    this.fadeOutQuick(callback);
                } else if(type === Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_LEFT) {
                    this.webkitTransitionLeft(callback);
                } else if(type === Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_RIGHT) {
                    this.webkitTransitionRight(callback);
                } else if(type === Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_FLIP_IN) {
                    this.webkitTransitionFlipIn(callback);
                } else if(type === Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_FLIP_OUT) {
                    this.webkitTransitionFlipOut(callback);
                } else if(type === Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_SLIDEDOWN_IN) {
                    this.webkitTransitionSlideDownIn(callback);
                } else if(type === Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_SLIDEDOWN_OUT) {
                    this.webkitTransitionSlideDownOut(callback);
                } 
            },
                        
            webkitTransitionLeft : function(callback) {
                var container =  $(this.container);
                if(!this.isNative) {
                    container.addClass("slide in");
                    callback(container);
                } else {
                    this.doNativeTransition({
                        type:"push",
                        direction:"fromRight"
                    });
                    callback(container);
                }
            },
                        
            webkitTransitionRight : function(callback) {
                var container =  $(this.container);
				container.css("left", "-1280px");
                if(!this.isNative) {
                    container.addClass("slide out reverse");
                    callback(container);
                } else {
                    this.doNativeTransition({
                        type:"push",
                        direction:"fromRight"
                    });
                    callback(container);
                }
            },
                        
            webkitTransitionFlipIn : function(callback) {
                var container =  $(this.container);
                if(!this.isNative) {
                    container.addClass("flip in");
                    callback(container);
                } else {
                    this.doNativeTransition({
                        type:"push",
                        direction:"fromRight"
                    });
                    callback(container);
                }
            },
                        
            webkitTransitionFlipOut : function(callback) {
                var container =  $(this.container);
                if(!this.isNative) {
                    container.addClass("flip out");
                    callback(container);
                } else {
                    this.doNativeTransition({
                        type:"push",
                        direction:"fromRight"
                    });
                    callback(container);
                }
            },
                        
            webkitTransitionSlideDownIn : function(callback) {
                var container =  $(this.container);
                if(!this.isNative) {
                    container.addClass("slidedown in");
                    callback(container);
                } else {
                    this.doNativeTransition({
                        type:"push",
                        direction:"fromRight"
                    });
                    callback(container);
                }
            },
                        
            webkitTransitionSlideDownOut : function(callback) {
                var container =  $(this.container);
                if(!this.isNative) {
                    container.addClass("slidedown out");
                    callback(container);
                } else {
                    this.doNativeTransition({
                        type:"push",
                        direction:"fromRight"
                    });
                    callback(container);
                }
            },
                        

            animateLeft : function(callback) {
                var container =  $(this.container);
                if(!this.isNative) {
                    container.animate({
                        left: "-=1660px"
                    }, {
                        duration : 1000, 
                        complete: function() {
                            callback(container);
                        }		
                    });
                } else {
                    this.doNativeTransition({
                        type:"push",
                        direction:"fromRight"
                    });
                    callback(container);
                }
            },
			
            animateRight : function(callback) {
                var container =  $(this.container);
				container.css("left", "-1660px");
				
                if(!this.isNative) {
                    container.animate({
                        left: "+=1660px"
                    }, {
                        duration : 1200, 
                        complete: function() {
                            callback(container);
                        }		
                    });
                } else {
                    this.doNativeTransition({
                        type:"push",
                        direction:"fromLeft"
                    });
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
                    this.doNativeTransition({
                        type:"push",
                        direction:"fromTop"
                    });
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
                    this.doNativeTransition({
                        type:"push",
                        direction:"fromBottom"
                    });
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
                        duration : 500, 
                        complete: function() {
                            callback(container);
                        }		
                    });
                }
                else {
                    this.doNativeTransition({
                        type:"fade"
                    });
                    callback(container);
                }
            }, 
            fadeOut : function(callback) {
                var container =  $(this.container);
                if(!this.isNative) {
                    container.css({
                        opacity: 1
                    });
					
                    container.animate({
                        opacity: 0.0
                    }, {
                        duration : 600, 
                        complete: function() {
                            callback(container);
                        }		
                    });
                }
                else {
                    this.doNativeTransition({
                        type:"fade"
                    });
                    callback(container);
                }
            },
			
            fadeOutQuick : function(callback) {
                var container =  $(this.container);
                if(!this.isNative) {
                    container.css({
                        opacity: 1
                    });
					
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
                    this.doNativeTransition({
                        type:"fade"
                    });
                    callback(container);
                }
            },
			
			
			
			
            doNativeTransition : function(options){
                var callbackFunction = function(param){
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
        FADE_OUT_QUICK : 7,
        WEBKIT_TRANSITION_LEFT : 8,
        WEBKIT_TRANSITION_RIGHT : 9,
        WEBKIT_TRANSITION_FLIP_IN: 10,
        WEBKIT_TRANSITION_FLIP_OUT: 11,
        WEBKIT_TRANSITION_SLIDEDOWN_IN :12,                
        WEBKIT_TRANSITION_SLIDEDOWN_OUT :13                
    };

});