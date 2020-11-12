package com.lgyun.system.user.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.ICommonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    @Override
    public R<String> ossImageUpload(MultipartFile file, String suffix) throws Exception {

        if (file.isEmpty()) {
            return R.fail("上传文件不能为空");
        }

        // 查询上传文件的后缀
        String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).replace(".", "");

        //文件类型拆分
        if (StringUtils.isNotBlank(suffix)) {
            List<String> suffixList = new ArrayList<>();
            String[] split = suffix.split(",");
            for (int i = 0; i < split.length; i++) {
                if (StringUtils.isNotBlank(split[i])) {
                    suffixList.add(split[i]);
                }
            }

            if (!(suffixList.isEmpty())) {
                if (!(suffixList.contains(fileSuffix))) {
                    return R.fail("文件类型有误");
                }
            }
        }

        // 上传文件中
        String url = ossService.uploadSuffix(file.getBytes(), fileSuffix);

        //保存图片url信息
        return R.data(url);
    }

}
