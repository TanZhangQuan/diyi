package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.vo.EnterpriseMakerDetailVO;
import com.lgyun.system.user.vo.MakerEnterpriseNumIncomeVO;
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
     * 查询当前创客关联商户数和收入情况
     *
     * @param makerId1
     * @param makerId2
     * @return
     */
    MakerEnterpriseNumIncomeVO getEnterpriseNumIncome(Long makerId1, Long makerId2);

    /**
     * 根据创客ID获取创客详情
     *
     * @param enterpriseId
     * @param makerId
     * @return
     */
    EnterpriseMakerDetailVO getMakerDetailById(Long enterpriseId,Long makerId);
}

