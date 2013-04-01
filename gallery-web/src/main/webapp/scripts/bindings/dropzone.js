// todo

define(['jquery', 'knockout', 'app/requestAnimationFrame'], function ($, ko, reqAnimationFrame) {
	
	// Drag listener
	var DragListener = function (options) {
		var counter = 0;
		
		$element = $(options.element);
		
		$element.bind('dragenter', function (e) {
			if (counter == 0) {
				options.start();
			}
			
			counter++;
		});
		
		$element.bind('dragleave', function (e) {
			counter--;
			
			if (counter == 0) {
				options.end();
			}
		});
		
		$element.bind('drop', function (e) {
			counter--;
			
			if (counter == 0) {
				options.end();
			}
		});
		
	};
	
	// Add the binding handler
	ko.bindingHandlers['dropzone'] = {
		init: function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
			var context = element.getContext("2d");
			var stopAnimation = true;
			var lastTime = undefined;

			var arrows = $('<img src="assets/img/arrows.png" />').get(0);
			var dropzone = $('<img src="assets/img/dropzone.png" />').get(0);
			
			var colorAcceleration;
			var color;
			
			var frame = function (time) {
				var delta = time - (lastTime || time);
				
				var colorChange = colorAcceleration * delta;
				color += colorChange;
				
				if (color > 1.0) {
					color = 1.0;
					colorAcceleration *= -1;
				} else if (color < 0.0) {
					color = 0.0;
					colorAcceleration *= -1;
				}
				
				/*context.fillStyle = 'black';
				context.font = '20px Arial';
				context.fillText("JPEG", 10, 50);
				context.fillText("PNG", 10, 100);
				
				var gradient = context.createRadialGradient(400, 100, 10, 400, 100, 500);
				gradient.addColorStop(0, 'black');
				gradient.addColorStop(1, 'blue');
				
				context.fillStyle = gradient;
				context.fillRect(0, 0, 960, 200);*/
				
				context.drawImage(dropzone, 0, 0);

				var change = (function (time) {
					return -(time * time) + 1;
				})(color);
				
				context.drawImage(arrows, 0, 0, 78, 54, 100 + (change*100), 80, 78, 54);
				context.drawImage(arrows, 0, 54, 78, 54, 782 - (change*100), 80, 78, 54);
				
				if (!stopAnimation) {
					lastTime = time;
					reqAnimationFrame(frame);
				}
			};
			
			frame(undefined);
			
			new DragListener({
				element: $(window),
				start: function () {
					stopAnimation = false;
					colorAcceleration = 0.001;
					color = 0.0;
					reqAnimationFrame(frame);
				},
				end: function () {
					stopAnimation = true;
					lastTime = undefined;
				}
			});
		}
	};
	
});
