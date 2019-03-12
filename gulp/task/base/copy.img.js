/*
* delete 复制html文件
*
* create by cch
*/

//import
const gulp = require('gulp');
const plug = require('gulp-load-plugins')();
const globalConfig = require('../../config/config.global');
const appConfig = require('../../config/config.apps');
const argUtil = require('../../util/argUtil');
const logUtil = require('../../util/logUtil');
const objectValid = require('../../valid/objectValid');
const stringValid = require('../../valid/stringValid');

gulp.task('copyImg:dist', () => {
    logUtil.i('start copyImg:dist');
    var gulpStream = null;
    var modules = argUtil.getModuleArgs();
    var targetDir = globalConfig.getDistDir();
    var pathStr = '';
    if (objectValid.isNotEmpty(modules)) {
        if (modules.length == 1) {
            targetDir = globalConfig.getDistDir(modules[0]);
        }
        pathStr = appConfig.getModulePaths(modules);
        gulpStream = copyMiniImg(pathStr, targetDir);
    } else {
        var path = appConfig.getModulePath();
        gulpStream = copyMiniImg(path, targetDir);
    }
    logUtil.i('copyImg:dist success');
    return gulpStream;
});

gulp.task('copyImg:build', () => {
    logUtil.i('start copyImg:build');
    var gulpStream = null;
    var modules = argUtil.getModuleArgs();
    var targetDir = globalConfig.getBuildDir();
    var pathStr = '';
    if (objectValid.isNotEmpty(modules)) {
        if (modules.length == 1) {
            targetDir = globalConfig.getBuildDir(modules[0]);
        }
        pathStr = appConfig.getModulePaths(modules);
        gulpStream = copyMiniImg(pathStr, targetDir);
    } else {
        var path = appConfig.getModulePath();
        gulpStream = copyMiniImg(path, targetDir);
    }
    logUtil.i('copyImg:build success');
    return gulpStream;
});

//复制并处理js
function copyMiniImg(source, target) {
    source = globalConfig.getSourceDir(source) + globalConfig.match.IMG;
    logUtil.i('begin copy img:' + source);
    var gulpStream = gulp.src([source, globalConfig.match.IGNORE])
        .pipe(plug.imagemin())
        .pipe(gulp.dest(target))
    logUtil.i('end copy img:' + target);
    return gulpStream;
}