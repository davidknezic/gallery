// data access object

define(['jquery'], function ($) {
	this._ajax = function (url, data, callback) {
		$.ajax({
			url: url,
			data: data,
			dataType: 'json',
			error: function (jqXhr, textStatus, errorThrown) {
				callback({ error: undefined });
			},
			success: function (data, textStatus, jqXhr) {
				callback(data);
			}
		});
	};
	
	this._imageList = function (count, startId, callback) {
		var data = {};
		
		if (startId !== undefined) {
			data['start'] = startId;
		}
		
		_ajax('api/images/list/' + count, data, callback);
	}
	
	// exports
	return {
		images: {
			list: this._imageList
		}
	};
});
