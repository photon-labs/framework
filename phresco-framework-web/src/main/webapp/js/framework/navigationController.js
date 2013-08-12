define(["framework/base", "framework/animationProvider"], function() {
	Clazz.NAVIGATION_ANIMATION = {
		// push transition in goes to the left, pop going the reverse
		SLIDE_LEFT_FADE_IN : 1,
		FADE_OUT_SLIDE_RIGHT : 2,
		SLIDE_UP_DOWN : 3,
		FADE_IN_FADE_OUT : 4,
		FADE_OUT_QUICK : 7,
		WEBKIT_TRANSITION_RIGHT_LEFT : 8,
		WEBKIT_TRANSITION_LEFT_RIGHT : 9,
		WEBKIT_TRANSITION_FLIP_IN_OUT : 10,
		WEBKIT_TRANSITION_FADE_IN_FLIP_OUT : 11,
		WEBKIT_TRANSITION_SLIDEDOWN_IN_OUT : 12
	};
	
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

	// TODO: ongoing work
	Clazz.NavigationController = Clazz.extend(Clazz.Base, {
		stack: {},
		indexMapping : {},
		browserBack : false,
		
		isNative : null,
		isUsingAnimation : true,
		
		// used in single page single div or multi divs scenario
		mainContainer : null,
		currentIndex: -1,
		loadingActive: false,
		jQueryContainer : null,
		transitionType : null,
		cancelTransitionType : null,

		initialize : function(config) {
			
			this.mainContainer = config.mainContainer;
			this.jQueryContainer = $(this.mainContainer);
			
			if(config.transitionType) {
				this.transitionType = config.transitionType;
				this.cancelTransitionType = config.cancelTransitionType;
			}
			if(config.isNative) {
				this.isNative = config.isNative;
			}
			
			this.setAnimation(this.transitionType);
		},
		
		setAnimation : function(transitionType){
			if(transitionType === Clazz.NAVIGATION_ANIMATION.SLIDE_LEFT_FADE_IN) {
				this.pushAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.FADE_IN;
				this.pushAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.SLIDE_LEFT;
				
				this.popAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.FADE_IN;
				this.popAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.SLIDE_LEFT;
				
			} else if(transitionType === Clazz.NAVIGATION_ANIMATION.FADE_OUT_SLIDE_RIGHT) {
				this.pushAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.SLIDE_RIGHT;
				this.pushAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.FADE_OUT;
				
				this.popAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.SLIDE_RIGHT;
				this.popAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.FADE_OUT;
				
			} else if(transitionType === Clazz.NAVIGATION_ANIMATION.SLIDE_UP_DOWN) {
				this.pushAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.FADE_IN;
				this.pushAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.SLIDE_UP;
				
				this.popAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.SLIDE_DOWN;
				this.popAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.FADE_OUT;
				
			} else if(transitionType === Clazz.NAVIGATION_ANIMATION.FADE_IN_FADE_OUT) {
				this.pushAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.FADE_IN;
				this.pushAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.FADE_OUT;
				
				this.popAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.FADE_IN;
				this.popAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.FADE_OUT;
			
			} else if(transitionType === Clazz.NAVIGATION_ANIMATION.FADE_OUT_QUICK) {
				this.pushAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.FADE_OUT_QUICK;
				this.popAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.FADE_OUT_QUICK;
			
			} else if(transitionType === Clazz.NAVIGATION_ANIMATION.WEBKIT_TRANSITION_RIGHT_LEFT) {
				this.pushAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_LEFT;
				this.pushAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_RIGHT;
				
				this.popAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_LEFT;
				this.popAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_RIGHT;
			
			} else if(transitionType === Clazz.NAVIGATION_ANIMATION.WEBKIT_TRANSITION_LEFT_RIGHT) {
				this.pushAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_RIGHT;
				this.pushAnimationTypeForGoingOut =  Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_LEFT;
				
				this.popAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_RIGHT;
				this.popAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_LEFT;
			
			} else if(transitionType === Clazz.NAVIGATION_ANIMATION.WEBKIT_TRANSITION_FLIP_IN_OUT) {
				this.pushAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_FLIP_IN;
				this.pushAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_FLIP_OUT;
				
				this.popAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_FLIP_IN;
				this.popAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_FLIP_OUT;
			
			} else if(transitionType === Clazz.NAVIGATION_ANIMATION.WEBKIT_TRANSITION_FADE_IN_FLIP_OUT) {
				this.pushAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.FADE_IN;
				this.pushAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_FLIP_OUT;
				
				this.popAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.FADE_IN;
				this.popAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_FLIP_OUT;
			
			} else if(transitionType === Clazz.NAVIGATION_ANIMATION.WEBKIT_TRANSITION_SLIDEDOWN_IN_OUT) {
				this.pushAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_SLIDEDOWN_IN;
				this.pushAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_SLIDEDOWN_OUT;
				
				this.popAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_SLIDEDOWN_IN;
				this.popAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_SLIDEDOWN_OUT;
			}
		},
		
		push : function(view, bCheck, animationtype) {
			var self = this;

			// create top element for pushing
			var newDiv = $("<div></div>");
			newDiv.addClass("widget-maincontent-div");
			
			view.doMore = function(element){

				if(bCheck) {
					var animationProviderMain = new Clazz.AnimationProvider({
						isNative: self.isNative,
						container: newDiv
					});
					
					animationProviderMain.animate(self.pushAnimationTypeForGoingIn, function(container) {
						container.show('slow', function(){
							container.css("z-index", 4);
							if(animationtype !== undefined && animationtype !== null && animationtype === true){
								self.setAnimation(self.transitionType);
							}
							if((commonVariables.ajaxXhr == null || commonVariables.ajaxXhr.readyState == 4) && !commonVariables.continueloading){
								commonVariables.loadingScreen.removeLoading();
							}
						});
					});
					
					var name = view.name ? view.name : "page1", data = {};
					if (history.pushState !== undefined){
						history.pushState({}, "#" + name, "#" + name);
					}
					
					// update browser history
					/* var name = view.name ? view.name : "page1", data = {};
					if(!self.browserBack){
						
						data = {
							name : name,
							view : view
						};
						self.stack[name] = data;
						
						if (history.pushState !== undefined){
							history.pushState({}, "#" + name, "#" + name);
						}
						
					}else{
						delete self.stack[name];
						self.browserBack = false;
					} */ 
				}else{
					if((commonVariables.ajaxXhr == null || commonVariables.ajaxXhr.readyState == 4) && !commonVariables.continueloading){
						commonVariables.loadingScreen.removeLoading();
					}
				}
				self.loadingActive = false;
			};
			
			if(bCheck && $(commonVariables.contentPlaceholder).find('.widget-maincontent-div').length > 0){
				if(animationtype !== undefined && animationtype !== null && animationtype === true){
					self.setAnimation(self.cancelTransitionType);
				}
				
				var animateConten = $(self.jQueryContainer).find('.widget-maincontent-div'),
				animationProviderSub = new Clazz.AnimationProvider({
					isNative: self.isNative,
					container: animateConten
				});
				self.loadingActive = false;
				animationProviderSub.animate(self.pushAnimationTypeForGoingOut, function(container) {
					container.hide('fast');
					$(container).remove();
					
					// render in its container
					$(self.jQueryContainer).append(newDiv);
					
					if(!commonVariables.hideloading && !commonVariables.continueloading){
						commonVariables.loadingScreen.showLoading($(self.jQueryContainer));
					}
					
					if(!self.loadingActive){
						self.loadingActive = true;
						view.render(newDiv);
					}
				});
			}else{
				if(!commonVariables.continueloading)
				if(commonVariables.animation) {
					$(self.jQueryContainer).find('.widget-maincontent-div').remove();
				}
				
				// render in its container
				$(self.jQueryContainer).append(newDiv);

				if(!commonVariables.hideloading){
					commonVariables.loadingScreen.showLoading();
					self.loadingActive = true;
				}

				view.render(newDiv);
			}
		}
	});
   
   return Clazz.NavigationController;
});