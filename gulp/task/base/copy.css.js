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

gulp.task('copyCss:dist', () => {
    logUtil.i('start copyCss:dist');
    var gulpStream = null;
    var modules = argUtil.getModuleArgs();
    var targetDir = globalConfig.getDistDir();
    var pathStr ='';
    if (objectValid.isNotEmpty(modules)) {
        if (modules.length == 1) {
            targetDir = globalConfig.getDistDir(modules[0]);
        }
        pathStr=appConfig.getModulePaths(modules);
        gulpStream = copyMiniCss(pathStr, targetDir);
    } else {
        var path = appConfig.getModulePath();
        gulpStream = copyMiniCss(path, targetDir);
    }
    logUtil.i('copyCSS:dist success');
    return gulpStream;
});

gulp.task('copyCss:build', () => {
    logUtil.i('start copyCss:build');
    var gulpStream = null;
    var modules = argUtil.getModuleArgs();
    var targetDir = globalConfig.getBuildDir();
    var pathStr ='';
    if (objectValid.isNotEmpty(modules)) {
        if (modules.length == 1) {
            targetDir = globalConfig.getBuildDir(modules[0]);
        }
        pathStr=appConfig.getModulePaths(modules);
        gulpStream = copyMiniCss(pathStr, targetDir);
    } else {
        var path = appConfig.getModulePath();
        gulpStream = copyMiniCss(path, targetDir);
    }
    logUtil.i('copyCss:build success');
    return gulpStream;
});

//复制并处理css
function copyMiniCss(source, target) {
    source = globalConfig.getSourceDir(source) + globalConfig.match.CSS;
    logUtil.i('begin copy css:' + source);
    var gulpStream = gulp.src([source,globalConfig.match.IGNORE])
        .pipe(plug.plumber())
        .pipe(plug.cleanCss({
            compatibility: 'ie8',
            keepSpecialComments: '*'
        }))
        .pipe(gulp.dest(target))
    logUtil.i('end copy css:' + target);
    return gulpStream;
}