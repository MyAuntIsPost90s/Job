//统一分装ajax
const Ajax = function (param) {
    var url = param.url;
    var data = param.data;
    var onSuccess = param.onSuccess;
    var tag = layer.open({type: 3});
    $.ajax({
        type: "post",
        url: URL_Head + url,
        contentType: "application/x-www-form-urlencoded",
        data: data,
        dataType: "json",
        success: function (data) {
            layer.close(tag);
            if (data.msgcode === '0X0000') {
                layer.msg(data.msg, {icon: 2});
                window.location.href = LOGIN_URL;
                return;
            }
            if (data.code == 1) {
                if (onSuccess != null) {
                    onSuccess(data);
                }
            } else {
                layer.msg(data.msg, {icon: 2});
            }
        }, error: function (error) {
            layer.msg('请求发生异常T_T', {icon: 2});
            console.log(error);
        }
    });
};

const FormUtil = {
    getFormMap: function (id) {
        var map = {};
        var name = null;
        var val = null;
        var elem = null;
        //获取input
        var inputs = $(id).find('input');
        for (var i = 0; i < inputs.length; i++) {
            elem = $(inputs[i]);
            if (!FormUtil.valid(elem)) {
                return;
            }
            name = elem.attr('name');
            val = elem.val();
            if (elem.attr('type') == 'radio') {
                val = $('input[name=' + name + ']:checked').val();
            }
            if (val != null && val != '') {
                map[name] = val;
            }
        }
        //获取select
        var selects = $(id).find('select');
        for (var i = 0; i < selects.length; i++) {
            elem = $(selects[i]);
            if (!FormUtil.valid(elem)) {
                return;
            }
            name = elem.attr('name');
            val = elem.val();
            if (val != null && val != '') {
                map[name] = val;
            }
        }
        //获取textarea
        var textareas = $(id).find('textarea');
        for (var i = 0; i < textareas.length; i++) {
            elem = $(textareas[i]);
            if (!FormUtil.valid(elem)) {
                return;
            }
            name = elem.attr('name');
            val = elem.val();
            if (val != null && val != '') {
                map[name] = val;
            }
        }
        return map
    },
    loadForm: function (id, data) {
        var elem = null;
        var name = null;
        //获取input
        var inputs = $(id).find('input');
        for (var i = 0; i < inputs.length; i++) {
            elem = $(inputs[i]);
            name = elem.attr('name');
            if (elem.attr('type') == 'radio' && data[name] != null) {
                if (elem.val() == data[name]) {
                    elem.attr('checked', 'checked');
                } else {
                    elem.removeAttr('checked');
                }
            }
            else if (data[name] != null) {
                elem.val(data[name]);
            }
        }
        //获取select
        var selects = $(id).find('select');
        for (var i = 0; i < selects.length; i++) {
            elem = $(selects[i]);
            name = elem.attr('name');
            if (data[name] != null) {
                elem.val(data[name]);
            }
        }
        //填充textarea
        var textareas = $(id).find('textarea');
        for (var i = 0; i < textareas.length; i++) {
            elem = $(textareas[i]);
            name = elem.attr('name');
            if (data[name] != null) {
                elem.val(data[name]);
            }
        }
    },
    valid: function (elem) {
        var val = elem.val();
        var validstr = elem.attr('data-valid');
        if (validstr != null && validstr != '') {
            var valid = eval('({' + validstr + '})');
            if (valid.notEmpty === true) {
                if (val == null || val == '') {
                    layer.msg(valid.name == null ? '' : valid.name + '不能为空', {icon: 2});
                    elem.focus();
                    return false;
                }
            }
            if (val.length < valid.length[0] || val.length > valid.length[1]) {
                layer.msg(valid.name == null ? '' : valid.name + '长度因在' + valid.length[0] + '到' + valid.length[1] + '之间', {icon: 2});
                elem.focus();
                return false;
            }
        }
        return true;
    }
}

/**
 * EasyUI配合lingshiframework返回类型帮助
 * author:linghsi
 * date:2018-01-29
 */
var EasyUIUtil = {
    loadForm: function (id, url, params) {
        Ajax({
            url: url,
            data: params,
            onSuccess: function (data) {
                $(id).form('load', data.data);
            }
        });
    },
    initTree: function (id, url, params) {
        $(id).tree({
            url: URL_Head + url,
            method: 'post',
            loadFilter: function (data) {
                return data.data;
            }
        })
    },
    initDataGrid: function (id, url, columns, params) {
        $(id).datagrid({
            url: URL_Head + url,
            method: 'get'
            , queryParams: params
            , fit: "true"
            , columns: columns
            , rownumbers: true
            , selectOnCheck: true
            , checkOnSelect: false
            , singleSelect: false
            , fitColumns: true
            , rownumbers: true
            , pagination: true
            , loadFilter: function (data) {
                if (data.code == 0) {
                    $.messager.alert("错误", data.msg);
                    return null;
                }
                return data.data;
            }
            , onCheckAll: function () {
                $(id).datagrid("unselectAll");
            }
            , onSelect: function (rowIndex, rowData) {
                $(id).datagrid("unselectRow", rowIndex);
            }
        })
    }
}

//编辑器加载器
var EditModel = {
    build: function (id, option) {
        var layedit = layui.layedit;
        layedit.set({
            uploadImage: {
                url: EDIT_UPLOAD_URL //接口url
                , type: 'post' //默认post
                , size: 10
                , before: function () {
                    layer.load();
                }
                , done: function () {
                    layer.closeAll("loading");
                }
                , error: function () {
                    layer.closeAll("loading");
                }
            }
        });
        return layedit.build(id, option); //建立编辑器
    },
    setContent: function (tag, val) {   //设置值
        try {
            var layedit = layui.layedit;
            layedit.setContent(tag, val);
        } catch (err) {
            //不做处理
        }
    },
    getContent: function (tag) {   //获取值
        var layedit = layui.layedit;
        return layedit.getContent(tag);
    }
}
