// TODO: Destroy fileupload on element removal
ko.bindingHandlers.pasteZoneUploader = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
    	var opts = valueAccessor();
    	
    	jQuery(element).fileupload({
    		url: 'upload',
	        dataType: 'json',
	        pasteZone: jQuery(element),
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
	                opts.done();
	            });
	        }
	    });
    }
};