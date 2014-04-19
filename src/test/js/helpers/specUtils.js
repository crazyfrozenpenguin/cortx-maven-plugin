var vertx = require('vertx');
var buffer = require('vertx/buffer');

// Emulate Vertx Container
var container = {
	log: true
};

Object.prototype.forEach = function(f) {
	for (var item in this) {
		f(item, this[item])
	}
};

var Headers = {
	ACCEPT : 'Accept'
}

var MymeType = {
	APP_JSON : 'application/json'
}

function Request() {
	this.body = '{body:"request"}'
	this.endResponse = undefined
	
	this.uri = function() { 
		return '/test/uri'
	};

	this.method = function() {
		return 'GET'
	};
	
	this.headers = function() {
		var headers = {}
		headers[Headers.ACCEPT] = MymeType.APP_JSON
		return headers
	};
	
	this.dataHandler = function(f) {
		f(new vertx.Buffer().appendString(this.body))
	}
	
	this.endHandler = function(f) {
		f()
	}
	
	this.response = {
		end : function(response) {
			this.endResponse = response
		}.bind(this)
	}
}; 


