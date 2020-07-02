package com.lgyun.system.user.oss;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 云存储配置信息
 *
 * @author liangfeihu
 * @since 2018/8/2 16:48.
 */
@Data
@Component
@NoArgsConstructor
@ConfigurationProperties("aliyun.oss")
public class CloudStorageConfig {

    /**
     * 阿里云绑定的域名
     */
    private String domain;

    /**
     * 阿里云路径前缀
     */
    private String prefix;

    /**
     * 阿里云EndPoint
     */
    private String endPoint;

    /**
     * 阿里云AccessKeyId
     */
    private String accessKeyId;

    /**
     * 阿里云AccessKeySecret
     */
    private String accessKeySecret;

    /**
     * 阿里云BucketName
     */
    private String bucketName;

}
