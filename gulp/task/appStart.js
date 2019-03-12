/*
* web工程运行
*
* create by cch
*/

// import
const gulp = require('gulp');
const runSequence = require('run-sequence');

gulp.task('appStart', (callback) => {
    runSequence(
        'clean:dist',
        ['copyHtml:dist', 'copyCss:dist', 'copyJs:dist', 'copyImg:dist', 'copyOther:dist'],
        'browserSync',
        'watch',
        callback
    )
});