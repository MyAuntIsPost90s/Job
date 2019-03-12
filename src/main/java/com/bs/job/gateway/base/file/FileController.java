package com.bs.job.gateway.base.file;

import lingshi.gateway.model.RequestFile;
import lingshi.gateway.model.RequestHolder;
import lingshi.utilities.DateUtil;
import lingshi.valid.StringValid;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bs.job.gateway.base.BaseConstant;
import com.bs.job.gateway.uimodel.MinFile;
import com.bs.job.service.common.util.LogUtil;
import com.bs.job.service.common.util.UnidUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理接口
 *
 * @author caich
 **/
@RestController
@RequestMapping("/base/file")
public class FileController {

    /**
     * 上传文件
     *
     * @param
     * @return
     **/
    @RequestMapping("/upload")
    public void upload(HttpServletRequest request, HttpServletResponse response,String basePath){
        RequestHolder requestHolder = RequestHolder.get(request, response);
        if (StringValid.isNullOrWhiteSpace(basePath)) {
            requestHolder.fail("basePath不能为空");
            return;
        }
        if (!basePath.startsWith("/")) {
            basePath = "/" + basePath;
        }
        try {
            RequestFile requestFile = requestHolder.getRequestFile();
            if (requestFile.isEmpty()) {
                requestHolder.fail("上传文件不能为空");
                return;
            }
            basePath = BaseConstant.UPLOADER_BASE_PATH + '/' + DateUtil.format(DateUtil.YYYYMMDD) + basePath;
            List<MultipartFile> files = requestFile.getFiles();
            List<MinFile> result = new ArrayList<>();
            for (MultipartFile file : files) {
                MinFile minFile = new MinFile();
                minFile.setName(file.getOriginalFilename());
                minFile.setSize(file.getSize());
                minFile.setExt(BaseConstant.getExt(file.getOriginalFilename()));
                String path = basePath + "/" + UnidUtil.getId() + "." + minFile.getExt();
                minFile.setPath(path);
                minFile.setUrl(BaseConstant.getBaseUrl(request) + path);
                File saveFile = new File(requestHolder.getRealPathPath(minFile.getPath()));
                if (!saveFile.getParentFile().exists()) {
                    saveFile.getParentFile().mkdirs();
                }
                if (BaseConstant.isImage(minFile.getName())) {
                    Thumbnails.of(requestFile.getFile().getInputStream()).scale(1F).outputQuality(0.7).toFile(saveFile);
                } else {
                    file.transferTo(saveFile);
                }
                result.add(minFile);
            }
            requestHolder.success(result);
        } catch (Exception e) {
            LogUtil.e(e);
            requestHolder.fail(BaseConstant.OPER_FAILURE_CN);
        }
    }

}
