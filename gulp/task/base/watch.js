const gulp = require('gulp');
const globalConfig = require('../../config/config.global');
const appConfig = require('../../config/config.apps');
const argUtil = require('../../util/argUtil');
const objectValid = require('../../valid/objectValid');

gulp.task('watch', function () {
    var pathStr = '';
    var modules = argUtil.getModuleArgs();
    if (objectValid.isNotEmpty(modules)) {
        pathStr = appConfig.getModulePaths(modules) + globalConfig.match.HTML;
        pathStr = globalConfig.getSourceDir(pathStr);
        gulp.watch(pathStr, ['copyHtml:dist','browserSync:reload'])
        pathStr = appConfig.getModulePaths(modules) + globalConfig.match.JS;
        pathStr = globalConfig.getSourceDir(pathStr);
        gulp.watch(pathStr, ['copyJs:dist','browserSync:reload']);
        pathStr = appConfig.getModulePaths(modules) + globalConfig.match.CSS;
        pathStr = globalConfig.getSourceDir(pathStr);
        gulp.watch(pathStr, ['copyCss:dist','browserSync:reload']);
        pathStr = appConfig.getModulePaths(modules) + globalConfig.match.IMG;
        pathStr = globalConfig.getSourceDir(pathStr);
        gulp.watch(pathStr, ['copyImg:dist','browserSync:reload']);
        pathStr = appConfig.getModulePaths(modules) + globalConfig.match.OTHER;
        pathStr = globalConfig.getSourceDir(pathStr);
        gulp.watch(pathStr, ['copyOther:dist','browserSync:reload']);
    } else {
        pathStr = appConfig.getModulePath() + globalConfig.match.HTML;
        gulp.watch(pathStr, ['copyHtml:dist','browserSync:reload']);
        pathStr = appConfig.getModulePath() + globalConfig.match.JS;
        gulp.watch(pathStr, ['copyJs:dist','browserSync:reload']);
        pathStr = appConfig.getModulePath() + globalConfig.match.CSS;
        gulp.watch(pathStr, ['copyCss:dist','browserSync:reload']);
        pathStr = appConfig.getModulePath() + globalConfig.match.IMG;
        gulp.watch(pathStr, ['copyImg:dist','browserSync:reload']);
        pathStr = appConfig.getModulePath() + globalConfig.match.OTHER;
        gulp.watch(pathStr, ['copyOther:dist','browserSync:reload']);
    }
});