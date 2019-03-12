/*
* 转发配置文件
*
* create by cch
*/

//各个环境转发地址
var proxyHead = ["/sys", "/base", "/app"];
var FileServerHosts = [
    {name: 'dev', host: 'http://localhost:51973'}
    , {name: 'dev4j', host: 'http://localhost:8084'}
    , {name: 'test', host: 'http://39.105.212.215:9080'}
]

var ProxyHosts = [
    {name: 'dev', host: 'http://localhost:51973'} //开发环境
    , {name: 'dev4j', host: 'http://localhost:8084'}
    , {name: 'test', host: 'http://39.105.212.215:9080'} //测试环境
];

var webHead = [
    {name: 'dev', url: ''} //开发环境
    , {name: 'dev4j', url: ''}
    , {name: 'test', url: '/JobCMS'} //测试环境
];

function getProxyHost(name) {
    for (var i = 0; i < ProxyHosts.length; i++) {
        if (ProxyHosts[i].name == name) {
            return ProxyHosts[i].host;
        }
    }
    return null;
}

function getFileServerHosts(name) {
    for (var i = 0; i < FileServerHosts.length; i++) {
        if (FileServerHosts[i].name == name) {
            return FileServerHosts[i].host;
        }
    }
    return null;
}

function getProxyHead() {
    return proxyHead;
}

function getWebHead(name) {
    for (var i = 0; i < webHead.length; i++) {
        if (webHead[i].name == name) {
            return webHead[i].url;
        }
    }
    return null;
}

module.exports = {
    getProxyHost: getProxyHost,
    getFileServerHosts: getFileServerHosts,
    getProxyHead: getProxyHead,
    getWebHead: getWebHead
};