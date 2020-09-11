package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.ServiceProviderMakerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.RelMakerListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 服务商创客关联表 Mapper
 *
 * @author liangfeihu
 * @since 2020-08-19 16:01:29
 */
@Mapper
public interface ServiceProviderMakerMapper extends BaseMapper<ServiceProviderMakerEntity> {

    /**
     * 查询当前服务商的所有关联创客
     *
     * @param serviceProviderId
     * @param keyword
     * @param page
     * @return
     */
    List<RelMakerListVO> getServiceProviderMakers(Long serviceProviderId, String keyword, IPage<RelMakerListVO> page);
}

