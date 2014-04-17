var helper = require('helpers/specHelper');

var vertx = require('vertx.js')

load('org/cortx/maven/plugin/module/CortxLogger.js');
load('org/cortx/maven/plugin/module/CortxRegistry.js');

describe('The CortxRegistry Spec', function () {

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
});