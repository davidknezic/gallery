// todo

define(['jquery', 'bootstrap', 'knockout', 'text!template/viewer.html'], function ($, bootstrap, ko, template) {
	var ViewerViewModel = function (image, images) {
		var self = this;
		
		self.image = ko.observable(image);
		self.images = ko.observableArray(images);
		
		self._getCurrentIndex = function () {
			var image = self.image();
			var images = self.images();
			
			var index = -1;
			
			$.each(images, function (i, e) {
				if (e.id === image.id) {
					index = i;
				}
			});
			
			return index;
		};
		
		self.hasNext = function () {
			var index = self._getCurrentIndex();
			
			if (index < 0)
				return false;
			
			return !(index >= self.images().length - 1);
		};
		
		self.hasPrevious = function () {
			var index = self._getCurrentIndex();
			
			if (index < 0)
				return false;
			
			return !(index <= 0);
		};
		
		self.next = function () {
			if (self.hasNext()) {
				self.image(self.images()[self._getCurrentIndex() + 1]);
			}
		};
		
		self.previous = function () {
			if (self.hasPrevious()) {
				self.image(self.images()[self._getCurrentIndex() - 1]);
			}
		};
	};
	
	var modal = $(template).appendTo($('body'));
	
	var vm = new ViewerViewModel(null, []);
	
	ko.applyBindingsToDescendants(vm, modal.get(0));
	
	ko.bindingHandlers['viewer'] = {
		init: function(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
			var options = valueAccessor();
			
			$(element).click(function (e) {
				var image = ko.utils.unwrapObservable(options.image);
				var images = ko.utils.unwrapObservable(options.images);
				
				vm.image(image);
				vm.images(images);
				
				$('#modal').modal('show');
				
				e.preventDefault();
			});
		}
	};
});
