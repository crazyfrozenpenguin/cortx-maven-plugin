// load cortx logger
load('org/cortx/maven/plugin/module/CortxLogger.js');

// config cortx
var cortx = {
	config : container.config
};

(function () {
	
	this.createKey = function(request) {
		var uri = new String(request.uri());
		return request.method() + ':' + uri;
	}
	
}).apply(cortx);