define(["jquery"], function($) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { runTests: function (configData) {
		module("login.js;login");
		asyncTest("login Test", function() {
			$(document).ready(function(){
				var url = "bestbuy/j_spring_security_check"; // the script where you handle the form input.
				var data = {};
				data.j_username = "a1010742";
				data.password = "password";
				
				$.ajax({
					type: "POST",
					url: url,
					data: 'j_username=a1010742&j_password=password', // serializes the form's elements.
					success: function(sucessdata) {
						equal(sucessdata.indexOf('basepage:widget'), "2200", "Success case for Login");
						start();					
						
						$('<script src="src/lib/bootstrap.min.js" type="text/javascript"></script>').appendTo(document.head);
						
						require(["synonymsTest"], function(synonymsTest) {
							
						});
					}
				});

			});
		});
	}};
	
});
