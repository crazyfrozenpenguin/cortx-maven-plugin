// init vert.x dependencies
var vertx = require('vertx.js')
var container = require('vertx/container');

// java imports
var String = java.lang.String;

// load cortx logger
load('org/cortx/maven/plugin/module/CortxLogger.js');

// config cortx
var cortx = {
	config : container.config
};

