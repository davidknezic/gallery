define(['jquery', 'knockout', 'jquery.fileupload'], function ($, ko) {
	ko.bindingHandlers['uploader'] = {
		init: function(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
			var opts = valueAccessor();
	    	
			$(element).fileupload({
	    		url: 'api/images/upload',
		        dataType: 'json',
		        pasteZone: opts.zone,
		        dropZone: opts.zone,
		        fileInput: opts.field,
		        autoUpload: true,
		        singleFileUploads: false,
		        add: function (e, data) {
		        	data.submit();
		        },
		        send: function (e, data) {
		        	opts.send();
		        },
		        done: function (e, data) {
		            $.each(data.result, function (index, file) {
		                opts.done(file);
		            });
		        }
		    });
		}
	};
});
