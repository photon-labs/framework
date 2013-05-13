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

	// TODO: ongoing work
	Clazz.NavigationController = Clazz.extend(
		Clazz.Base,
		{
			stack: [],
			indexMapping : {},

			isNative : null,
			isUsingAnimation : true,
			
			// used in single page single div or multi divs scenario
			mainContainer : null,
			currentIndex: -1,
			
			jQueryContainer : null,
			transitionType : null,

			initialize : function(config) {
				
				this.mainContainer = config.mainContainer;
				this.jQueryContainer = $(this.mainContainer);
				
				if(config.transitionType) {
					this.transitionType = config.transitionType;
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
				
				} else if(transitionType == Clazz.NAVIGATION_ANIMATION.WEBKIT_TRANSITION_RIGHT_LEFT) {
					this.pushAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_LEFT;
					this.pushAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_RIGHT;
					
					this.popAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_LEFT;
					this.popAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_RIGHT;
				
				} else if(transitionType == Clazz.NAVIGATION_ANIMATION.WEBKIT_TRANSITION_LEFT_RIGHT) {
					this.pushAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_RIGHT;
					this.pushAnimationTypeForGoingOut =  Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_LEFT;
					
					this.popAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_RIGHT;
					this.popAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_LEFT;
				
				} else if(transitionType == Clazz.NAVIGATION_ANIMATION.WEBKIT_TRANSITION_FLIP_IN_OUT) {
					this.pushAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_FLIP_IN;
					this.pushAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_FLIP_OUT;
					
					this.popAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_FLIP_IN;
					this.popAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_FLIP_OUT;
				
				} 
				
				
				else if(transitionType == Clazz.NAVIGATION_ANIMATION.WEBKIT_TRANSITION_FADE_IN_FLIP_OUT) {
					this.pushAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.FADE_IN;
					this.pushAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_FLIP_OUT;
					
					this.popAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.FADE_IN;
					this.popAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_FLIP_OUT;
				
				} 
				
				
				else if(transitionType == Clazz.NAVIGATION_ANIMATION.WEBKIT_TRANSITION_SLIDEDOWN_IN_OUT) {
					this.pushAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_SLIDEDOWN_IN;
					this.pushAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_SLIDEDOWN_OUT;
					
					this.popAnimationTypeForGoingIn = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_SLIDEDOWN_IN;
					this.popAnimationTypeForGoingOut = Clazz.ANIMATION_TYPE.WEBKIT_TRANSITION_SLIDEDOWN_OUT;
				}
			},
			
			pop : function(goBack) {
				var self = this;
				var page = this.stack.pop();
				
				if(goBack === null) {
					goBack = true;
				}
				
				if(page !== undefined && page !== null){
					var animationProviderMain = new Clazz.AnimationProvider( {
						isNative: self.isNative,
						container: page.element
					});
					
					if(!self.isNative){
						animationProviderMain.animate(this.popAnimationTypeForGoingOut, function(container) {
							container.remove();
							page = null;
							delete page;
						});
					} else {
						page.element.remove();
						page = null;
						delete page;
					}
					
					if(this.stack.length > 0) {
						var topPage = this.stack[this.stack.length-1];
						topPage.element.show();
						
						var animationProviderSub = new Clazz.AnimationProvider( {
							isNative: self.isNative,
							container: topPage.element
						});
							
						// call in transition on sub
						animationProviderSub.animate(this.popAnimationTypeForGoingIn, function(container) {
							container.css("z-index", 4);
							
							if(goBack) {
								history.back();
							}
							self.currentIndex = self.stack.length - 1;
						});
					}
				}
			},
			
			push : function(view, bCheck) {
				var self = this;
				
				// create top element for pushing
				var newDiv = $("<div></div>");
				
				// add absolute positioning
				newDiv.addClass("widget-maincontent-div");
				
				$(self.jQueryContainer).append(newDiv);

				if(bCheck) {
					view.doMore = function(element) {
						var animationProviderMain = new Clazz.AnimationProvider( {
							isNative: self.isNative,
							container: newDiv
						});
						
						animationProviderMain.animate(self.pushAnimationTypeForGoingIn, function(container) {
							container.show();
							container.css("z-index", 4);
							self.removeClasses(container);
						});

						if(self.stack.length > 0) {
							var topPage = self.stack[self.stack.length-1];
							
							// call onPause to save the state of this page
							if(topPage.view.onPause) {
								topPage.view.onPause();
							}
							
							var animationProviderSub = new Clazz.AnimationProvider( {
								isNative: self.isNative,
								container: topPage.element
							});
						
							animationProviderSub.animate(self.pushAnimationTypeForGoingOut, function(container) {
								container.hide();
								container.css("z-index", 3);
								$(container).remove();
								self.removeClasses(container);
							});
						}
						
						// update browser history
						var title = "#page" + self.stack.length;
						var name = view.name ? "#"  + view.name : title;
						// push into the stack
						var data = {
							view : view,
							element : newDiv
						};
						
						self.stack.push(data);
						self.currentIndex = self.stack.length - 1;
						self.indexMapping[name] = self.stack.length - 1;
						history.pushState({}, name, name);
					};
				}
				
				// render in its default container
				view.render(newDiv);
			},
			
			getView: function(locationHash) {
				
				var index = this.indexMapping[locationHash];
					
				if(index !== null) {
					var page = this.stack[index];
					
					if(this.currentIndex > index) {
						for(var i = this.currentIndex; i > index; i--) {
							var current = this.stack[i];
							if(current !== undefined && current !== null){
								// delete the mapping
								// update browser history
								var title = "#page" + i;
								var name = current.view.name ? "#"  + current.view.name : title;
								delete this.indexMapping[name];
								this.pop(false);
							}
						}
						this.currentIndex = index;
					} 
					
					if(page.view.onResume) {
						// call on resume on the current page
						page.view.onResume();
					}
				} 
			},
			
			removeClasses : function(container){
				setTimeout(function(){
					$(container).css("left", "0px");
					$(container).removeClass("slide in");
					$(container).removeClass("slide out reverse");
					$(container).removeClass("flip in");
					$(container).removeClass("flip out");
					$(container).removeClass("slidedown in");
					$(container).removeClass("slidedown out");
				},800);
			}
		}
	);
   
   return Clazz.NavigationController;
});