package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.system.order.entity.AddressEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Mapper
public interface AddressMapper extends BaseMapper<AddressEntity> {

    /**
     * 通过创客Id去查询
     */
    List<AddressEntity> findAddressMakerId(IPage page, Long makerId);
}

