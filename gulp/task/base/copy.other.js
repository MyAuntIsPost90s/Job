/*
* delete 复制html文件
*
* create by cch
*/

//import
const gulp = require('gulp');
const globalConfig = require('../../config/config.global');
const appConfig = require('../../config/config.apps');
const argUtil = require('../../util/argUtil');
const logUtil = require('../../util/logUtil');
const objectValid = require('../../valid/objectValid');

gulp.task('copyOther:dist', () => {
    logUtil.i('start copyOther:dist');
    var gulpStream = null;
    var modules = argUtil.getModuleArgs();
    var targetDir = globalConfig.getDistDir();
    var pathStr = '';
    if (objectValid.isNotEmpty(modules)) {
        if (modules.length == 1) {
            targetDir = globalConfig.getDistDir(modules[0]);
        }
        pathStr = appConfig.getModulePaths(modules);
        gulpStream = copyOther(pathStr, targetDir);
    } else {
        var path = appConfig.getModulePath();
        gulpStream = copyOther(path, targetDir);
    }
    logUtil.i('copyOther:dist success');
    return gulpStream;
});

gulp.task('copyOther:build', () => {
    logUtil.i('start copyOther:build');
    var gulpStream = null;
    var modules = argUtil.getModuleArgs();
    var targetDir = globalConfig.getBuildDir();
    var pathStr = '';
    if (objectValid.isNotEmpty(modules)) {
        if (modules.length == 1) {
            targetDir = globalConfig.getBuildDir(modules[0]);
        }
        pathStr = appConfig.getModulePaths(modules);
        gulpStream = copyOther(pathStr, targetDir);
    } else {
        var path = appConfig.getModulePath();
        gulpStream = copyOther(path, targetDir);
    }
    logUtil.i('copyOther:build success');
    return gulpStream;
});

//复制并处理js
function copyOther(source, target) {
    source = globalConfig.getSourceDir(source) + globalConfig.match.OTHER;
    logUtil.i('begin copy orther:' + source);
    var gulpStream = gulp.src(source)
        .pipe(gulp.dest(target))
    logUtil.i('end copy orther:' + target);
    return gulpStream;
}