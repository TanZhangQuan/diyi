package com.lgyun.system.user.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.ICommonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommonServiceImpl implements ICommonService {
    private Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    private final AliyunOssService ossService;

    @Override
    public R ossImageUpload(MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            return R.fail("上传文件不能为空");
        }
        // 获取上传文件的后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 上传文件中
        String url = ossService.uploadSuffix(file.getBytes(), suffix);
        //保存图片url信息
        return R.data(url);
    }

}
