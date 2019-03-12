/*
* 导出
*
* create by cch
*/

//import
const gulp = require('gulp');
const runSequence = require('run-sequence');

gulp.task('export', (callback) => {
    runSequence(
        'clean:build',
        ['copyHtml:build', 'copyCss:build', 'copyJs:build', 'copyImg:build', 'copyOther:build'],
        'zip',
        callback
    )
});