package com.lgyun.system.order.service;

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
    R<String> ossImageUpload(MultipartFile file) throws Exception;

    /**
     * 上传Excel文件
     *
     * @param file
     * @return
     */
    R<String> ossExcelUpload(MultipartFile file) throws Exception;
}
