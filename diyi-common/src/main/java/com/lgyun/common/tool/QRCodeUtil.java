package com.lgyun.common.tool;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * @author jun.
 * @date 2020/4/8.
 * @time 16:08.
 */
@Component
public class QRCodeUtil {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";

    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;

    private BufferedImage createImage(String content) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        return image;
    }


    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content 内容
     * @throws Exception
     */
    public Map encode(String content) throws Exception {
        BufferedImage image = this.createImage(content);
        String fileName = UUID.randomUUID().toString();
        File htmlFile = File.createTempFile(fileName,".jpg");
        try {
            ImageIO.write(image, FORMAT_NAME, htmlFile);
            FileInputStream fileInputStream = new FileInputStream(htmlFile);
            Map map = new HashMap();
            map.put("htmlFile", htmlFile);
            map.put("fileInputStream", fileInputStream);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
