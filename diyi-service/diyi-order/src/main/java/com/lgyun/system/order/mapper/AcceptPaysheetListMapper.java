package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.order.entity.AcceptPaysheetListEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商户支付回单表 Mapper
 *
 * @author liangfeihu
 * @since 2020-10-29 19:55:38
 */
@Mapper
public interface AcceptPaysheetListMapper extends BaseMapper<AcceptPaysheetListEntity> {

    /**
     * 查询总包支付清单的交付支付验收单关联记录ID
     *
     * @param payEnterpriseId
     */
    List<Long> queryAcceptPaysheetIdList(Long payEnterpriseId);

    /**
     * 删除交付支付验收单关联记录
     *
     * @param acceptPaysheetId
     */
    void deleteAcceptPaysheetList(Long acceptPaysheetId);

}

