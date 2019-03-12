/*
* logger
*
* create by cch
*/

//import
const log4js = require('log4js');

log4js.configure({
    appenders: {
        ruleConsole: {type: 'console'}
    }
    , categories: {
        default: {appenders: ['ruleConsole'], level: 'all'}
    }
});
const logger = log4js.getLogger();

function i(e) {
    logger.info(e);
}

function e(e) {
    logger.error(e);
}

function d(e) {
    logger.debug(e);
}

module.exports = {
    i: i,
    e: e,
    d: d
}