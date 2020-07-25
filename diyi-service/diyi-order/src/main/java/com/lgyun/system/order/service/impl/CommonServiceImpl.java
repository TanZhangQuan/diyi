package com.lgyun.system.order.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.system.order.oss.AliyunOssService;
import com.lgyun.system.order.service.ICommonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Slf4j
@Service
@AllArgsConstructor
public class CommonServiceImpl implements ICommonService {

    private AliyunOssService ossService;

    @Override
    public R<String> ossImageUpload(MultipartFile file) throws Exception {

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
