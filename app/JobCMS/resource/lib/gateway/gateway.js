/**
 * 设置全局token author:lingshi
 *
 */
$(function () {
    $.ajaxSetup({
        aysnc: true, // 默认异步加载
        type: "POST", // 默认使用POST方式
        dataType: 'JSON',
        crossDomain: true,
        xhrFields: {withCredentials: true},
        headers: { // 默认添加请求头
            "AppKey": APP_KEY,
            "AccessToken": GatewayUtil.getCookie(TOKEN_KEY),
        },
        complete: function (XMLHttpRequest, textStatus) {
            // 请求完成处理
            if (XMLHttpRequest.responseJSON != null
                && XMLHttpRequest.responseJSON.errorCode == '0X0000') {
                window.top.location.href = LOGIN_URL; // token失效，跳转登陆页面
                return;
            }
        }
    });
})

var GatewayUtil = {
    getCookie: function (name) {
        var strcookie = document.cookie;//获取cookie字符串
        var arrcookie = strcookie.split("; ");//分割
        //遍历匹配
        for (var i = 0; i < arrcookie.length; i++) {
            var arr = arrcookie[i].split("=");
            if (arr[0] == name) {
                return arr[1];
            }
        }
        return "";
    },
    getParam: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)
            return r[2];
        return '';
    }
}