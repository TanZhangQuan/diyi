package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.dto.PayEnterpriseListDto;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.order.vo.PayEnterpriseListVO;
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

    List<PayEnterpriseListVO> getByDtoEnterprise(Long enterpriseId, PayEnterpriseListDto payEnterpriseListDto, IPage<PayEnterpriseListVO> page);
}

