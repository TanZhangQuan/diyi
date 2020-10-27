package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.vo.AddressListVO;
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
     * 查询商户所有收货地址信息
     *
     * @param objectType
     * @param objectId
     * @param page
     * @return
     */
    List<AddressListVO> queryAddressList(ObjectType objectType, Long objectId, IPage<AddressListVO> page);
}

