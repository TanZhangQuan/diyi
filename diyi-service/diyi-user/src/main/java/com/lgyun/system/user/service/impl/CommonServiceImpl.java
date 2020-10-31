package com.lgyun.system.user.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.system.entity.Dict;
import com.lgyun.system.feign.IDictClient;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.ICommonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-06-26 17:21:06
 */
@Slf4j
@Service
@AllArgsConstructor
public class CommonServiceImpl implements ICommonService {

    private AliyunOssService ossService;
    private IDictClient dictClient;

    @Override
    public R<String> ossImageUpload(MultipartFile file) throws Exception {

        //判断文件内容是否为空
        if (file.isEmpty()) {
            return R.fail("上传文件内容为空");
        }

        // 查询上传文件的后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        // 上传文件中
        String url = ossService.uploadSuffix(file.getBytes(), suffix);

        //保存图片url信息
        return R.data(url);
    }

    @Override
    public R<String> ossExcelUpload(MultipartFile file) throws Exception {

        //判断文件内容是否为空
        if (file.isEmpty()) {
            return R.fail("上传文件内容为空");
        }

        // 查询上传文件的后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        if ((!StringUtils.endsWithIgnoreCase(suffix, ".xls") && !StringUtils.endsWithIgnoreCase(suffix, ".xlsx"))) {
            return R.fail("请选择Excel文件");
        }

        // 上传文件中
        String url = ossService.uploadSuffix(file.getBytes(), suffix);

        //保存图片url信息
        return R.data(url);
    }

    @Override
    public R<List<Dict>> getDictList(String code) {
        if (code.isEmpty()) {
            return R.fail("请先输入需要查询的code");
        }
        return dictClient.getList(code);
    }

}
