package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.entity.OrderEntity;
import com.lgyun.system.order.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author jun
 * @since 2020-06-26 16:57:54
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {

    /**
     * 自定义分页
     *
     * @param page
     * @return
     */
    List<OrderVO> selectOrderPage(IPage page, String orderState);

}

