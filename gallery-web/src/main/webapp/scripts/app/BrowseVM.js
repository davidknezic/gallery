// Browse view model class

define(['jquery', 'knockout', 'binding/infiniteScroll', 'app/DataAccessObject'], function ($, ko, is, dao) {
	return function () {
		var self = this;
		
		self.images = ko.observableArray();
		
		//dao.images.list(20, undefined, self.images);
		
		self.load = function (reactivate) {
			var lastIndex = self.images().length - 1;
			var last = self.images()[lastIndex];
			
			var lastId = (last !== undefined ? last.id : undefined);
			
			dao.images.list(20, lastId, function (data) {
				$.each(data, function (i, o) {
					o.comment = "Na nu was ist denn das für ein schönes hübsches Bildchen hier? Mal was schönes für die Augen!";
					o.comment += o.comment;
					o.comment += o.comment;
					self.images.push(o);
				});
				reactivate();
			});
			
		}
	};
});
