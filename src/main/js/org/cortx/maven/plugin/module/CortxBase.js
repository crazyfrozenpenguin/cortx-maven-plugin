// load cortx logger
load('org/cortx/maven/plugin/module/CortxLogger.js');

// config cortx
var cortx = {
	config : container.config
};

(function () {
	
	this.createKey = function(request) {
		var uri = request.uri()
		if (uri.startsWith('/~/')) {
			uri = uri.substring(2)
		} else if (uri.startsWith('/_/')) { 
			var context = uri.substring(4);
			
			if (uri.startsWith('/_/G/')) {
				return 'GET:' + context;
			} else if (uri.startsWith('/_/P/')) {
				return 'POST:' + context;
			} else if (uri.startsWith('/_/U/')) {
				return 'PUT:' + context;
			} else if (uri.startsWith('/_/D/')) {
				return 'DELETE:' + context;
			} else if (uri.startsWith('/_/H/')) {
				return 'HEAD:' + context;
			} else if (uri.startsWith('/_/O/')) {
				return 'OPTIONS:' + context;
			} else if (uri.startsWith('/_/C/')) {
				return 'CONNECT:' + context;
			} else if (uri.startsWith('/_/T/')) {
				return 'TRACE:' + context;
			} else if (uri.startsWith('/_/A/')) {
				return 'PATCH:' + context;
			}
		}
		return request.method() + ':' + uri;
	}
	
}).apply(cortx);
