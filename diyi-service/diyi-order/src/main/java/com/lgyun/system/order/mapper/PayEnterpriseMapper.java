package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.vo.InvoiceEnterpriseVO;
import com.lgyun.system.order.vo.PayEnterpriseStatisticalVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
@Mapper
public interface PayEnterpriseMapper extends BaseMapper<PayEnterpriseEntity> {

    /**
     * 根据创客id查询所有商户
     *
     * @param makerId
     * @param page
     * @return
     */
    List<InvoiceEnterpriseVO> getEnterpriseAll(Long makerId, IPage<InvoiceEnterpriseVO> page);

    /**
     * 根据创客id查询所有商户
     *
     * @param makerId
     * @param enterpriseId
     * @param page
     * @return
     */
    List<InvoiceEnterpriseVO> getEnterpriseMakerIdAll(Long makerId, Long enterpriseId, IPage<InvoiceEnterpriseVO> page);

    /**
     * 根据创客id查询所有商户
     *
     * @param makerId
     * @param enterpriseId
     * @param payMakerId
     * @return
     */
    InvoiceEnterpriseVO getEnterpriseMakerIdDetail(Long makerId, Long enterpriseId, Long payMakerId);

    /**
     * 获取交付清单统计数据
     *
     * @param enterpriseId
     * @return
     */
    PayEnterpriseStatisticalVO statistical(Long enterpriseId);
}

