(function() {

	var log = CortxLogger.instance().logger;
	var cortxRegistry = CortxRegistry.instance();
	
	this.init = function () {
		if (JSON.stringify(cortx.config) == '{}') container.exit();

		if (!cortx.config.log) CortxLogger.instance().disable();

		startServer();
	}

	function startServer() {
		this.cortxServer = vertx.createHttpServer();
		
		this.cortxServer.requestHandler(requestHandler.bind(this)).listen(cortx.config.port, 'localhost');
	}
	
	function requestHandler(request) {
		var path = new String(request.path());
		if (path.startsWith('/~')) {
			handleRegistration(request);
		} else if (path.startsWith('/_')) {
			handleVerification(request);
		} else { 
			handleAPIRequest(request);
		}
	}
	
	function handleAPIRequest(request) {
		log('Process REST API call...');
		var key = cortx.createKey(request);
		cortxRegistry.processRequest(key, request);
	}

	function handleRegistration(request) {
		log('Process registration...');
		var uri = new String(request.uri());
		var key = request.method() + ':' + uri.substring(2);
		cortxRegistry.register(key, request);
	}
	
	function handleVerification(request) {
		log('Process verification...');
		
		var uri = new String(request.uri());
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

		var processedRequest = cortxRegistry.getProcessedRequest(key);
		if (processedRequest) {
			log('\tVerified ' + key + '\n');
			if (processedRequest.body) {
				request.response.end(processedRequest.body);
			} else {
				request.response.end();
			}
			return;
		}
		
		var message = '\tUnknown request: ' + key;
		log(message)
	    request.response.statusCode(404).statusMessage(message).end();
	}

}).apply(cortx);