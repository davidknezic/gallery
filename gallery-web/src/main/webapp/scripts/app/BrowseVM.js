// Browse view model class

define(['jquery', 'knockout', 'binding/infiniteScroll', 'app/DataAccessObject'], function ($, ko, is, dao) {
	return function () {
		var self = this;
		
		self.images = ko.observableArray();
		
		self.load = function (reactivate) {
			var lastIndex = self.images().length - 1;
			var last = self.images()[lastIndex];
			
			var lastId = (last !== undefined ? last.id : undefined);
			
			dao.images.list(20, lastId, function (data) {
				$.each(data, function (i, o) {
					self.images.push(o);
				});
				reactivate();
			});
		};
	};
});
