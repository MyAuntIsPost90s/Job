/*
* delete 删除文件
*
* create by cch
*/

//import
const gulp = require('gulp');
const del = require('del');
const globalConfig = require('../../config/config.global');
const logUtil = require('../../util/logUtil');
const argUtil = require('../../util/argUtil');
const appConfig = require('../../config/config.apps');
const objectValid = require('../../valid/objectValid');

gulp.task('clean:build', () => {
    var buildPath = globalConfig.getBuildDir() + globalConfig.match.ALL;
    return clean(buildPath);
})

gulp.task('clean:dist', () => {
    var gulpStream = null;
    var modules = argUtil.getModuleArgs();
    logUtil.i('start clean distDir');
    if (objectValid.isNotEmpty(modules)) {
        var paths=[];
        modules.forEach((val) => {
            var path = appConfig.getModulePath(val);
            path = globalConfig.getDistDir(path) + globalConfig.match.ALL;
            paths.push(path);
        });
        gulpStream = clean(paths);

    } else {
        var path = globalConfig.getDistDir() + globalConfig.match.ALL;
        gulpStream = clean(path);
    }
    logUtil.i('clean distDir success');
    return gulpStream;
})

function clean(path) {
    logUtil.i('start clean path: ' + path);
    var gulpStream = del(path);
    logUtil.i('clean path success');
    return gulpStream;
}
