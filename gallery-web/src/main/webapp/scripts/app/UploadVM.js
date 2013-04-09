// Upload view model class

define(['jquery', 'knockout', 'app/DataAccessObject', 'binding/uploader', 'binding/dropzone'], function ($, ko, dao) {
	return function () {
		var self = this;
		
		self.images = ko.observableArray();
		
		var storedImages = localStorage.getItem("uploads");
		if (storedImages !== undefined) {
			var arr = $.parseJSON(storedImages);
			
			$.each(arr, function(index, image) {
				self.images.push({
					id: image.id,
					filename: ko.observable(image.filename),
					comment: ko.observable(image.comment)
				});
			});
		}
		
		self.imageCount = ko.computed(function () {
			return self.images().length;
		});
		
		self.hasImages = ko.computed(function () {
			return self.images().length != 0;
		});
		
		self.uploadImage = function () {	
		};
		
		self.uploadedImage = function (image) {
			self.images.push({
				id: image.id,
				filename: ko.observable(image.filename),
				comment: ko.observable(image.comment)
			});
			
			var images = [];
			
			$.each(self.images(), function(index, value) {
				images.push({
					id: value.id,
					filename: value.filename(),
					comment: value.comment()
				});
			});
			
			localStorage.setItem("uploads", JSON.stringify(images));
		};;
		
		self.publishImage = function (image) {
			var toSave = {
				id: image.id,
				filename: image.filename(),
				comment: image.comment()
			};
			
			dao.images.publish(toSave, function (data) {
				self.images.remove(image);
			});
		};
	};
});
