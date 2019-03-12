/**
 * 提供stringformat帮助
 * author:caichenghh
 * date:2018/01/19
 */
String.prototype.format = function (args) {
    var result = this;
    if (arguments.length > 0) {
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                if (args[key] != undefined) {
                    var reg = new RegExp("({" + key + "})", "g");
                    result = result.replace(reg, args[key]);
                }
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] != undefined) {
                    var reg = new RegExp("({)" + i + "(})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
    }
    return result;
}

/**
 * 分装webuploader,基本上传服务
 * author:caichenghh
 * date:2018/04/20
 */
function BaseUploader(params) {

    var registerName = 'BaseUploader';    //组件名称
    var chunkSize = params.chunkSize;     //分片大小
    var server = params.server;   // 文件接收服务端
    var fileSize = params.fileSize;   //文件大小
    var pick = params.pick; //选择文件的按钮id
    var uploadParams = {  //上传参数
        swf: 'Uploader.swf',    //swf文件路径
        pick: pick,
        chunked: true,	//开启分片上传
        chunkSize: chunkSize == null ? 1024 * 1024 * 5 : chunkSize * 1024 * 1024,	//默认5M一页
        threads: 1,	//线程数量
        chunkRetry: 0,//重传次数
        prepareNextFile: true,	//下一个分片预处理
        server: server,   // 文件接收服务端。
        fileSingleSizeLimit: fileSize,	//默认2G
        runtimeOrder: 'html5,flash',	//优先H5
        duplicate: true  //支持重复上传
    };
    var events = params.events;   //上传事件

    //文件上传过程事件
    var onBeforeSendFile = events.onBeforeSendFile;     //文件开始上传前回调
    var onBeforeSendBlock = events.onBeforeSendBlock;  //分块上传前回调
    var onAfterSendFile = events.onAfterSendFile;   //上传结束回调

    //队列事件
    var onBeforeFileQueued = events.onBeforeFileQueued; //文件加入队列前触发，返回false则不加入队列

    //文件事件
    var onUploadProgress = events.onUploadProgress;
    var onFileStatusChange = events.onFileStatusChange;

    //注册事件
    WebUploader.Uploader.register({
        name: registerName,
        //上传文件前执行
        beforeSendFile: function (file) {
            var deferred = WebUploader.Deferred();
            //1、计算文件的唯一标记fileMd5，用于断点续传
            (new WebUploader.Uploader()).md5File(file, 0, 10 * 1024 * 1024)
                .progress()
                .then(function (val) {
                    if (typeof(onBeforeSendFile) == 'function') {
                        onBeforeSendFile(file, val);	//执行一个上传前的回调
                    }
                    //获取文件信息后进入下一步
                    deferred.resolve();
                });
            return deferred.promise();
        },
        beforeSend: function (block) {
            var deferred = WebUploader.Deferred();
            if (typeof(onBeforeSendBlock) == 'function') {
                var result = onBeforeSendBlock(block, this);
                if (result == true) {
                    deferred.reject();
                }
            }
            deferred.resolve();
            return deferred.promise();
        },
        afterSendFile: function (file) {
            if (typeof(onAfterSendFile) == 'function') {
                onAfterSendFile(file);
            }
        }
    });
    //创建上传实践
    var uploader = WebUploader.create(uploadParams);

    //当有文件被添加进队列前
    uploader.on('beforeFileQueued', function (file) {
        if (file.getStatus() == 'inited') {
            if (typeof (onBeforeFileQueued) == 'function') {
                onBeforeFileQueued(file);
            }


            //注册事件变化事件
            file.on('statuschange', function (status) {
                if (typeof(onFileStatusChange) == 'function') {
                    onFileStatusChange(file, status);
                }
            });
        }
    });

    //文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function (file, percentage) {
        if (typeof (onUploadProgress) == 'function') {
            onUploadProgress(file, percentage);
        }
    });

    //移除文件
    this.removeFile = function (file) {
        uploader.removeFile(file);
    }

    //获取文件
    this.getFile = function (id) {
        return uploader.getFile(id);
    }

    //全部暂停
    this.stopAll = function () {
        uploader.stop(true);
    }

    //暂停单个文件
    this.stop = function (file) {
        uploader.stop(file);
        uploader.removeFile(file);
        uploader.addFile(file);
    }

    //开始或继续上传
    this.start = function (file) {
        uploader.upload(file);
    }

    //全部开始
    this.startAll = function () {
        uploader.upload();
    }

    //取消上传单个文件
    this.cancel = function (file) {
        uploader.cancelFile(file);
    }

    //销毁实例
    this.destroy = function () {
        WebUploader.Uploader.unRegister(registerName);	//取消注册
        uploader.destroy();
    }
}

/**
 * 文件上传组件工具类
 * author:caichenghh
 * date:2018/05/22
 */
var AttachMgrUtil = {
    getUUID: function () {
        var s = [];
        var hexDigits = "0123456789abcdef";
        for (var i = 0; i < 36; i++) {
            s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
        s[8] = s[13] = s[18] = s[23] = "-";
        var uuid = s.join("");
        return uuid;
    },
    getFileId: function () {
        return AttachMgrUtil.getUUID().toUpperCase().replace(/\-/g, '');
    },
    getBId: function () {
        return AttachMgrUtil.getFileId();
    },
    downLoad: function (url, param) {
        var $iframe = $('<iframe id="downloadIframe" src="' + url + '"></iframe>');
        var $form = $('<form target="downloadIframe" method="post"></form>');
        $form.attr('action', url);
        for (var key in param) {
            $form.append('<input type="hidden" name="' + key + '" value="' + param[key] + '"/>')
        }
        $('body').append($iframe);
        $iframe.append($form);
        $form[0].submit();
        $iframe.remove();
    }
}

/**
 * 文件上传组件
 * author:caichenghh
 * date:2018/05/22
 */
function AttachMgr(params) {

    var baseUploader = null;    //baseUploader
    var bId = params.bId;	//业务id
    var baseUrl = params.baseUrl;	//基路径
    var showType = params.showType == null ? 2 : params.showType;	//展示窗体类型，默认为1 1上传以及上传完成;2仅上传
    var fileSize = params.fileSize == null ? 1024 * 1024 * 500 : params.fileSize;       //单个文件大小 默认500M
    var totalFileSize = params.totalFileSize; //总上传个文件大小 不传不限制
    var fileCount = params.fileCount;
    var title = params.title == null ? '文件上传' : params.title;   //上传框的标题，为空时采用默认
    var tip = params.tip; //上传框的提示，为空时采用默认
    var chunkSize = params.chunkSize == null ? 1024 * 1024 * 5 : params.chunkSize; //分片大小
    var fileType = null;

    var onCancelCall = params.onCancelCall;	//用户点击取消或则删除后回调（单个文件）
    var onSuccessCall = params.onSuccessCall;	//用户上传成功后回调（单个文件）
    var onComplete = params.onComplete;	//用户点击确定后回调（窗体上的确定按钮）
    var onClosed = params.onClosed;	//窗体关闭后回调
    var onBeforeAddFile = params.onBeforeAddFile;    //上传文件前回调（返回false则取消上传）
    var onFileCountOver = params.onFileCountOver;     //文件数量超出回调
    var onFileSizeOver = params.onFileSizeOver;   //文件大小超出回调
    var onTotalFileSizeOver = params.onTotalFileSizeOver; //文件总大小超出回调

    //url
    var host = URL_Head; //host
    var blockCheckUrl = host + '/base/fileForUploader/blockExist';    //分块检查
    var blockMergeUrl = host + '/base/fileForUploader/blockMerge';        //合并分块
    var deleteUrl = host + '/base/fileForUploader/delete';	//取消url
    var initUrl = host + '/base/fileForUploader/init';	//插入url
    var downloadUrl = host + '/apps/support/attachmgr/download';	//下载url
    var batchDeleteUrl = host + '/apps/support/attachmgr/batchDelete';     //批量删除文件
    var uploadUrl = host + '/base/fileForUploader/uploadBlock';    //文件上传路径

    //窗体参数
    var winIndex = null;    //窗体标识
    var winSize = {   //窗体大小
        width: '670px'
        , height: '420px'
    }
    var winTip = {  //提示
        countOver: '上传个数超过最大限制'
        , sizeOver: '文件大小超过最大限制'
        , totalSizeOver: '文件总大小超过最大限制'
        , needOne: '请至少选择一条数据'
        , fileTypeErr: '不支持的文件类型'
        , fileNameLengthOver: '文件名长度应在100以内'
    }

    //边框模板
    var borderTemplet = '<div class="layui-tab layui-tab-brief filemgr">'
        + '<ul class="layui-tab-title"><li class="layui-this">待处理</li><li class="li-finished">已完成（<span id="sp-finishedCount">0</span>）</li>'
        + '<div style="float: right;padding: 0 10px;"><button id="btn-selectFile" class="layui-btn layui-btn-sm">选择文件</button></div>'
        + '</ul>'
        + '<div class="layui-tab-content">'
        + '<div class="layui-tab-item layui-show">{0}</div>'
        + '<div class="layui-tab-item">{1}</div>'
        + '</div></div>';
    //未处理部分模板
    var undoTemplet = '<table class="layui-table" lay-skin="line" >'
        + '<colgroup><col width="50"><col width="500"><col width="150"><col></colgroup>'
        + '<thead><tr><th><input type="checkbox" id="cb-all" /></th><th>文件</th><th>操作</th></tr></thead></table>'
        + '<div class="div-table"><table class="layui-table tablebody" lay-skin="line"><tbody id="file-list"></tbody></table></div>'
        + '<div class="layui-form-mid layui-word-aux"><span class="layui-badge">tips</span>&nbsp;<span id="sp-fileTip"></span></div>'
        + '<div class="btn-menus"><button id="btn-yes" class="layui-btn">确定</button><button id="btn-allCancel" class="layui-btn layui-btn-primary">取消</button></div>';
    //已处理部分模板
    var doTemplet = '<table class="layui-table" lay-skin="line" >'
        + '<colgroup><col width="50"><col width="500"><col width="150"><col></colgroup>'
        + '<thead><tr><th><input id="cb-delall" type="checkbox" /></th><th>文件</th><th>操作</th></tr></thead></table>'
        + '<div class="div-table"><table class="layui-table tablebody" lay-skin="line" ><tbody id="finished-list"></tbody></table></div>'
        + '<div class="btn-menus"><button id="btn-alldel" class="layui-btn layui-btn-sm layui-btn-primary">全部删除</button></div>';
    //上传文件项
    var uploadItemTemplet = '<tr id="{0}" data-fileSize="{3}" data-nowPercentage="0" data-finished="0" class="upload-item">'
        + '<td width="50"><input type="checkbox" /></td>'
        + '<td width="500"><div class="item-info">'
        + '<div class="filename">{1}</div>'
        + '<div class="filesize"><span class="sp-progressSize">0B</span>/{2}</div>'
        + '<div class="tip">等待上传</div>'
        + '<div class="percent"></div>'
        + '</div><div class="layui-progress">'
        + '<div class="layui-progress-bar"></div>'
        + '</div></td><td width="150">'
        + '<button class="layui-btn layui-btn-xs btn-upload">上传</button>'
        + '<button class="layui-btn layui-btn-warm layui-btn-xs btn-cancel">取消</button>'
        + '</td></tr>';
    //已完成文件项
    var finishedItemTemplet = '<tr id="{0}" data-fileid="{0}" data-fileSize="{3}" class="upload-item">'
        + '<td width="50"><input type="checkbox" /></td>'
        + '<td width="500"><div class="item-info">'
        + '<div class="filename">{1}</div>'
        + '<div class="filesize">{2}</div>'
        + '<div class="tip">已完成</div>'
        + '<div class="percent"></div>'
        + '</div></td><td width="150">'
        + '<button class="layui-btn layui-btn-xs btn-download">下载</button>'
        + '<button class="layui-btn layui-btn-warm layui-btn-xs btn-del">删除</button>'
        + '</td></tr>';

    //展示文件上传框
    function show() {
        //禁止重复打开
        if ($('#btn-selectFile').length > 0) {
            return;
        }
        var html = borderTemplet.format(undoTemplet, doTemplet);
        winIndex = layer.open({
            title: '<i class="layui-icon">&#xe61d;</i> ' + title,
            area: [winSize.width, winSize.height],
            content: html,
            type: 1,
            shade: 0,
            resize: false,
            success: function () {
                viewInit();
                tipInit();
                eventInit();
                baseUploaderInit();
            },
            btn: null,
            cancel: function () {
                $('#btn-allCancel').click();
                return false;
            },
            end: function () {
                baseUploader.destroy();
                if (onClosed != null) {
                    onClosed()
                }
            }
        });
    }

    //加载已完成数据（需要先将弹窗show出来）
    function loadFinished(data) {
        //data格式fileName,id,fileSize
        if (data == null) {
            return;
        }
        var html = '';
        for (var i = 0; i < data.length; i++) {
            var sizeStr = getFileSizeStr(data[i].fileSize);
            html += finishedItemTemplet.format(data[i].id, data[i].fileName, sizeStr, data[i].fileSize);
        }
        $('#finished-list').html(html);
        bindFinishedEvent();
        reloadFinishedCount();
    }

    //绑定完成文件的点击事件
    function bindFinishedEvent() {
        //全选事件
        $('#cb-delall').click(function () {
            var obj = this;
            $('#finished-list').find('input[type=checkbox]').each(function () {
                if ($(obj).is(':checked')) {
                    $(this)[0].checked = true;
                } else {
                    $(this)[0].checked = false;
                }
            });
        });

        //全部删除
        $('#btn-alldel').click(function () {
            var ids = [];
            $('#finished-list').find('input[type=checkbox]').each(function () {
                if ($(this).is(':checked')) {
                    var $tr = $(this).parent().parent();
                    ids.push($tr.attr('id'));
                    $tr.remove();
                }
            });
            if (ids == null || ids.length < 1) {
                layer.msg(winTip.needOne, {icon: 2})
            }
            deleteFiles(ids);
            reloadFinishedCount();
        });

        //绑定按钮事件
        $('#finished-list tr').each(function () {
            var $tr = $(this);
            //下载
            $tr.find('.btn-download').click(function () {
                var fileId = $tr.attr('id');
                downloadFile(fileId);
            })
            //删除
            $tr.find('.btn-del').click(function () {
                layer.confirm("是否确定删除该文件？", function (i) {
                    deleteFile($tr.attr('id'));
                    layer.close(i);
                });
            })
        })
    }

    //下载文件
    function downloadFile(fileId) {
        var param = {fileId: fileId};
        AttachMgrUtil.downLoad(downloadUrl, param);
    }

    //删除文件
    function deleteFile(fileId, tempPath, filePath) {   //fileId:文件主键
        //通知服务端删除
        $.post(deleteUrl
            , {tempPath: tempPath, filePath: filePath}
            , function (data) {
                if (data.code != 1) {
                    console.log(data);
                }
            }, 'json');
        var $tr = $('#' + fileId);
        if ($tr != null) {
            $tr.remove();
            reloadFinishedCount();
        }
    }

    //批量删除文件
    function deleteFiles(ids) {
        if (ids == null || ids.length < 1) {
            return;
        }
        var json = JSON.stringify(ids);
        $.ajax({
            type: "POST",
            contentType: "application/json ; charset=utf-8",
            url: batchDeleteUrl,
            data: json,
            dataType: "JSON",
            async: false,
            success: function (data) {
                if (data.code != 1) {
                    console.log(data);
                }
            }
        });
    }

    //重载完成文件数量
    function reloadFinishedCount() {
        $('#sp-finishedCount').text($('#finished-list tr').length);
    }

    //视图初始化
    function viewInit() {
        if (showType == 1) {
            $('.li-finished').hide();
        } else {
            getFile({
                bId: bId,	//业务id *
                fileType: fileType, //文件类型
                onSuccess: function (data) {
                    loadFinished(data.rows);		//数据回填 data字段包含fileName,id,fileSize （数据回填必须在show之后）
                },
            })
        }
    }

    //初始化提示
    function tipInit() {
        if (tip == null) {
            tip = '';
            if (fileCount != null) {
                tip = '文件上传不超过' + fileCount + '个';
            }
            if (fileCount != null && fileSize != null) {
                tip += '，';
            }
            if (fileSize != null) {
                var sizeStr = getFileSizeStr(fileSize);
                tip += '单个文件大小不超过' + sizeStr;
            }
        }
        $('#sp-fileTip').html(tip);
    }

    //绑定事件
    function eventInit() {
        //选择文件点击事件
        $('#btn-selectFile').click(function () {
            $('#btn-selectFile input')[0].click();
        })
        //点击确定事件
        $('#btn-yes').click(function () {
            //获取未处理的文件id
            var unfinishedId = [];
            $('tr[data-finished=0]').each(function () {
                unfinishedId.push($(this).attr('data-fileId'));
            });
            //返回已处理的文件id
            var result = [];
            $('tr[data-finished=1]').each(function () {
                var $tr = $(this);
                result.push({
                    name: $tr.find('.filename').text(),
                    path: $tr.attr('data-filePath'),
                    url: $tr.attr('data-fileUrl')
                });
            });
            if (onComplete != null) {
                onComplete(result);
            }
            if (unfinishedId.length > 0) {
                layer.confirm("存在未上传完成文件，确定后将丢失，是否确定？", function (i) {
                    deleteFiles(unfinishedId);
                    layer.close(i);
                    layer.close(winIndex);
                });
                return
            }
            layer.close(winIndex);
        });
        //点击取消事件
        $('#btn-allCancel').click(function () {
            var ids = [];
            $('#file-list tr').each(function () {
                ids.push($(this).attr('data-fileId'));
            })
            if (ids == null || ids.length < 1) {
                layer.close(winIndex);
                return;
            }
            layer.confirm("是否确定取消？（将移除当前文件）", function (i) {
                deleteFiles(ids);
                layer.close(winIndex);
                layer.close(i);
            });
        });
    }

    //初始化baseUploader
    function baseUploaderInit() {
        if (baseUploader != null) {
            baseUploader.destroy();
        }
        //baseUploader的事件
        var events = {
            onBeforeSendFile: onBeforeSendFile,  //文件上传前回调
            onBeforeSendBlock: onBeforeSendBlock, //分块上传前回调
            onAfterSendFile: onAfterSendFile,   //文件上传结束回调
            onBeforeFileQueued: onBeforeFileQueued,    //文件加入队列前触发，返回false则不加入队列
            onUploadProgress: onUploadProgress,      //上传过程进度条
            onFileStatusChange: onFileStatusChange,    //文件状态改变时
        }
        //baseUploader的上传参数
        var uploaderParam = {
            pick: '#btn-selectFile',	//选择文件的按钮id
            chunkSize: chunkSize,    //分片大小
            fileSize: fileSize,     //上传文件总大小
            server: uploadUrl,     //接收文件上传路径
            events: events  //事件
        }
        baseUploader = new BaseUploader(uploaderParam);
    }

    //文件上传前回调
    function onBeforeSendFile(file, md5) {
        var $file = $('#' + file.id);
        var postData = {
            basePath: baseUrl
        };
        if ($file.attr('data-tempDirPath') == null) {
            $.ajax({
                url: initUrl,
                type: "POST",
                async: false,
                data: postData,
                dataType: 'JSON',
                success: function (data) {
                    if (data.code != 1) {
                        console.log(data);
                        return;
                    }
                    $file.attr('data-fileDirPath', data.data.fileDirPath + '/' + file.name);
                    $file.attr('data-baseDirPath', data.data.fileDirPath);
                    $file.attr('data-tempDirPath', data.data.tempDirPath);
                },
                error: function (data) {
                    console.log(data);
                    baseUploader.stop(file);
                    file.setStatus('error');
                }
            });
        }
    }

    //分块上传前回调
    function onBeforeSendBlock(block, _this) {
        if (blockCheckUrl == null || blockCheckUrl == '') {
            return;
        }
        var $file = $('#' + block.file.id);
        var $tip = $file.find('.tip');
        var id = $file.attr('data-fileId');
        var postData = {
            chunk: block.chunk
            , chunkSize: block.end - block.start
            , tempPath: $file.attr('data-tempDirPath')
        }
        //上传文件前判断块是否存在
        //上传完成，提示后台合并
        var isExist = false;
        $.ajax({
            url: blockCheckUrl,
            type: "POST",
            async: false,
            data: postData,
            dataType: 'JSON',
            success: function (data) {
                if (data.code == 1 && data.data == true) {  //分块存在，跳过,跳过不能显示进度条
                    $tip.html('正在处理');
                    isExist = true;
                }
                if (data.code == 1 && data.data == false) {
                    //分块不存在或不完整，重新发送该分块内容 ;
                    $tip.html('上传中');
                }
                if (data.code != 1) {
                    baseUploader.stop(block.file);
                    block.file.setStatus('error');
                }
            },
            error: function (data) {
                console.log(data);
                baseUploader.stop(block.file);
                block.file.setStatus('error');
            }
        })
        _this.owner.options.formData.fileId = id;
        _this.owner.options.formData.chunk = block.chunk;
        _this.owner.options.formData.tempPath = $file.attr('data-tempDirPath');
        return isExist;
    }

    //文件上传结束回调
    function onAfterSendFile(file) {
        //页面提示
        var $file = $('#' + file.id)
        $file.find(params.tipClassName).html('正在处理');
        if (blockMergeUrl == null || blockMergeUrl == '') {
            console.log('blockMerge，无法合并文件');
            file.setStatus('error');
            return;
        }
        var postData = {
            tempPath: $file.attr('data-tempDirPath'),
            basePath: $file.attr('data-baseDirPath'),
            fileName: file.name
        };
        //上传完成，提示后台合并
        $.ajax({
            url: blockMergeUrl,
            type: "POST",
            async: false,
            data: postData,
            dataType: 'JSON',
            success: function (data) {
                if (data.code != 1) {
                    baseUploader.stop(file);
                    file.setStatus('error');
                    return;
                }
                $file.attr('data-finished', '1');    //标记文件为已完成
                $file.attr('data-filePath', data.data.path);
                $file.attr('data-fileUrl', data.data.url);
                onUploadProgress(file, 1);   //将进度条设置为100%
                if (onSuccessCall != null) {
                    onSuccessCall(file, data);
                }
            },
            error: function (data) {
                console.log(data);
                baseUploader.stop(file);
                file.setStatus('error');
            }
        });
    }

    //在加入文件到上传队列之前触发
    function onBeforeFileQueued(file) {
        if (fileAddBeforeCheck(file) == false) {
            baseUploader.cancel(file);
            return;
        }
        if ($('#' + file.id).length > 0) {  //当fileId重复时，可判断为断点续传
            return;
        }
        var sizeStr = getFileSizeStr(file.size);
        var html = uploadItemTemplet.format(file.id, file.name, sizeStr, file.size);
        $('#file-list').append(html);

        var $file = $('#' + file.id);
        bindFileItemEvent($file);
    }

    //文件上传中进度条显示
    function onUploadProgress(file, percentage) {
        var $item = $('#' + file.id);
        var nowPercentage = parseFloat($item.attr('data-nowPercentage'));
        if (percentage > nowPercentage) {
            $item.attr('data-nowPercentage', percentage);
            var $percent = $item.find('.percent');
            var $progressSize = $item.find('.sp-progressSize');
            var $progress = $item.find('.layui-progress-bar');
            $progressSize.text(getFileSizeStr(percentage * file.size));
            $percent.text(parseInt(percentage * 100) + '%');
            $progress.css('width', parseInt(percentage * 100) + '%');
        }
    }

    //文件状态改变时执行
    function onFileStatusChange(file, status) {
        var $file = $('#' + file.id);
        var $btn = $file.find('.btn-upload');
        var $btnCancel = $file.find('.btn-cancel');
        var $tip = $file.find('.tip');
        switch (status) {
            case 'queued':
                $tip.html('正在解析');
                $btn.text('暂停');
                break;
            case 'progress':
                $tip.html('等待上传');
                $btn.text('暂停');
                break;
            case 'interrupt':
                $tip.html('暂停');
                $btn.text('继续');
                break;
            case 'complete':
                $tip.text('完成')
                $btn.text('已完成');
                $btn.addClass('layui-btn-disabled');
                $btnCancel.text('删除');
                break;
            case 'error':
                $tip.html('<span style="color:red">上传失败</span>');
                $btn.text('重试');
                break;
        }
    }

    //在文件加入之前进行判断
    function fileAddBeforeCheck(file) {
        if (file.size < 1) {
            return false;
        }
        if (onBeforeAddFile != null) {
            if (onBeforeAddFile(file) == false) {
                return false;
            }
        }
        try {
            checkFileParam(file);
            checkFileCount(file);
            checkFileSize(file);
            checkFileTotalSize(file);
            checkFileType(file);
            return true;
        } catch (e) {
            layer.msg(e.message, {icon: 2});
            return false;
        }
        return true;
    }

    //检查文件参数
    function checkFileParam(file) {
        if (file.name == null || file.name == '' || file.name.length > 100) {
            throw new Error(winTip.fileNameLengthOver);
        }
    }

    //检查文件数量是否超出
    function checkFileCount(file) {
        var countLimit = fileCount - $('#finished-list tr').length - $('#file-list tr').length;
        if (countLimit < 1) {
            if (onFileCountOver != null) {
                onFileCountOver(file);
            }
            throw new Error(winTip.countOver);
        }
    }

    //检查文件大小是否超出
    function checkFileSize(file) {
        if (file.size > fileSize) { //文件大小超出
            if (onFileSizeOver != null) {
                onFileSizeOver(file);
            }
            throw new Error(winTip.sizeOver);
        }
    }

    //检查文件总大小是否超出
    function checkFileTotalSize(file) {
        if (totalFileSize != null) {
            //计算文件总大小
            var allSize = file.size;
            $('#finished-list tr').each(function () {
                var fileSize = parseInt($(this).attr('data-fileSize'));
                allSize += fileSize;
            });
            $('#file-list tr').each(function () {
                var fileSize = parseInt($(this).attr('data-fileSize'));
                allSize += fileSize;
            });
            if (totalFileSize < allSize) {  //总大小超出
                if (onTotalFileSizeOver != null) {
                    onTotalFileSizeOver(file);
                }
                throw new Error(winTip.totalSizeOver);
            }
        }
    }

    //检查文件类型
    function checkFileType(file) {
        if (fileType != null && fileType > 1) {
            var res = true;
            //2图片 3音频 4视频
            switch (fileType) {
                case 2:
                    res = checkFileIsImg(file.ext);
                    break;
                case 3:
                    res = checkFileIsVoice(file.ext);
                    break;
                case 4:
                    res == checkFileIsVideo(file.ext);
                    break;
            }
            if (!res) {
                throw new Error(winTip.fileTypeErr);
            }
        }
    }

    //检查图片格式
    function checkFileIsImg(ext) {
        return /(gif|jpg|jpeg|png)$/.test(ext.toLowerCase());
    }

    //判断是否是音频
    function checkFileIsVoice(ext) {
        return /(mp3|wma|flac|wav)$/.test(ext.toLowerCase());
    }

    //判断是否是视频
    function checkFileIsVideo(ext) {
        return /(mp4|3gp|avi|)$/.test(ext.toLowerCase());
    }

    //绑定单个文件事件
    function bindFileItemEvent($file) {
        //上传
        $file.find('.btn-upload').click(function () {
            var id = $file.attr('id');
            var file = baseUploader.getFile(id);
            if ($(this).hasClass('layui-btn-disabled')) {
                return;
            }
            switch (file.getStatus()) {
                case 'inited':
                    baseUploader.start(id);
                    break;
                case 'cancelled':
                    baseUploader.start(id);
                    break;
                case 'error':
                    baseUploader.start(file);
                    break;
                case 'interrupt':
                    baseUploader.start(file);
                    break;
                case 'queued':
                    baseUploader.stop(file);
                    break;
                case 'progress':
                    baseUploader.stop(file);
                    break;
            }
        });
        //取消
        $file.find('.btn-cancel').click(function () {
            var id = $file.attr('id');
            var file = baseUploader.getFile(id);
            baseUploader.cancel(id);
            //通知服务端删除
            deleteFile(id, $file.attr('data-tempDirPath'), $file.attr('data-fileDirPath'));
            var url = $file.attr('data-url');
            if (onCancelCall != null) {
                onCancelCall(file, url);
            }
            $file.remove();//ui上移除
        })
    }

    //获取文件大小文字提示
    function getFileSizeStr(fileSize) {
        var g = parseFloat(fileSize / 1024 / 1024 / 1024).toFixed(2);
        var m = parseFloat(fileSize / 1024 / 1024).toFixed(1);
        var k = parseInt(fileSize / 1024);
        var sizeStr = '';
        if (k > 0) {
            sizeStr = k + "KB";
        }
        if (m >= 1) {
            sizeStr = m + "M"
        }
        if (g >= 1) {
            sizeStr = g + "G";
        }
        if (sizeStr == '') {
            sizeStr = fileSize + "B";
        }
        return sizeStr;
    }

    return {
        show: show,     //展示组件
        deleteFile: deleteFile,  //删除文件
        downloadFile: downloadFile   //下载文件
    }
}