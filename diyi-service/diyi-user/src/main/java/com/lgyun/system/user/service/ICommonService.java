package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public interface ICommonService {

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    R ossImageUpload(MultipartFile file) throws Exception;

}

