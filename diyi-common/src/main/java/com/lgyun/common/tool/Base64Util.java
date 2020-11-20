package com.lgyun.common.tool;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Base64工具
 *
 * @author tzq
 * @since 2020/6/5 18:00
 */
public class Base64Util extends org.springframework.util.Base64Utils {

    /**
     * 编码
     *
     * @param value 字符串
     * @return {String}
     */
    public static String encode(String value) {
        return Base64Util.encode(value, Charsets.UTF_8);
    }

    /**
     * 编码
     *
     * @param value   字符串
     * @param charset 字符集
     * @return {String}
     */
    public static String encode(String value, java.nio.charset.Charset charset) {
        byte[] val = value.getBytes(charset);
        return new String(Base64Util.encode(val), charset);
    }

    /**
     * 编码URL安全
     *
     * @param value 字符串
     * @return {String}
     */
    public static String encodeUrlSafe(String value) {
        return Base64Util.encodeUrlSafe(value, Charsets.UTF_8);
    }

    /**
     * 编码URL安全
     *
     * @param value   字符串
     * @param charset 字符集
     * @return {String}
     */
    public static String encodeUrlSafe(String value, java.nio.charset.Charset charset) {
        byte[] val = value.getBytes(charset);
        return new String(Base64Util.encodeUrlSafe(val), charset);
    }

    /**
     * 解码
     *
     * @param value 字符串
     * @return {String}
     */
    public static String decode(String value) {
        return Base64Util.decode(value, Charsets.UTF_8);
    }

    /**
     * 解码
     *
     * @param value   字符串
     * @param charset 字符集
     * @return {String}
     */
    public static String decode(String value, java.nio.charset.Charset charset) {
        byte[] val = value.getBytes(charset);
        byte[] decodedValue = Base64Util.decode(val);
        return new String(decodedValue, charset);
    }

    /**
     * 解码URL安全
     *
     * @param value 字符串
     * @return {String}
     */
    public static String decodeUrlSafe(String value) {
        return Base64Util.decodeUrlSafe(value, Charsets.UTF_8);
    }

    /**
     * 解码URL安全
     *
     * @param value   字符串
     * @param charset 字符集
     * @return {String}
     */
    public static String decodeUrlSafe(String value, java.nio.charset.Charset charset) {
        byte[] val = value.getBytes(charset);
        byte[] decodedValue = Base64Util.decodeUrlSafe(val);
        return new String(decodedValue, charset);
    }

    /**
     * 在线图片转换成base64字符串
     *
     * @param imgURL 图片线上路径
     * @return
     * @author ZHANGJL
     * @dateTime 2018-02-23 14:43:18
     */
    public static String imageToBase64ByOnline(String imgURL) throws Exception {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        // 创建URL
        URL url = new URL(imgURL);
        byte[] by = new byte[1024];
        // 创建链接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        InputStream is = conn.getInputStream();
        // 将内容读取内存中
        int len = -1;
        while ((len = is.read(by)) != -1) {
            data.write(by, 0, len);
        }
        // 关闭流
        is.close();
        // 对字节数组Base64编码
        return Base64Util.encodeToString(data.toByteArray());
    }

}
