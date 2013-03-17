define(['jquery', 'knockout', 'waypoints'], function ($, ko) {
	ko.bindingHandlers['infiniteScroll'] = {
		init: function(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
	        var options = valueAccessor();
	        
			var $element = $(element);
	        
	        $element.waypoint({
	        	offset: 'bottom-in-view',
	        	handler: function (direction) {
	        		console.log('triggered! ' + direction);
	        		
	        		$element.waypoint('disable');
		        	
	        		options(function () {
		        		$element.waypoint('enable');
		        	});
	        	}
	        });
	    }
	};
});
