package com.lgyun.common.tool;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author .
 * @date 2020/11/17.
 * @time 15:06.
 */
@Component
public class WordExportTest {


    public Map testWrite(String makerName,String templatePath,String idCard) throws Exception {
        InputStream is = getImgFromUrl(templatePath);
        HWPFDocument doc = new HWPFDocument(is);
        Range range = doc.getRange();
        //把range范围内的${reportDate}替换为当前的日期
        range.replaceText("${date}", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        range.replaceText("${name}", makerName);
        range.replaceText("${idCard}", idCard);
        // 生成的文件路径
        String UUIDStr = UUID.randomUUID().toString();
        File htmlFile = File.createTempFile(UUIDStr, ".pdf");
        OutputStream os = new FileOutputStream(htmlFile);
        //把doc输出到输出流中
        doc.write(os);
        this.closeStream(os);
        this.closeStream(is);
        FileInputStream fileInputStream = new FileInputStream(htmlFile);
        Map map = new HashMap();
        map.put("htmlFile", htmlFile);
        map.put("fileInputStream", fileInputStream);
        return map;
    }

    /**
     * 关闭输入流
     * @param is
     */
    private void closeStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     * @param os
     */
    private void closeStream(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public InputStream getImgFromUrl(String url) throws IOException {
        URL myUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
        con.setConnectTimeout(5 * 1000);
        InputStream is = con.getInputStream();
        return is;
    }
}
