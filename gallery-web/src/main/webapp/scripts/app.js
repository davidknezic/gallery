// Main app file

requirejs.config({
	baseUrl: 'scripts/lib',
	shim: {
		sammy: {
			deps: [ 'jquery' ],
			exports: 'sammy'
		}
	},
	paths: {
		app: '../app',
		binding: '../bindings',
		template: '../../templates'
	}
});

require(['jquery', 'sammy', 'knockout', 'domReady!'], function ($, Sammy, ko) {
	
	var app = Sammy('#main', function () {
		this.get('#!/home', function (context) {
			// Delete content
			context.app.swap('');
		});
		
		this.get('#!/upload', function (context) {
			// Delete content
			context.app.swap('');
			
			require(['app/UploadVM', 'text!template/upload.html'], function (UploadVM, template) {
				var viewModel = new UploadVM();
				
				$(template).appendTo($('#main'));
				
				ko.applyBindingsToDescendants(viewModel, $('#main').get(0));
			});
		});
		
		this.get('#!/browse/:category/:page', function (context) {
			this.params['category'];
			this.params['page'];
		});
		
		this.get('#!/browse', function (context) {
			// Delete content
			context.app.swap('');
			
			require(['app/BrowseVM', 'text!template/browse.html'], function (BrowseVM, template) {
				var viewModel = new BrowseVM();
				
				$(template).appendTo($('#main'));
				
				ko.applyBindingsToDescendants(viewModel, $('#main').get(0));
			});
		});
	});
	
	app.run('#/home');
});
