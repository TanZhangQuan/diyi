package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.dto.PayEnterpriseMakerListDto;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.order.vo.PayEnterpriseMakersListVO;
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

    /**
     * 根据条件查询所有分包
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param payEnterpriseMakerListDto
     * @param page
     * @return
     */
    List<PayEnterpriseMakersListVO> getPayMakersByEnterprise(Long enterpriseId, Long serviceProviderId, PayEnterpriseMakerListDto payEnterpriseMakerListDto, IPage<PayEnterpriseMakersListVO> page);
}

