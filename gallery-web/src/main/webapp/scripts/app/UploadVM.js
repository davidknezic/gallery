// Upload view model class

define(['knockout', 'app/DataAccessObject', 'binding/uploader'], function (ko, dao) {
	return function () {
		var self = this;
		
		self.images = ko.observableArray();
		
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
