// Upload view model class

define(['jquery', 'knockout', 'app/DataAccessObject', 'binding/uploader', 'binding/dropzone'], function ($, ko, dao) {
	return function () {
		var self = this;
		
		self.images = ko.observableArray();
		
		// Fill array from local storage
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
			
			// Update local storage
			var images = self._toRawImageArray(self.images());
			localStorage.setItem("uploads", JSON.stringify(images));
		};
		
		self.publishImage = function (image) {
			var toSave = {
				id: image.id,
				filename: image.filename(),
				comment: image.comment()
			};
			
			dao.images.publish(toSave, function (data) {
				self.images.remove(image);
				
				// Update local storage
				var images = self._toRawImageArray(self.images());
				localStorage.setItem("uploads", JSON.stringify(images));
			});
		};
		
		self._toRawImageArray = function (images) {
			var raw = [];
			
			$.each(images, function(index, value) {
				raw.push({
					id: value.id,
					filename: value.filename(),
					comment: value.comment()
				});
			});
			
			return raw;
		};
	};
});
