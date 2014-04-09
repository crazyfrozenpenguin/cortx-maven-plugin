var vertx = require('vertx.js')
var container = require('vertx/container');
var console = require('vertx/console');

var config = container.config;

if (JSON.stringify(config) == '{}') {
	container.exit();
}

function log(message) {
	if (config.log && message != undefined) console.log(message);

}

function handleRegistration(req) {
	var uri = new java.lang.String(req.uri());
	var key = req.method() + ':' + uri.substring(2);
	var body = new vertx.Buffer();
	log('Registration details: ' + key);

	req.dataHandler(function(buffer) {
		body.appendBuffer(buffer);
	});

	req.endHandler(function() {
		registry[key] = body;
		req.response.end();
		log('Register on ' + key + ' return "' + body.toString() + '"');
	});
}

function handleVerification(req) {
	var uri = new java.lang.String(req.uri());
	var context = uri.substring(4);
	var key = undefined;
	if (uri.startsWith('/_/G/')) {
		key = 'GET:' + context;
	} else if (uri.startsWith('/_/P/')) {
		key = 'POST:' + context;
	} else if (uri.startsWith('/_/U/')) {
		key = 'PUT:' + context;
	} else if (uri.startsWith('/_/D/')) {
		key = 'DELETE:' + context;
	} else if (uri.startsWith('/_/H/')) {
		key = 'HEAD:' + context;
	} else if (uri.startsWith('/_/O/')) {
		key = 'OPTIONS:' + context;
	} else if (uri.startsWith('/_/C/')) {
		key = 'CONNECT:' + context;
	} else if (uri.startsWith('/_/T/')) {
		key = 'TRACE:' + context;
	} else if (uri.startsWith('/_/A/')) {
		key = 'PATCH:' + context;
	}

	if (key != undefined && requests[key] != undefined) {
		log('Verified ' + key);
		req.response.end(requests[key]);
		return;
	}
	
	var message = "Unknown request : " + key;
	log(message)
    req.response.statusCode(404).statusMessage(message).end();
}

function handleRequest(req) {
	var uri = new java.lang.String(req.uri());
	var key = req.method() + ':' + uri;
	var body = new vertx.Buffer();

	req.dataHandler(function(buffer) {
		body.appendBuffer(buffer);
	});

	req.endHandler(function() {
		requests[key] = body;
		log('Request: Received ' + key + ' with "' + body.toString() + '"');
		if (registry[key] != undefined) {
			log('Request response found: ' + key + ' returned "' + registry[key] + '"');
			req.response.end(registry[key]);
			return;
		}
		req.response.end();
		log('Request: ' + key + ' not in registry. Nothing returned.');
	});	
}

log('config is ' + JSON.stringify(config));

var registry = {};
var requests = {};

var server = vertx.createHttpServer();
server.requestHandler(function(req) {
	var path = new java.lang.String(req.path());
	if (path.startsWith('/~')) {
		log('Init registration...');
		handleRegistration(req);
	} else if (path.startsWith('/_')) {
		handleVerification(req);
	} else { 
		handleRequest(req);
	}
}).listen(config.port, 'localhost');
