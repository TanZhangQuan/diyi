package com.lgyun.common.tool;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author tzq
 * @date 2020/7/20.
 * @time 17:54.
 */
public class PDFUtil {

    /**
     * 添加图片
     */
    public Map addPdf(String pdfUrl, Integer pageSize, String signPic) throws Exception {
        InputStream input = getImgFromUrl(pdfUrl);
        // 生成的文件路径
        String UUIDStr = UUID.randomUUID().toString();

        File htmlFile = File.createTempFile(UUIDStr, ".pdf");
        // 读取模板文件
        PdfReader reader = new PdfReader(input);
        FileOutputStream fileOutputStream = new FileOutputStream(htmlFile);
        PdfStamper stamper = new PdfStamper(reader, fileOutputStream);
        Document document = new Document();
        // 通过域名查询所在页和坐标，左下角为起点
        float x = document.getPageSize().getWidth() - 440;
        float y = document.getPageSize().getHeight() - 480;
        // 读图片
        Image image = Image.getInstance(signPic);
        // 查询操作的页面
        PdfContentByte under = stamper.getOverContent(pageSize);
        // 根据域的大小缩放图片
        image.scaleToFit(document.getPageSize().getWidth() - 350, document.getPageSize().getHeight() - 290);
        // 添加图片
        image.setAbsolutePosition(x, y);
        under.addImage(image);
        stamper.close();
        reader.close();
        FileInputStream fileInputStream = new FileInputStream(htmlFile);
        Map map = new HashMap();
        map.put("htmlFile", htmlFile);
        map.put("fileInputStream", fileInputStream);
        return map;
    }

    public InputStream getImgFromUrl(String url) throws IOException {
        URL myUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
        con.setConnectTimeout(5 * 1000);
        InputStream is = con.getInputStream();
        return is;
    }
}
