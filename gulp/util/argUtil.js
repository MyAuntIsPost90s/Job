/*
* 参数帮助类
*
* create by cch
*/

//import
const miniMist = require('minimist');
const stringValid = require('../valid/stringValid');

//获取参数
function getArg() {
    return miniMist(process.argv.slice(2));
}

//获取模块参数
function getModuleArgs() {
    var arg=getArg();
    if(stringValid.isNotEmpty(arg.m)){
        return arg.m.split(',');
    }
    return [];
}

//获取运行环境参数
function getEnvArg() {
    var arg=getArg();
    return arg.env;
}

module.exports = {
    getArg: getArg,
    getEnvArg:getEnvArg,
    getModuleArgs:getModuleArgs
};
