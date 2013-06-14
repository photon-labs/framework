define(["jquery", "features/features", "framework/navigationController", "framework/widgetWithTemplate", , "features/listener/featuresListener"], function($, Features) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	 var fearures = new Features();
	return { runTests: function (configData) {
		module("Features.js;Features");
		asyncTest("Features Test", function() {
		
			mockFeaturesList = mockFunction();
			when(mockFeaturesList)(anything()).then(function(arg) {
				
				var fearuresListresponse = {"response":null,"message":"Application Features listed successfully","exception":null,"data":[{"artifactId":"mod_pagination_1.0","classifier":null,"packaging":"zip","groupId":"modules.tech-php.files","versions":[{"scope":null,"artifactGroupId":"mod_pagination","dependencyIds":null,"appliesTo":[{"required":false,"techId":"tech-php"}],"downloadURL":"http://172.16.17.226:8080/repository/content/groups/public//modules/tech-php/files/mod_pagination_1.0/1.0/mod_pagination_1.0-1.0.zip","used":false,"fileSize":0,"version":"1.0","creationDate":1348896224750,"helpText":null,"system":false,"name":"Pagination","id":"mod_pagination_tech_php1.0","displayName":null,"description":null,"status":null}],"licenseId":"dda55a98-658b-4509-a47a-db00cec98e95","imageURL":null,"appliesTo":[{"core":false,"techId":"tech-php"}],"type":"FEATURE","used":false,"customerIds":["photon"],"creationDate":1348896224828,"helpText":"As your database grows, showing all the results of a query on a single page is no longer practical","system":true,"name":"Pagination","id":"mod_pagination","displayName":"Pagination","description":"As your database grows, showing all the results of a query on a single page is no longer practical. This is where pagination comes in handy. You can display your results over a number of pages, each linked to the next, to allow your users to browse your content in bite sized pieces. ","status":null}]};
				var collection = {};
				collection.featureslist = fearuresListresponse.data;
				fearures.renderTemplate(collection, commonVariables.contentPlaceholder)
				
			});
			fearures.getFeatures = mockFeaturesList;
			fearures.loadPage();
			setTimeout(function() {
				start();
				console.info("$(commonVariables.contentPlaceholder)", $(commonVariables.contentPlaceholder));
				equal($(commonVariables.contentPlaceholder).find("li[type = FEATURE]").attr("name"), "Pagination", "Feature list service Tested");
			}, 1500);
		});

	}};
	
});
