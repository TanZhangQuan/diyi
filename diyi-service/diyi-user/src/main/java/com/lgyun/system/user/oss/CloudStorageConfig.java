package com.lgyun.system.user.oss;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 云存储配置信息
 *
 * @author tzq
 * @since 2018/8/2 16:48.
 */
@Data
@Component
@RefreshScope
@NoArgsConstructor
@ConfigurationProperties("aliyun.oss")
public class CloudStorageConfig {

    /**
     * 阿里云绑定的域名
     */
    private String domain ="https://diyi-cr.oss-cn-shanghai.aliyuncs.com";

    /**
     * 阿里云路径前缀
     */
    private String prefix ="upload";

    /**
     * 阿里云EndPoint
     */
    private String endPoint = "http://oss-cn-shanghai.aliyuncs.com";

    /**
     * 阿里云AccessKeyId
     */
    private String accessKeyId = "LTAI4GDMovvBWMpWk2VTDBpq";

    /**
     * 阿里云AccessKeySecret
     */
    private String accessKeySecret ="yO6t0rEOSxTr4tPSZ4w1ZxMy1n0Hmf";

    /**
     * 阿里云BucketName
     */
    private String bucketName ="diyi-cr";

}
