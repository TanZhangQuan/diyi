package com.lgyun.common.tool;

import com.alibaba.fastjson.JSONObject;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.RealnameVerifyConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实名认证工具
 *
 * @author tzq
 * @since 2020/6/27 18:00
 */
public class RealnameVerifyUtil {
    private static Logger logger = LoggerFactory.getLogger(RealnameVerifyUtil.class);

    /**
     * @return 返回token
     */
    public static String getToken() {
        logger.info("-----------获取Token------------");
        String res = RealnameVerifyHttpUtil.sendGet(RealnameVerifyConstant.getTokenUrl);
        JSONObject result = JSONObject.parseObject(res);
        int code = result.getInteger("code");
        if (code != 0) {
            return null;
        }
        JSONObject data = result.getJSONObject("data");
        String token = data.getString("token");
        logger.info("获得的token是:======== ");
        logger.info(token);
        logger.info("============================");
        return token;
    }

    /**
     * 身份证实名认证
     */
    public static R IdCardOCR(String infoImg, String emblemImg) {
        try {

            //获取实名认证token
            String token = getToken();
            if (StringUtil.isBlank(token)) {
                return R.fail("实名鉴权失败");
            }

            //整理请求参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("infoImg", infoImg);
            jsonObject.put("emblemImg", emblemImg);
            String params = JSONObject.toJSONString(jsonObject);

            //实名认证请求
            String res = RealnameVerifyHttpUtil.sendPost(RealnameVerifyConstant.idCardOCRUrl, token, params);
            JSONObject result = JSONObject.parseObject(res);
            int code = result.getInteger("code");
            String msg = result.getString("message");
            if (code != 0) {
                return R.fail(code, msg);
            }
            JSONObject data = result.getJSONObject("data");
            logger.info(data.toJSONString());

            return R.data(data.toJSONString());
        } catch (Exception e) {
            logger.error("实名认证异常", e);
        }
        return R.fail("实名认证失败");
    }
}
