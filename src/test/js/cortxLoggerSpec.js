var helper = require('helpers/specHelper');

var vertx = require('vertx.js')

load('org/cortx/maven/plugin/module/CortxLogger.js');

describe('The CortxLogger Spec', function () {

	var logger, message;
	
	beforeEach(function () {
		logger = CortxLogger.instance();
		message = 'test message';
	});
	
	it('should return logger instance', function () {
		// Then
		expect(logger).toBeDefined();
	});

	it('should be same instance', function () {
		// Then
		expect(logger).toBe(CortxLogger.instance());
	});
	
	it('should log message', function () {
		// Given
		spyOn(logger.console, 'log');
		
		// When
		logger.log(message)
		
		// Then
		expect(logger.console.log).toHaveBeenCalledWith(message);
	});

	it('should not log undefined message', function () {
		// Given
		spyOn(logger.console, 'log');
		message = undefined;
		
		// When
		logger.log(message)
		
		// Then
		expect(logger.console.log).not.toHaveBeenCalled();
	});
	
	it('should not log null message', function () {
		// Given
		spyOn(logger.console, 'log');
		message = null;
		
		// When
		logger.log(message)
		
		// Then
		expect(logger.console.log).not.toHaveBeenCalled();
	});
	
	it('should bind logger', function () {
		// Given
		this.log = logger.logger;
		spyOn(logger.console, 'log');
		
		// When
		this.log(message);
		
		// Then
		expect(logger.console.log).toHaveBeenCalledWith(message);
	});
	
	it('should not log when disabled', function () {
		// Given
		spyOn(logger.console, 'log');
		
		// When
		logger.disable();
		logger.log(message)
		
		// Then
		expect(logger.console.log).not.toHaveBeenCalled();
	});
	
});