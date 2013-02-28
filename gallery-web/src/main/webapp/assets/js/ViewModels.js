function UploadViewModel() {
	var self = this;
	
	self.uploads = ko.observableArray();
	
	self.sendFile = function () {
		alert("send");
	}
	self.doneFile = function () {
		alert("done");
	}
}
