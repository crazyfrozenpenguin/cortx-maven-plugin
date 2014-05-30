var helper = require('helpers/specHelper');

load('helpers/specUtils.js');
load('org/cortx/maven/plugin/module/common/JSDefinitions.js');
load('org/cortx/maven/plugin/module/CortxBase.js');

describe('The Cortx Base Spec', function () {

	var logger = CortxLogger.instance()
	var request, registry
	var context = '/my/uri?x=y'
	
	beforeEach(function () {
		request = new Request()
		registry = CortxRegistry.instance()
	})

	it('should register key creation API with Cortx', function () {
		// Then
		expect(cortx.createKey).toBeDefined()
	})

	it('should create key for REST call request', function () {
		// Given
		request.uriValue = context
		request.methodName = 'HEAD'
			
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('HEAD:' + context)
	})
	
	it('should create key for GET registration request', function () {
		// Given
		request.uriValue = '/~/G' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('GET:' + context)
	})

	it('should create key for POST registration request', function () {
		// Given
		request.uriValue = '/~/P' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('POST:' + context)
	})

	it('should create key for PUT registration request', function () {
		// Given
		request.uriValue = '/~/U' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('PUT:' + context)
	})

	it('should create key for DELETE registration request', function () {
		// Given
		request.uriValue = '/~/D' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('DELETE:' + context)
	})

	it('should create key for HEAD registration request', function () {
		// Given
		request.uriValue = '/~/H' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('HEAD:' + context)
	})

	it('should create key for HEAD registration request', function () {
		// Given
		request.uriValue = '/~/O' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('OPTIONS:' + context)
	})

	it('should create key for CONNECT registration request', function () {
		// Given
		request.uriValue = '/~/C' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('CONNECT:' + context)
	})

	it('should create key for TRACE registration request', function () {
		// Given
		request.uriValue = '/~/T' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('TRACE:' + context)
	})

	it('should create key for PATCH registration request', function () {
		// Given
		request.uriValue = '/~/A' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('PATCH:' + context)
	})
	
	it('should create key for GET verification request', function () {
		// Given
		request.uriValue = '/_/G' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('GET:' + context)
	})

	it('should create key for POST verification request', function () {
		// Given
		request.uriValue = '/_/P' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('POST:' + context)
	})

	it('should create key for PUT verification request', function () {
		// Given
		request.uriValue = '/_/U' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('PUT:' + context)
	})

	it('should create key for DELETE verification request', function () {
		// Given
		request.uriValue = '/_/D' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('DELETE:' + context)
	})

	it('should create key for HEAD verification request', function () {
		// Given
		request.uriValue = '/_/H' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('HEAD:' + context)
	})

	it('should create key for HEAD verification request', function () {
		// Given
		request.uriValue = '/_/O' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('OPTIONS:' + context)
	})

	it('should create key for CONNECT verification request', function () {
		// Given
		request.uriValue = '/_/C' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('CONNECT:' + context)
	})

	it('should create key for TRACE verification request', function () {
		// Given
		request.uriValue = '/_/T' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('TRACE:' + context)
	})

	it('should create key for PATCH verification request', function () {
		// Given
		request.uriValue = '/_/A' + context
		
		// When
		var key = cortx.createKey(request)
		
		// Then
		expect(key).toBe('PATCH:' + context)
	})

	
});

