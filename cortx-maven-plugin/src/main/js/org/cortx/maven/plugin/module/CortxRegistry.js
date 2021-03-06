var CortxRegistry = function() {

    if (CortxRegistry.prototype._singletonInstance) {
        return CortxRegistry.prototype._singletonInstance;
    }

    CortxRegistry.prototype._singletonInstance = this;

    this.log = CortxLogger.instance().logger;

    this.reset();

    return this;
}

CortxRegistry.prototype.reset = function() {
    this.registeredRequests = {};
    this.processedRequests = {};
}

CortxRegistry.prototype.createRequestEntry = function(header, body) {
    return {
        header : header,
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

CortxRegistry.prototype.getRegisteredRequest = function(key) {
    if (key && this.registeredRequests[key]) {
        return this.registeredRequests[key];
    }
    return undefined;
}

CortxRegistry.prototype.processRequest = function(key, request) {
    var processBody = function(body) {
        this.processedRequests[key] = this.createRequestEntry(this.extractHeaders(request), body);

        this.log('\tRequest: ' + key + '\n\tBody: "' + body.toString() + '"');

        if (this.registeredRequests[key]) {
            this.log('\tRequest response found: ' + key + '\n');
            if (this.registeredRequests[key].header) {
                for (var hkey in this.registeredRequests[key].header) {
                    if (typeof this.registeredRequests[key].header[hkey] !== 'function') {
                        if (hkey == 'REGISTER_HTTP_STATUS_CODE') {
                            request.response.statusCode(parseInt(this.registeredRequests[key].header[hkey]));
                        } else if (hkey == 'REGISTER_HTTP_STATUS_MESSAGE') {
                            request.response.statusMessage(this.registeredRequests[key].header[hkey]);
                        } else {
                            this.log('\tReturned header: "' + hkey + ' = ' + this.registeredRequests[key].header[hkey] + '\"');
                            request.response.putHeader(hkey, this.registeredRequests[key].header[hkey]);
                        }
                    }
                }
            }
            if (this.registeredRequests[key].body) {
                this.log('\tReturned body: "' + this.registeredRequests[key].body + '\"\n');
                request.response.end(this.registeredRequests[key].body);
                return;
            }
        }

        if (key.startsWith('POST') || key.startsWith('PUT')) {
            this.log('Adding ' + key + ' to registry.');
            this.registeredRequests[key] = this.processedRequests[key];
        }

        request.response.end();

        this.log('\tRequest: ' + key + ' not in registry. Nothing returned.\n');
    };

    this.readRequestBody(request, processBody.bind(this));
}

CortxRegistry.instance = function () {
    return new CortxRegistry();
}