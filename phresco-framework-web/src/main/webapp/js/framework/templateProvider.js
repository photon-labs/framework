define(["framework/class"], function() {

	//TODO: MOVE this to different file and load dynamically via require.js
	Clazz.AngularProvider = {
			compiledCache : {},

			merge : function(templateUrl, data, callbackFunction) {
				var self = this;
				if(this.compiledCache[templateUrl] != null) {
					var compiledTemplate = this.compiledCache[templateUrl].compiledTemplate;
					var scope =  this.compiledCache[templateUrl].scope;
					scope.data = data;
					var element = compiledTemplate(scope);
					scope.$digest();
					callbackFunction(element);
				} else {
					$.get(templateUrl).success(function(template, status, response) {
						// get ng service
						var injector = angular.injector(['ng']);

						injector.invoke(function($rootScope, $compile, $document){
							var compiledTemplate = $compile(response.responseText);
							var scope = $rootScope.$new();
							self.compiledCache[templateUrl] = {"compiledTemplate" : compiledTemplate, "scope" : scope };

							scope.data = data;
							var element = compiledTemplate(scope);
							scope.$digest();
							callbackFunction(element);
						});
					}).error(function(error) {
						console.log("error "+ error.status +": "+ error.statusText);
					});
				}
			}
	};

	//TODO: MOVE this to different file and load dynamically via require.js
	Clazz.DustProvider = {
			compiledCache : {},

			merge : function(templateUrl, data, callbackFunction) {
				var lastPath = templateUrl.substring(templateUrl.lastIndexOf("/")+1);
				$.get(templateUrl).success(function(template, status, response) {
					var compiledTemplate = dust.compile(response.responseText, lastPath);
					dust.loadSource(compiledTemplate);

					dust.render(lastPath, data ,function(error,output){
						callbackFunction(output);
					});
				}).error(function(error) {
					console.log("error "+ error.status +": "+ error.statusText);
				});
			}
	};

	//TODO: MOVE this to different file and load dynamically via require.js
	Clazz.HandleBarProvider = {
			compiledCache : {},

			merge : function(templateUrl, data, callbackFunction) {
				$.get(templateUrl).success(function(template, status, response) {
					require(["lib/handlebars-1.0.0"], function() {
						var compiledTemplate = Handlebars.compile(typeof template == "object" ? response.responseText : template);
						var element = compiledTemplate(data);
						callbackFunction(element);
					});
				}).error(function(error) {
					console.log("error "+ error.status +": "+ error.statusText);
				});
			}
	};

	//TODO: MOVE this to different file and load dynamically via require.js
	Clazz.PlateProvider = {
			compiledCache : {},

			merge : function(templateUrl, data, callbackFunction) {
				$.get(templateUrl).success(function(template, status, response) {
					var mapping = Plates.Map();

					var allElement = $(response.responseText);
					var allWithDataAttribute = $('[data-text]', allElement);
					var allWithDataHtmlAttribute = $('[data-html]', allElement);

					// get every data and htmlData in the template
					for(var i=0;i<allWithDataAttribute.length;i++) {
						var element = $(allWithDataAttribute[i]);
						var keyValue = element.attr("data-text");
						mapping.where('data-text').is(keyValue).to(keyValue);
					}

					// get every data and htmlData in the template
					for(var i=0;i<allWithDataHtmlAttribute.length;i++) {
						var element = $(allWithDataHtmlAttribute[i]);
						var keyValue = element.attr("data-html");
						mapping.where('data-html').is(keyValue).append(data[keyValue]);
					}

					var element = Plates.bind(response.responseText, data, mapping);
					callbackFunction(element);
				}).error(function(error) {
					console.log("error "+ error.status +": "+ error.statusText);
				});
			}
	};

	Clazz.UnderscoreProvider = {
			compiledCache : {},

			merge : function(templateUrl, data, callbackFunction) {
				$.get(templateUrl).success(function(template, status, response) {
					var compiledTemplate = _.template(response.responseText, data);
					callbackFunction(compiledTemplate);
				}).error(function(error) {
					console.log("error "+ error.status +": "+ error.statusText);
				});
			}
	};

	Clazz.RivetProvider = {
			compiledCache : {},

			merge : function(templateUrl, data, callbackFunction) {
				$.get(templateUrl).success(function(template, status, response) {
					var allElement = $(response.responseText);
					var rivetsView = rivets.bind(allElement, {data:data});
					var element = rivetsView.els;
					callbackFunction(element);
				}).error(function(error) {
					console.log("error "+ error.status +": "+ error.statusText);
				});
			}
	};

	TemplateProvider = {
			DUST: Clazz.DustProvider,
			UNDERSCORE : Clazz.UnderscoreProvider,
			HANDLE_BAR : Clazz.HandleBarProvider,
			ANGULAR : Clazz.AngularProvider,
			RIVET : Clazz.RivetProvider,
			PLATE : Clazz.PlateProvider
	};

	Clazz.TemplateProvider = Clazz.extend(
			Clazz.Base,
			{
				templateEngine : null, 

				initialize : function(config) {
					if(config.templateEngine != null) {
						this.templateEngine = config.templateEngine;
					} else {
						this.templateEngine = TemplateProvider.DUST;
					}
				},

				merge : function(templateUrl, data, callbackFunction) {
					var self = this;
					self.templateEngine.merge(templateUrl, data, callbackFunction);
					/* $.get(templateUrl).success(function(template, status, response) {
						self.templateEngine.merge(templateUrl, data, callbackFunction);
					}).error(function(error) {
						console.log("error "+ error.status +": "+ error.statusText);
					}); */ 
				}
			}
	);
	return Clazz.TemplateProvider;
});