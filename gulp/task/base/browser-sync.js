/**
 * Created by cch on 2018/06/21.
 */

//import
const gulp = require('gulp');
const browserSync = require('browser-sync').create();
const proxy = require('http-proxy-middleware');

const proxyConfig = require('../../config/config.proxy');
const globalConfig = require('../../config/config.global');
const logUtil = require('../../util/logUtil');
const argUtil = require('../../util/argUtil');
const appConfig = require('../../config/config.apps');
const stringValid = require('../../valid/stringValid');
const objectValid = require('../../valid/objectValid');

const defaultBS = process.platform === 'darwin' ? 'google chrome' : 'chrome'; // 默认打开chrome浏览器

gulp.task('browserSync', () => {
    logUtil.i('start browserSync...')
    var browserSyncConfig = getBrowserSyncConfig();
    var gulpStream = browserSync.init(browserSyncConfig);
    logUtil.i('start browserSync success');
    return gulpStream;
})

gulp.task('browserSync:reload',()=>{
    browserSync.reload();
})

function getBrowserSyncConfig() {
    var middleWareConfig = getMiddleWareConfig();
    var distPath = getDistPath();
    return {    //browsersync 配置
        notify: false,
        port: 8090,
        startPath: '/',
        server: {
            baseDir: ['./', distPath],
            index: '/index.html'
        },
        middleware: [...middleWareConfig],
        logPrefix: 'HifitCMS',
        browser: [defaultBS],
        open: 'external',
        host: "web-dev.youbokeba.com"
    };
}

function getDistPath() {
    var modules = argUtil.getModuleArgs();
    var path = '';
    if (objectValid.isNotEmpty(modules)) {
        path = appConfig.getModulePath(modules[0]);
        path == null ? '' : path;
    }
    return globalConfig.getDistDir(path);
}

function getMiddleWareConfig() {
    var host = getProxyHost();
    if (stringValid.isEmpty(host)) {
        throw new Error('error:转发地址不存在');
    }
    return [  //转发中间件配置
        proxy(proxyConfig.getProxyHead(), {
            target: host,
            changeOrigin: true
        })
    ];
}

//获取转发地址
function getProxyHost() {
    var arg = argUtil.getEnvArg();
    var host = proxyConfig.getProxyHost(arg);
    return host;
}