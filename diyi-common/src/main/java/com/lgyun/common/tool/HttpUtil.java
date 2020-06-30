package com.lgyun.common.tool;

import com.alibaba.fastjson.JSON;
import com.lgyun.common.enumeration.HttpRequestMethedEnum;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    /**
     * @author chenxi
     */
    //日志记录器
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 15000;
    private static final int MAX_TOTAL = 3;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(MAX_TOTAL);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final int CONNECT_TIME_OUT = 5000; //链接超时时间5秒

    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom().setConnectTimeout(CONNECT_TIME_OUT).build();

    /**
     * @param url    请求地址
     * @param params 参数
     * @return 请求失败返回null
     * @description 功能描述: get 请求
     */
    public static String get(String url, Map<String, String> params) throws IOException {

        CloseableHttpClient httpClient = null;
        if (params != null && !params.isEmpty()) {
            StringBuffer param = new StringBuffer();
            boolean flag = true; // 是否开始
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (flag) {
                    param.append("?");
                    flag = false;
                } else {
                    param.append("&");
                }
                param.append(entry.getKey()).append("=");
                param.append(URLEncoder.encode(entry.getValue(), DEFAULT_CHARSET));
            }
            url += param.toString();
        }

        String body;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.custom().setDefaultRequestConfig(REQUEST_CONFIG).build();
            HttpGet httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
        } finally {
            if (response != null) {
                response.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return body;
    }

    /**
     * @param url 请求地址
     * @return 请求失败返回null
     * @description 功能描述: get 请求
     */
    public static String get(String url) throws IOException {
        return get(url, null);
    }

    /**
     * @param url    请求地址
     * @param params 参数
     * @return 请求失败返回null
     * @description 功能描述: post 请求
     */
    public static String post(String url, Map<String, String> params) throws IOException {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        String body;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.custom().setDefaultRequestConfig(REQUEST_CONFIG).build();
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, DEFAULT_CHARSET));
            response = httpClient.execute(httpPost);
            body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
        } finally {
            if (response != null) {
                response.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return body;
    }

    /**
     * @param url 请求地址
     * @param s   参数xml
     * @return 请求失败返回null
     * @description 功能描述: post 请求
     */
    public static String post(String url, String s) throws IOException {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = new HttpPost(url);
        String body;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.custom().setDefaultRequestConfig(REQUEST_CONFIG).build();
            httpPost.setEntity(new StringEntity(s, DEFAULT_CHARSET));
            response = httpClient.execute(httpPost);
            body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
        } finally {
            if (response != null) {
                response.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return body;
    }

    /**
     * 发送http请求
     *
     * @param requestMethod 请求方式（HttpGet、HttpPost、HttpPut、HttpDelete）
     * @param url           请求路径
     * @param header        请求头
     * @param params        post请求参数
     * @param isHttps       int :1为是https方式，0为http方式
     * @return 响应文本
     */
    public static String sendHttp(HttpRequestMethedEnum requestMethod, String url, Map<String, String> header, String params, int isHttps) {
        logger.info("开始发送：=============");
        logger.info("请求头: ================");
        long beginTime = new Date().getTime();
        //1、创建一个HttpClient对象;
        CloseableHttpClient httpClient;
        if (isHttps == 1) {
            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setConnectionManagerShared(true).setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
        CloseableHttpResponse httpResponse = null;
        String responseContent = null;

        //2、创建一个Http请求对象并设置请求的URL，比如GET请求就创建一个HttpGet对象，POST请求就创建一个HttpPost对象;
        HttpRequestBase request = requestMethod.createRequest(url);
        request.setConfig(requestConfig);
        logger.info("发送Http请求方法：" + request.getMethod());
        //3、如果需要可以设置请求对象的请求头参数，也可以往请求对象中添加请求参数;

        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                //打印一下，以便postman:
                logger.info(entry.getKey() + "      :    " + entry.getValue());
                request.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 往对象中添加相关参数
        try {
            if (params != null) {
                ((HttpEntityEnclosingRequest) request).setEntity(
                        new StringEntity(params, ContentType.create("application/json", "utf-8")));
            }
            //4、调用HttpClient对象的execute方法执行请求;
            httpResponse = httpClient.execute(request);
            //5、获取请求响应对象和响应Entity;
            HttpEntity httpEntity = httpResponse.getEntity();
            logger.info("发送地址：" + request.getURI());
            logger.info("发送内容：" + params);
            logger.info("响应状态：" + httpResponse.getStatusLine());
            //6、从响应对象中获取响应状态，从响应Entity中获取响应内容;
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, "UTF-8");
                logger.info("响应内容：" + responseContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //7、关闭响应对象;
                if (httpResponse != null) {
                    httpResponse.close();
                }
                //8、关闭HttpClient.
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long endTime = new Date().getTime();
        logger.info("该接口响应时间:   " + (endTime - beginTime));
        return responseContent;
    }


    /**
     * 发送http请求
     *
     * @param requestMethod 请求方式（HttpGet、HttpPost、HttpPut、HttpDelete）
     * @param url           请求路径
     * @param header        请求头
     * @param params        post请求参数
     * @param isHttps       int :1为是https方式，0为http方式
     * @return 响应文本
     */
    public static String sendHttp2(HttpRequestMethedEnum requestMethod, String url, Map<String, String> header, Map<String, Object> params, int isHttps) {
        logger.info("开始发送：=============");
        logger.info("请求头: ================");
        long beginTime = new Date().getTime();
        //1、创建一个HttpClient对象;
        CloseableHttpClient httpClient = null;
        if (isHttps == 1) {
            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setConnectionManagerShared(true).setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
        CloseableHttpResponse httpResponse = null;
        String responseContent = null;
        //2、创建一个Http请求对象并设置请求的URL，比如GET请求就创建一个HttpGet对象，POST请求就创建一个HttpPost对象;
        HttpRequestBase request = requestMethod.createRequest(url);
        request.setConfig(requestConfig);
        logger.info("发送Http请求方法：" + request.getMethod());
        //3、如果需要可以设置请求对象的请求头参数，也可以往请求对象中添加请求参数;
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                //打印一下，以便postman:
                logger.info(entry.getKey() + "      :    " + entry.getValue());
                request.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 往对象中添加相关参数
        try {
            if (params != null) {
                ((HttpEntityEnclosingRequest) request).setEntity(
                        new StringEntity(JSON.toJSONString(params),
                                ContentType.create("application/json", "UTF-8")));
            }
            logger.info("发送地址：" + request.getURI());
            logger.info("发送内容：" + JSON.toJSONString(params));
            //4、调用HttpClient对象的execute方法执行请求;
            httpResponse = httpClient.execute(request);
            logger.info("响应状态：" + httpResponse.getStatusLine());
            //5、获取请求响应对象和响应Entity;
            HttpEntity httpEntity = httpResponse.getEntity();
            //6、从响应对象中获取响应状态，从响应Entity中获取响应内容;
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, "UTF-8");
                logger.info("响应内容：" + responseContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //7、关闭响应对象;
                if (httpResponse != null) {
                    httpResponse.close();
                }
                //8、关闭HttpClient.
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long endTime = new Date().getTime();
        logger.info("该接口响应时间:   " + (endTime - beginTime));
        return responseContent;
    }
    //发送httpput,实现文件流的上传

    /**
     * @param url       请求地址
     * @param filebytes 文件流
     * @param header    请求头
     * @param isHttps   int :1为是https方式，0为http方式
     * @return
     */
    public static String sendHttpput(String url, byte[] filebytes, Map<String, String> header, int isHttps) {
        logger.info("开始发送：=============");
        logger.info("发送地址: " + url);
        //计时开始
        long beginTime = new Date().getTime();
        //固定是httpput方式
        HttpRequestMethedEnum requestMethod = HttpRequestMethedEnum.HttpPut;
        //1、创建一个HttpClient对象;
        CloseableHttpClient httpClient = null;
        if (isHttps == 1) {
            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setConnectionManagerShared(true).setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = HttpClients.createDefault();
        }

        CloseableHttpResponse httpResponse = null;
        String responseContent = null;
        //2、创建一个Http请求对象并设置请求的URL，比如GET请求就创建一个HttpGet对象，POST请求就创建一个HttpPost对象;
        HttpRequestBase request = requestMethod.createRequest(url);
        request.setConfig(requestConfig);
        logger.info("发送Http请求方法：" + request.getMethod());
        //3、如果需要可以设置请求对象的请求头参数，也可以往请求对象中添加请求参数;

        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                logger.info(entry.getKey() + "      :    " + entry.getValue());
                request.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 往对象中添加相关参数
        try {
            if (filebytes != null) {
                ((HttpEntityEnclosingRequest) request).setEntity(new ByteArrayEntity(filebytes));
            }
//			request.removeHeader(request.getFirstHeader("Content-Length"));
            //4、调用HttpClient对象的execute方法执行请求;
            httpResponse = httpClient.execute(request);
            logger.info("响应内容:====================");
//			logger.info(httpResponse);
            //5、获取请求响应对象和响应Entity;
            HttpEntity httpEntity = httpResponse.getEntity();
            logger.info("响应状态：" + httpResponse.getStatusLine());
            //6、从响应对象中获取响应状态，从响应Entity中获取响应内容;
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, "UTF-8");
                logger.info("响应内容：" + responseContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //7、关闭响应对象;
                if (httpResponse != null) {
                    httpResponse.close();
                }
                //8、关闭HttpClient.
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long endTime = new Date().getTime();
        logger.info("该接口响应时间:   " + (endTime - beginTime));
        return responseContent;
    }

    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

}


