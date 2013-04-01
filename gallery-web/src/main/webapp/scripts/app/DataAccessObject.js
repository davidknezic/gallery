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
	
	this._ajaxPost = function (url, data, callback) {
		$.ajax({
			url: url,
			data: data,
			dataType: 'json',
			type: 'POST',
			contentType : 'application/json',
			error: function (jqXhr, textStatus, errorThrown) {
				if (typeof(callback) === 'function') {
					callback({ error: undefined });
				}
			},
			success: function (data, textStatus, jqXhr) {
				if (typeof(callback) === 'function') {
					callback(data);
				}
			}
		});
	};
	
	this._showImage = function (id, callback) {
		_ajax('api/images/show/' + id, {}, callback);
	};
	
	this._imageList = function (count, startId, callback) {
		var data = {};
		
		if (startId !== undefined) {
			data['start'] = startId;
		}
		
		_ajax('api/images/list/' + count, data, callback);
	};
	
	this._randomImages = function (count, callback) {
		_ajax('api/images/random/' + count, {}, callback);
	};
	
	this._saveImage = function (image) {
		_ajaxPost('api/images/edit', JSON.stringify(image));
	};
	
	this._saveAndPublishImage = function (image, callback) {
		_ajaxPost('api/images/edit', JSON.stringify(image), function (data) {
			_ajaxPost('api/images/publish', data.id, callback);
		});
	};
	
	// exports
	return {
		images: {
			show: this._showImage,
			list: this._imageList,
			random: this._randomImages,
			save: this._saveImage,
			publish: this._saveAndPublishImage
		}
	};
});
