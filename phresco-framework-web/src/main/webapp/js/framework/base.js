define(["framework/class"], function() {

	Clazz.Base = Clazz.extend(
		function() {},
		{
			/****
			 * This is called during the object creation 
			 * @param config Parameter for the constructor, typically a {} containing the
			 * necessary initialization parameters
			 */
			initialize : function(config) {
			}
		}
	);

	return Clazz.Base;
});