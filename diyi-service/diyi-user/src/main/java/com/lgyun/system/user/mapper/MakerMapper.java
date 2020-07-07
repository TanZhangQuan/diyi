package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.entity.MakerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Mapper
public interface MakerMapper extends BaseMapper<MakerEntity> {

    /**
     * 根据微信openid获取创客
     *
     * @param openid
     * @return
     */
    MakerEntity findByOpenid(String openid);

    /**
     * 根据微信手机号码获取创客
     *
     * @param phoneNumber
     * @return
     */
    MakerEntity findByPhoneNumber(String phoneNumber);

    /**
     * 获取名称
     *
     * @param id
     * @return
     */
    String getName(Long id);

    /**
     * 根据userId获取创客
     *
     * @param userId
     * @return
     */
    MakerEntity findByUserId(Long userId);

}

