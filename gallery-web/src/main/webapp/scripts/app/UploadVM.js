// Upload view model class

define(['knockout', 'binding/uploader'], function (ko) {
	return function () {
		var self = this;
		
		self.images = ko.observableArray();
		
		self.uploadImage = function () {
			
		};
		
		self.uploadedImage = function (image) {
			self.images.push(image);
		};
	};
});
