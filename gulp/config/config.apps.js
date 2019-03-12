/*
* 存放项目功能模块 (提供给打包和构建使用)
* @author:cch
*/

//import
const stringValid = require('../valid/stringValid');
const objectValid = require('../valid/objectValid');

var modules = [
    {name: "JobCMS", path: "/JobCMS"}
];

function getModulePath(name) {
    if (stringValid.isEmpty(name)) {
        return '';
    }
    for (var i = 0; i < modules.length; i++) {
        if (modules[i].name == name) {
            return modules[i].path;
        }
    }
    return null;
}

function getModulePaths(names) {
    var pathStr = '';
    if (objectValid.isNotEmpty(names)) {
        var paths = []
        names.forEach((val) => {
            var path = getModulePath(val);
            if (stringValid.isNotEmpty(path)) {
                paths.push(path)
            }
        });
        if (paths.length == 1) {
            pathStr = paths[0];
        }
        if (paths.length > 1) {
            pathStr = '{' + paths.join(',') + '}';
        }
    }
    return pathStr;
}

module.exports = {
    modules: modules,
    getModulePath: getModulePath,
    getModulePaths: getModulePaths
}