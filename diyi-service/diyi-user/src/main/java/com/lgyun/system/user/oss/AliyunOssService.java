package com.lgyun.system.user.oss;

import com.aliyun.oss.OSSClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 阿里云存储
 *
 * @author tzq
 * @since 2018/8/2 16:46.
 */
@Slf4j
@Service
public class AliyunOssService {
    private OSSClient client;

    @Autowired
    private CloudStorageConfig config;

    public AliyunOssService(CloudStorageConfig config) {
        this.config = config;
        //初始化
        init();
    }

    private void init() {
        client = new OSSClient(config.getEndPoint(), config.getAccessKeyId(),
                config.getAccessKeySecret());
    }

    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getPrefix(), suffix));
    }

    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getPrefix(), suffix));
    }

    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    public String upload(InputStream inputStream, String path) {
        client.putObject(config.getBucketName(), path, inputStream);
        return config.getDomain() + "/" + path;
    }

    /**
     * 文件路径
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    private String getPath(String prefix, String suffix) {
        //生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //文件路径
        String path = DateFormatUtils.format(new Date(), "yyyyMMdd") + "/" + uuid;

        if (StringUtils.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }

        return path + suffix;
    }

}
