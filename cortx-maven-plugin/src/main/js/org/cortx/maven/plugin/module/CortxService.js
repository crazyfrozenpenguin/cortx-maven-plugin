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
		} else if (path.startsWith('/$')) {
			cortxRegistry.reset()
			log('Registry reset.')
			request.response.end()
		} else { 
			this.handleAPIRequest(request)
		}
	}
	
	this.handleAPIRequest = function(request) {
		log('Process REST API call...');
		var method = request.method();
		var key = cortx.createKey(request);
		cortxRegistry.processRequest(key, request);
	}

	this.handleRegistration = function(request) {
		log('Process registration...')

		var key = cortx.createKey(request);
		
		var method = request.method();
		if (method != 'POST' && method != 'PUT') {
			this.unknownRequest(request, key);
		} else {
			cortxRegistry.register(key, request);
		}
	}
	
	this.handleVerification = function(request) {
		log('Process verification...');
		
		var key = cortx.createKey(request);
		
		var processedRequest = cortxRegistry.getProcessedRequest(key);
		if (processedRequest) {
			log('\tVerified ' + key + '\n')
			if (processedRequest.header) {
				for (var key in processedRequest.header) {
					if (typeof processedRequest.header[key] !== 'function') {
						request.response.putHeader('VERIFY_' + key, processedRequest.header[key])
					}
				}
			}
			if (processedRequest.body) {
//				log(processedRequest.body.toString())
				request.response.end(processedRequest.body);
			} else {
				log('Closing response...')
				request.response.end();
			}
			return;
		}
		
		this.unknownRequest(request, key);
	}

	this.unknownRequest = function(request, key) {
		var message = '\tUnknown request: ' + key;
		log(message)
	    request.response.statusCode(404).statusMessage(message).end();
	}
	
}).apply(cortx);