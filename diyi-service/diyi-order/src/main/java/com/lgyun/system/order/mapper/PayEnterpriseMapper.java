package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.vo.InvoiceEnterpriseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
@Mapper
public interface PayEnterpriseMapper extends BaseMapper<PayEnterpriseEntity> {

    /**
     * 根据创客id查询所有商户
     */
    List<InvoiceEnterpriseVO> getEnterpriseAll(Long makerId, IPage page);

    /**
     * 根据创客id查询所有商户
     */
    List<InvoiceEnterpriseVO> getEnterpriseMakerIdAll(Long makerId,Long enterpriseId,IPage page);

    /**
     * 根据创客id查询所有商户
     */
    InvoiceEnterpriseVO getEnterpriseMakerIdDetail(Long makerId,Long enterpriseId,Long payMakerId);
}

