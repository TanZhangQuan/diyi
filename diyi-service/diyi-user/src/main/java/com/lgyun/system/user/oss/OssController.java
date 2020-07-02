package com.lgyun.system.user.oss;

import com.lgyun.common.api.R;
import com.lgyun.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 *
 * @author liangfeihu
 * @since 2018/8/2 16:41.
 */
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private AliyunOssService ossService;


    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new ServiceException("上传文件不能为空");
        }

        // 获取上传文件的后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        // 上传文件中
        String url = ossService.uploadSuffix(file.getBytes(), suffix);

        //保存图片url信息

        return R.data(url);
    }


}
