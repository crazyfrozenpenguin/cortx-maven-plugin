var vertx = require('vertx');
var buffer = require('vertx/buffer');

// Emulate Vertx Container
var container = {
	config: {
		log: true
	}
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

	this.uriValue = '/test/uri'
	this.body = '{body:"request"}'
	this.endResponse = undefined
	this.methodName = 'GET'
		
	this.uri = function() { 	
		return this.uriValue
	};

	this.path = this.uri
	
	this.method = function() {
		return this.methodName
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
		}.bind(this),
		
		statusCode : function(code) {
			this.statusCode = code
			return this.response
		}.bind(this),
		
		statusMessage : function(message) {
			this.statusMessage = message
			return this.response
		}.bind(this)
	}
}; 


