package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.order.vo.TransactionMonthVO;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.vo.EnterpriseStatisticalVO;
import com.lgyun.system.user.vo.EnterprisesDetailVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Mapper
public interface EnterpriseMapper extends BaseMapper<EnterpriseEntity> {

    /**
     * 获取商户首页统计数据
     *
     * @param enterpriseId
     * @return
     */
    EnterpriseStatisticalVO statistical(Long enterpriseId);

    /**
     * 获取商户交易金额
     *
     * @param enterpriseId
     * @return
     */
    TransactionMonthVO queryEnterprisePayMoney(Long enterpriseId);

    /**
     * 根据商户ID获取商户详情
     *
     * @param enterpriseId
     * @return
     */
    EnterprisesDetailVO getEnterpriseDetailById(Long enterpriseId);
}

