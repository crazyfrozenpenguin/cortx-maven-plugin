var helper = require('helpers/specHelper');

load('helpers/specUtils.js');
load('org/cortx/maven/plugin/module/common/JavaImports.js');
load('org/cortx/maven/plugin/module/CortxBase.js');
load('org/cortx/maven/plugin/module/CortxRegistry.js');

describe('The CortxRegistry Spec', function () {

	var registry, request, key
	
	beforeEach(function () {
		request = new Request()
		key = cortx.createKey(request)
		registry = CortxRegistry.instance()
	})
	
	it('should return registry instance', function () {
		// Then
		expect(registry).toBeDefined()
	})

	it('should be same instance', function () {
		// Then
		expect(registry).toBe(CortxRegistry.instance());
	})
	
	it('should register request details', function () {
		// When
		registry.register(key, request)
		
		// Then
		expect(request.endResponse).not.toBeDefined()
	})
	
	it('should retrieve registered request details', function () {
		// When
		registry.register(key, request)
		var result = registry.getRegisteredRequests(key)
		
		// Then
		expect(result).toBeDefined();
		expect(result.header[Headers.ACCEPT]).toBe(MymeType.APP_JSON)
		expect(result.body.toString()).toBe(request.body)
	})
	
	it('should process request details', function () {
		// When
		registry.processRequest(key, request)
		
		// Then
		expect(request.endResponse.toString()).toBe(request.body)
	})

	it('should retrieve process details', function () {
		// When
		registry.processRequest(key, request)
		var result = registry.getProcessedRequest(key)
		
		// Then
		expect(result).toBeDefined()
		expect(result.header[Headers.ACCEPT]).toBe(MymeType.APP_JSON)
		expect(result.body.toString()).toBe(request.body)
	})
});