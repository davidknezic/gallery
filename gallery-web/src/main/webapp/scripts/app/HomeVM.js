// home view model class

define(['knockout', 'app/DataAccessObject', 'bootstrap', 'binding/viewer'], function (ko, dao) {
	return function () {
		var self = this;
		
		self.images = ko.observableArray();
		
		dao.images.random(5, self.images);
	};
});
