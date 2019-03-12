/*
* 导出成zip
*
* create by cch
*/

//import
const gulp = require('gulp');
const plug = require('gulp-load-plugins')();
const globalConfig = require('../../config/config.global');
const logUtil = require('../../util/logUtil');

gulp.task('zip', () => {
    var buildDir = globalConfig.getBuildDir();
    logUtil.i('build app to zip in dir:' + buildDir);
    var gulpStream = gulp.src(buildDir + globalConfig.match.ALL)
        .pipe(plug.plumber())
        .pipe(plug.zip('app.zip'))
        .pipe(plug.md5('10'))
        .pipe(gulp.dest(buildDir));
    logUtil.i('build app to zip success');
    return gulpStream;
})
