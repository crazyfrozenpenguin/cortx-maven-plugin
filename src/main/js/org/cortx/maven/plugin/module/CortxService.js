(function() {

	var log = CortxLogger.instance().logger
	var cortxRegistry = CortxRegistry.instance()
	
	this.init = function () {
		if (JSON.stringify(cortx.config) == '{}') container.exit()

		if (!cortx.config.log) CortxLogger.instance().disable()

		this.startServer()
	}

	this.startServer = function() {
		var cortxServer = vertx.createHttpServer()
		
		cortxServer.requestHandler(this.requestHandler.bind(this)).listen(cortx.config.port, 'localhost');
	}
	
	this.requestHandler = function(request) {
		var path = request.path()
		if (path.startsWith('/~/')) {
			this.handleRegistration(request)
		} else if (path.startsWith('/_/')) {
			this.handleVerification(request)
		} else { 
			this.handleAPIRequest(request)
		}
	}
	
	this.handleAPIRequest = function(request) {
		log('Process REST API call...');
		var key = cortx.createKey(request);
		cortxRegistry.processRequest(key, request);
	}

	this.handleRegistration = function(request) {
		log('Process registration...')
		var key = cortx.createKey(request)
		cortxRegistry.register(key, request)
	}
	
	this.handleVerification = function(request) {
		log('Process verification...');
		
		var key = cortx.createKey(request);
		var processedRequest = cortxRegistry.getProcessedRequest(key);
		if (processedRequest) {
			log('\tVerified ' + key + '\n');
			if (processedRequest.body) {
				log(processedRequest.body.toString())
				request.response.end(processedRequest.body);
			} else {
				log('Closing response...')
				request.response.end();
			}
			return;
		}
		
		var message = '\tUnknown request: ' + key;
		log(message)
	    request.response.statusCode(404).statusMessage(message).end();
	}

}).apply(cortx);