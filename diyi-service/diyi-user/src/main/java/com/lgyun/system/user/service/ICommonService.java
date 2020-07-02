package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.system.user.dto.MakerLoginDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public interface ICommonService {

    //微信授权
    R wechatAuthorization(String code) throws IOException;

    //创客登陆
    R makerLogin(MakerLoginDto makerLoginDto) throws Exception;

    //上传图片
    R imageUpload(MultipartFile file);
}

