package com.lgyun.system.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgyun.auth.utils.RedisUtil;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.WechatConstant;
import com.lgyun.common.tool.AesCbcUtil;
import com.lgyun.common.tool.HttpUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.system.user.dto.MakerWechatLoginDto;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.ICommonService;
import com.lgyun.system.user.service.IMakerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommonServiceImpl implements ICommonService {
    private Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    private final IMakerService iMakerService;
    private final RedisUtil redisUtil;

    @Override
    public R wechatAuthorization(String code) throws IOException {

        Map<String, String> requestUrlParam = new HashMap<>();
        requestUrlParam.put("grant_type", "authorization_code");    //默认参数
        requestUrlParam.put("appid", WechatConstant.WECHAT_APPID);    //开发者设置中的appId
        requestUrlParam.put("secret", WechatConstant.WECHAT_SECRET);    //开发者设置中的appSecret
        requestUrlParam.put("js_code", code);    //小程序调用wx.login返回的code

        JSONObject jsonObject = JSON.parseObject(HttpUtil.post(WechatConstant.WECHAT_SESSIONHOST, requestUrlParam));
        if (jsonObject == null) {
            logger.error("微信授权失败, 获取数据失败");
            return R.fail("微信授权失败");
        }

        Object errcode = jsonObject.get("errcode");
        String errmsg = String.valueOf(jsonObject.get("errmsg"));
        if (errcode != null) {
            return R.fail(Integer.parseInt(String.valueOf(errcode)), errmsg);
        }

        String openid = String.valueOf(jsonObject.get("openid"));
        String sessionKey = String.valueOf(jsonObject.get("session_key"));
        if (StringUtils.isBlank(openid)) {
            logger.error("微信授权失败, 返回参数缺失openid");
            return R.fail("微信授权失败");
        }

        if (StringUtils.isBlank(sessionKey)) {
            logger.error("微信授权失败, 返回参数缺失session_key");
            return R.fail("微信授权失败");
        }

        //查看是否存在openid的创客，存在就不需要验证手机号，直接登录
        MakerEntity makerEntity = iMakerService.findByOpenid(openid);
        if (makerEntity != null) {
            //登陆操作
            //TODO

            return R.data(null);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("openid", openid);
        map.put("session_key", sessionKey);

        // 存入redis并设置过期时间为5分钟
        String random = UUID.randomUUID().toString();
        redisUtil.hmset(random, map, 300);

        return R.data(random);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R makerWechatLogin(MakerWechatLoginDto makerWechatLoginDto) throws Exception {

        //判断是否微信已授权
        String random = makerWechatLoginDto.getRandom();
        Map<Object, Object> map = redisUtil.hmget(random);
        if (CollectionUtils.isEmpty(map)) {
            return R.fail("微信未授权或已过期");
        }

        //解密数据
        String sessionKey = String.valueOf(map.get("session_key"));
        String iv = makerWechatLoginDto.getIv();
        String encryptedData = makerWechatLoginDto.getEncryptedData();
        // 参数含义：第一个，加密数据串（String）；第二个，session_key需要通过微信小程序的code获得（String）；
        // 第三个，数据加密时所使用的偏移量，解密时需要使用（String）；第四个，编码
        String result = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");

        if (StringUtil.isNotBlank(result)) {
            // 将解密后的JSON格式字符串转化为对象
            JSONObject jsonObject = JSONObject.parseObject(result);
            // 获取手机号
            String purePhoneNumber = String.valueOf(jsonObject.get("purePhoneNumber"));
            //根据手机号码查询创客，存在就更新微信信息，不存在就新建创客
            MakerEntity makerEntity = iMakerService.findByPhoneNumber(purePhoneNumber);
            String openid = String.valueOf(map.get("openid"));
            if (makerEntity != null) {
                //更新微信信息
                makerEntity.setOpenid(openid);
                makerEntity.setSessionKey(sessionKey);
                iMakerService.updateById(makerEntity);
            } else {
                //新建创客
                makerEntity = new MakerEntity();
                makerEntity.setOpenid(openid);
                makerEntity.setSessionKey(sessionKey);
                makerEntity.setPhoneNumber(purePhoneNumber);
                iMakerService.save(makerEntity);
            }

            //登陆
            //TODO

            return R.data(null);
        } else {
            return R.fail("解密失败");
        }

    }

}
