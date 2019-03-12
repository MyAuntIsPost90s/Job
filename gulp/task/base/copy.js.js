/*
* delete 复制html文件
*
* create by cch
*/

//import
const gulp = require('gulp');
const plug = require('gulp-load-plugins')();
const replace = require('gulp-replace');
const globalConfig = require('../../config/config.global');
const appConfig = require('../../config/config.apps');
const argUtil = require('../../util/argUtil');
const logUtil = require('../../util/logUtil');
const objectValid = require('../../valid/objectValid');
const proxyConfig = require('../../config/config.proxy');
const stringValid = require('../../valid/stringValid');

gulp.task('copyJs:dist', () => {
    logUtil.i('start copyJs:dist');
    var gulpStream = null;
    var modules = argUtil.getModuleArgs();
    var targetDir = globalConfig.getDistDir();
    var pathStr = '';
    if (objectValid.isNotEmpty(modules)) {
        if (modules.length == 1) {
            targetDir = globalConfig.getDistDir(modules[0]);
        }
        pathStr = appConfig.getModulePaths(modules);
        gulpStream = copyJs(pathStr, targetDir);
    } else {
        var path = appConfig.getModulePath();
        gulpStream = copyJs(path, targetDir);
    }
    logUtil.i('copyJs:dist success');
    return gulpStream;
});

gulp.task('copyJs:build', () => {
    logUtil.i('start copyJs:build');
    var gulpStream = null;
    var modules = argUtil.getModuleArgs();
    var targetDir = globalConfig.getBuildDir();
    var pathStr = '';
    if (objectValid.isNotEmpty(modules)) {
        if (modules.length == 1) {
            targetDir = globalConfig.getBuildDir(modules[0]);
        }
        pathStr = appConfig.getModulePaths(modules);
        gulpStream = copyJs(pathStr, targetDir);
    } else {
        var path = appConfig.getModulePath();
        gulpStream = copyMiniJs(path, targetDir);
    }
    logUtil.i('copyJs:build success');
    return gulpStream;
});

//复制js
function copyJs(source, target) {
    var env = argUtil.getEnvArg();
    if(stringValid.isEmpty(env)){
        throw new Error('error:--env 环境变量参数不能为空');
    }
    source = globalConfig.getSourceDir(source) + globalConfig.match.JS;
    logUtil.i('begin copy js:' + source);
    var gulpStream = gulp.src(source)
        .pipe(replace('#UrlHead#',''))
        .pipe(replace('#UploadUrlHead#',proxyConfig.getFileServerHosts(env)))
        .pipe(replace('#WebHead#',proxyConfig.getWebHead(env)))
        .pipe(gulp.dest(target))
    logUtil.i('end copy js:' + target);
    return gulpStream;
}

//复制并处理js
function copyMiniJs(source, target) {
    var env = argUtil.getEnvArg();
    if(stringValid.isEmpty(env)){
        throw new Error('error:--env 环境变量参数不能为空');
    }
    source = globalConfig.getSourceDir(source) + globalConfig.match.JS;
    logUtil.i('begin copy js:' + source);
    var gulpStream = gulp.src([source, globalConfig.match.IGNORE])
        .pipe(replace('#UrlHead#', proxyConfig.getProxyHost(env)))
        .pipe(replace('#UploadUrlHead#', proxyConfig.getFileServerHosts(env)))
        .pipe(replace('#WebHead#', proxyConfig.getWebHead(env)))
        .pipe(plug.babel())
        .pipe(plug.uglify())
        .pipe(gulp.dest(target))
    logUtil.i('end copy js:' + target);
    return gulpStream;
}