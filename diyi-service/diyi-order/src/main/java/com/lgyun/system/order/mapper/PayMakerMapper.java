package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.dto.PayListDto;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.order.vo.PayListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
@Mapper
public interface PayMakerMapper extends BaseMapper<PayMakerEntity> {

    List<PayListVO> getByDtoEnterprise(Long enterpriseId, PayListDto payListDto, IPage<PayListVO> page);
}

