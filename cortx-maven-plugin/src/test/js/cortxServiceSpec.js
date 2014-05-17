var helper = require('helpers/specHelper');

load('helpers/specUtils.js');
load('org/cortx/maven/plugin/module/common/JSDefinitions.js');
load('org/cortx/maven/plugin/module/CortxBase.js');
load('org/cortx/maven/plugin/module/CortxRegistry.js');
load('org/cortx/maven/plugin/module/CortxService.js');

describe('The Cortx Service Spec', function () {

	var logger = CortxLogger.instance()
	var request, registry
	
	beforeEach(function () {
		request = new Request()
		registry = CortxRegistry.instance()
	})
	
	it('should register service API with Cortx', function () {
		// Then
		expect(cortx.init).toBeDefined()
		expect(cortx.startServer).toBeDefined()
		expect(cortx.requestHandler).toBeDefined()
		expect(cortx.handleAPIRequest).toBeDefined()
		expect(cortx.handleRegistration).toBeDefined()
		expect(cortx.handleVerification).toBeDefined()
	})
	
	it('should init service', function () {
		// Given
		spyOn(cortx, 'startServer')
		
		// When
		cortx.init()
		
		// Then
		expect(cortx.startServer).toHaveBeenCalled()
	})
	
	it('should handle registration requests', function () {
		// Given
		request.uriValue = '/~/my/uri?x=y'
		spyOn(cortx, 'handleRegistration')
		
		// When
		cortx.requestHandler(request)
		
		// Then
		expect(cortx.handleRegistration).toHaveBeenCalledWith(request)
	})
	
	it('should handle API/REST requests', function () {
		// Given
		request.uriValue = '/my/uri?x=y'
		spyOn(cortx, 'handleAPIRequest')
		
		// When
		cortx.requestHandler(request)
		
		// Then
		expect(cortx.handleAPIRequest).toHaveBeenCalledWith(request)		
	})

	it('should handle verification requests', function () {
		// Given
		request.uriValue = '/_/my/uri?x=y'
		spyOn(cortx, 'handleVerification')
		
		// When
		cortx.requestHandler(request)
		
		// Then
		expect(cortx.handleVerification).toHaveBeenCalledWith(request)				
	})

	it('should process registration request', function () {
		// Given
		request.uriValue = '/~/my/uri?x=y'
		var key = cortx.createKey(request)
		spyOn(registry, 'register')
		
		// When
		cortx.handleRegistration(request)
		
		// Then
		expect(registry.register).toHaveBeenCalledWith(key, request)
	})

	it('should process API/REST request', function () {
		// Given
		request.uriValue = '/my/uri?x=y'
		var key = cortx.createKey(request)
		spyOn(registry, 'processRequest')
		
		// When
		cortx.handleAPIRequest(request)
		
		// Then
		expect(registry.processRequest).toHaveBeenCalledWith(key, request)
	})

	it('should fail on verification request', function () {
		// Given
		request.uriValue = '/_/G/my/uri?x=y'
		var key = cortx.createKey(request)
		spyOn(registry, 'getProcessedRequest')
		spyOn(request.response, 'end')
		
		// When
		cortx.handleVerification(request)
		
		// Then
		expect(registry.getProcessedRequest).toHaveBeenCalledWith(key)
		expect(request.statusCode).toBe(404)
		expect(request.statusMessage).toBe('\tUnknown request: ' + key)
		expect(request.response.end).toHaveBeenCalled()
	})

	it('should process GET verification request', function () {
		// Given
		request.uriValue = '/my/uri?x=y'
		request.methodName = 'GET'
		request.putHeader('TEST-HEADER', 'header value')
		cortx.handleAPIRequest(request)
		
		request.uriValue = '/_/G/my/uri?x=y'
		var key = cortx.createKey(request)
		spyOn(registry, 'getProcessedRequest').andCallThrough()
		spyOn(request.response, 'end')
		
		// When
		cortx.handleVerification(request)
		
		// Then
		expect(registry.getProcessedRequest).toHaveBeenCalledWith(key)
		expect(request.response.headers()[Headers.ACCEPT]).toBe(MymeType.APP_JSON)
		expect(request.response.headers()['TEST-HEADER']).toBe('header value')
		expect(request.response.end).toHaveBeenCalled()
		expect(request.response.end.mostRecentCall.args[0].toString()).toBe(request.body)
	})

});