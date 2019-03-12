/*
* 构建
*
* create by cch
*/

//import
const gulp = require('gulp');
const runSequence = require('run-sequence');

gulp.task('build',(callback) => {
    runSequence(
        'clean:dist',
        ['copyHtml:dist', 'copyCss:dist', 'copyJs:dist', 'copyImg:dist', 'copyOther:dist'],
        callback
    )
});