/*
* 全局配置文件
*
* create by cch
*/

//import
const stringValid = require('../valid/stringValid');

const sourcePath = 'app';
const distPath = 'dist';
const buildPath = 'build';
const match = {
    ALL: '/**'
    ,JS: '/**/*.js'
    ,HTML:'/**/*.html'
    ,CSS:'/**/*.css'
    ,IMG:'/**/*.+(jpg|png)'
    ,OTHER:'{/**/*.+(eot|svg|ttf|woff|woff2|less|scss),/**/lib/**}'
    ,IGNORE:'!/**/lib/**'
}

function getSourceDir(path) {
    if (stringValid.isEmpty(path)) {
        return sourcePath;
    }
    return sourcePath + path;
}

function getDistDir(path) {
    if (stringValid.isEmpty(path)) {
        return distPath;
    }
    if (path.indexOf("/") != 0) {
        path = "/" + path;
    }
    return distPath + path;
}

function getBuildDir(path) {
    if (stringValid.isEmpty(path)) {
        return buildPath;
    }
    if (path.indexOf("/") != 0) {
        path = "/" + path;
    }
    return buildPath + path;
}

module.exports = {
    getSourceDir: getSourceDir,
    getDistDir: getDistDir,
    getBuildDir: getBuildDir,
    match: match
}