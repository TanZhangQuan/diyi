package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.vo.MakerEnterpriseNumIncomeVO;
import com.lgyun.system.user.vo.MakerInfoVO;
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
     * 根据微信手机号码获取创客
     *
     * @param phoneNumber
     * @return
     */
    MakerEntity findByPhoneNumber(String phoneNumber);

    /**
     * 根据微信手机号码获取创客
     *
     * @param phoneNumber
     * @return
     */
    MakerEntity findByPhoneNumberAndLoginPwd(String phoneNumber, String loginPwd);

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

    /**
     * 获取当前创客基本信息
     *
     * @param
     * @return
     */
    MakerInfoVO getInfo(Long makerId);

    /**
     * 查询当前创客关联商户数和收入情况
     *
     * @param
     * @return
     */
    MakerEnterpriseNumIncomeVO getEnterpriseNumIncome(Long makerId1, Long makerId2);
}

