var helper = require('helpers/specHelper');

load('helpers/specUtils.js');
load('org/cortx/maven/plugin/module/common/JavaImports.js');
load('org/cortx/maven/plugin/module/CortxBase.js');
load('org/cortx/maven/plugin/module/CortxRegistry.js');

describe('The CortxRegistry Spec', function () {

	var logger = CortxLogger.instance();

	var registry;
	
	beforeEach(function () {
		registry = CortxRegistry.instance();
	});
	
	it('should return registry instance', function () {
		// Then
		expect(registry).toBeDefined();
	});

	it('should be same instance', function () {
		// Then
		expect(registry).toBe(CortxRegistry.instance());
	});
	
	it('should register request by URI based key', function () {
		// Given
		var request = new Request()
		var key = cortx.createKey(request)
		
		// When
		registry.register(key, request)
		var result = registry.getRegisteredRequests(key)
		
		// Then
		expect(result).toBeDefined();
		expect(result.header[Headers.ACCEPT]).toBe(MymeType.APP_JSON)
		expect(result.body.toString()).toBe(request.body)
	});
	
	it('should process request', function () {
		
	});
	
	it('shuld access processed request by URI based key', function () {
		
	});
});