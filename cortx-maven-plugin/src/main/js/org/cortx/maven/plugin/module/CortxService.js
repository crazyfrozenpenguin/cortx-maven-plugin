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
            log('\tVerified ' + key)
            if (processedRequest.header) {
                for (var hkey in processedRequest.header) {
                    if (typeof processedRequest.header[hkey] !== 'function') {
                        log('\tReturned header: "' + hkey + ' = ' + processedRequest.header[hkey] + '\"');
                        request.response.putHeader('VERIFY_' + hkey, processedRequest.header[hkey])
                    }
                }
                var registeredRequest = cortxRegistry.getRegisteredRequest(key);
                if (registeredRequest) {
                    if (registeredRequest.header['REGISTER_HTTP_STATUS_CODE']) {
                        log('\tReturned status code: "' + registeredRequest.header['REGISTER_HTTP_STATUS_CODE'] + '\"');
                        request.response.putHeader('REGISTER_HTTP_STATUS_CODE', registeredRequest.header['REGISTER_HTTP_STATUS_CODE']);
                    }
                    if (registeredRequest.header['REGISTER_HTTP_STATUS_MESSAGE']) {
                        log('\tReturned status message: "' + registeredRequest.header['REGISTER_HTTP_STATUS_MESSAGE'] + '\"');
                        request.response.putHeader('REGISTER_HTTP_STATUS_MESSAGE', registeredRequest.header['REGISTER_HTTP_STATUS_MESSAGE']);
                    }
                }
            }

            if (processedRequest.body) {
                log('\tReturned body: "' + processedRequest.body + '\"');
                request.response.end(processedRequest.body);
            } else {
                request.response.end();
            }

            log('\n')
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