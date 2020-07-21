package com.lgyun.system.order.service;

import com.lgyun.common.api.R;
import org.springframework.web.multipart.MultipartFile;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public interface ICommonService {

    //上传文件
    R ossImageUpload(MultipartFile file) throws Exception;

}
