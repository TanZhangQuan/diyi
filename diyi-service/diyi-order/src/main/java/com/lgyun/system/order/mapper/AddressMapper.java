package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.order.entity.AddressEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author jun
 * @since 2020-07-02 16:21:19
 */
@Mapper
public interface AddressMapper extends BaseMapper<AddressEntity> {
    /**
     * 通过购买方id查询收件地址
     */
    List<AddressEntity> findAddressCompanyId(Long companyId);
}

