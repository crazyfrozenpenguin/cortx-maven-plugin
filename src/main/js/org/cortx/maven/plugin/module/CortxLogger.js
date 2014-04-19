var CortxLogger = function () {
	if (CortxLogger.prototype._singletonInstance) {
		return CortxLogger.prototype._singletonInstance;
	}

	CortxLogger.prototype._singletonInstance = this;

	this.console = require('vertx/console');
	this.enabled = true;
	
	CortxLogger.prototype.log = function (message) {
		if (this.console && this.enabled && message) this.console.log(message);
	}
	
	CortxLogger.prototype.disable = function () {
		this.enabled = false;
	}
	
	CortxLogger.prototype.enable = function () {
		this.enabled = true;
	}
	
	CortxLogger.prototype.logger = CortxLogger.prototype.log.bind(this);

};

CortxLogger.instance = function () {
	return new CortxLogger();
};