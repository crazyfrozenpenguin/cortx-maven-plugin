var CortxRegistry = function() {
	
	if (CortxRegistry.prototype._singletonInstance) {
		return CortxRegistry.prototype._singletonInstance;
	}

	CortxRegistry.prototype._singletonInstance = this;
	
	this.registeredRequests = {};
	this.processedRequests = {};

	this.log = CortxLogger.instance().logger;
	
	return this;
}

CortxRegistry.prototype.createRequestEntry = function(header, body) {
	return {
		header : {},
		body : body
	};
}

CortxRegistry.prototype.extractHeaders = function(request) {
	var header = {};
	request.headers().forEach(function(key, value) {
		header[key] = value;
	});
	return header;
}

CortxRegistry.prototype.readRequestBody = function(request, callback) {
	var body = new vertx.Buffer();

	request.dataHandler(function(buffer) {
		body.appendBuffer(buffer);
	});

	request.endHandler(function() {
		callback(body);
	});
}

CortxRegistry.prototype.register = function(key, request) {
	var processBody = function(body) {
		this.registeredRequests[key] = this.createRequestEntry(this.extractHeaders(request), body);
		request.response.end();
		this.log('\tRegistered request information:\n\tOn ' + key + '\n\tReturn body "' + body.toString() + '"\n');
	};
	
	this.readRequestBody(request, processBody.bind(this));
}

CortxRegistry.prototype.getProcessedRequest = function(key) {
	if (key && this.processedRequests[key]) {
		return this.processedRequests[key];
	}
	
	return undefined;
}

CortxRegistry.prototype.processRequest = function(key, request) {
	var processBody = function(body) {
		this.processedRequests[key] = this.createRequestEntry(this.extractHeaders(request), body);
		
		this.log('\tRequest: ' + key + '\n\tBody: "' + body.toString() + '"');
		
		if (this.registeredRequests[key] && this.registeredRequests[key].body) {
			this.log('\n\tRequest response found: ' + key + '\n\tReturned body: "' + this.registeredRequests[key].body + '"\n');
			request.response.end(this.registeredRequests[key].body);
			return;
		}
		
		request.response.end();
		
		this.log('\tRequest: ' + key + ' not in registry. Nothing returned.\n');
	};

	this.readRequestBody(request, processBody.bind(this));
}

CortxRegistry.instance = function () {
	return new CortxRegistry();
};