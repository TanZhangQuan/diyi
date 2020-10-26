package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.system.entity.Dict;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service 接口
 *
 * @author tzq
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


    /**
     * 根据code查询字典数据
     * @param code
     * @return
     */
    R<List<Dict>> getDictList(String code);
}

