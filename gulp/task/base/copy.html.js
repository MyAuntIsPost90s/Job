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

gulp.task('copyHtml:dist', () => {
    logUtil.i('start copyHtml:dist');
    var gulpStream = null;
    var modules = argUtil.getModuleArgs();
    var targetDir = globalConfig.getDistDir();
    var pathStr = '';
    if (objectValid.isNotEmpty(modules)) {
        if (modules.length == 1) {
            targetDir = globalConfig.getDistDir(modules[0]);
        }
        pathStr = appConfig.getModulePaths(modules);
        gulpStream = copyMiniHtml(pathStr, targetDir);
    } else {
        var path = appConfig.getModulePath();
        gulpStream = copyMiniHtml(path, targetDir);
    }
    logUtil.i('copyHtml:dist success');
    return gulpStream;
});

gulp.task('copyHtml:build', () => {
    logUtil.i('start copyHtml:build');
    var gulpStream = null;
    var modules = argUtil.getModuleArgs();
    var targetDir = globalConfig.getBuildDir();
    var pathStr = '';
    if (objectValid.isNotEmpty(modules)) {
        if (modules.length == 1) {
            targetDir = globalConfig.getBuildDir(modules[0]);
        }
        pathStr = appConfig.getModulePaths(modules);
        gulpStream = copyMiniHtml(pathStr, targetDir);
    } else {
        var path = appConfig.getModulePath();
        gulpStream = copyMiniHtml(path, targetDir);
    }
    logUtil.i('copyHtml:build success');
    return gulpStream;
});

//复制并处理html
function copyMiniHtml(source, target) {
    source = globalConfig.getSourceDir(source) + globalConfig.match.HTML;
    var htmlMiniOptions = getHtmlMiniOption();
    logUtil.i('begin copy html:' + source);
    var gulpStream = gulp.src([source, globalConfig.match.IGNORE])
        .pipe(plug.htmlmin(htmlMiniOptions))
        .pipe(gulp.dest(target))
    logUtil.i('end copy html:' + target);
    return gulpStream;
}

function getHtmlMiniOption() {
    return {
        removeComments: true,//清除HTML注释
        collapseWhitespace: true,//压缩HTML
        removeEmptyAttributes: true,//删除所有空格作属性值 <input id="" /> ==> <input />
        removeScriptTypeAttributes: true,//删除<script>的type="text/javascript"
        removeStyleLinkTypeAttributes: true,//删除<style>和<link>的type="text/css"
        minifyJS: false,//压缩页面JS
        minifyCSS: true//压缩页面CSS
    }
}