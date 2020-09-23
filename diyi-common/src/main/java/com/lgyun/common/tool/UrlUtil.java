package com.lgyun.common.tool;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

/**
 * url处理工具类
 *
 * @author L.cm
 */
public class UrlUtil extends org.springframework.web.util.UriUtils {

    /**
     * url 编码，同js decodeURIComponent
     *
     * @param source  url
     * @param charset 字符集
     * @return 编码后的url
     */
    public static String encodeURL(String source, Charset charset) {
        return UrlUtil.encode(source, charset.name());
    }

    /**
     * url 解码
     *
     * @param source  url
     * @param charset 字符集
     * @return 解码url
     */
    public static String decodeURL(String source, Charset charset) {
        return UrlUtil.decode(source, charset.name());
    }

    /**
     * 查询url路径
     *
     * @param uriStr 路径
     * @return url路径
     */
    public static String getPath(String uriStr) throws URISyntaxException {
        URI uri = new URI(uriStr);
        return uri.getPath();
    }

}
