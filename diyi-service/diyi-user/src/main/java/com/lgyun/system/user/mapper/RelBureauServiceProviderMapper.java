package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.RelBureauServiceProviderEntity;
import com.lgyun.system.user.vo.CooperationServiceProviderListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 相关局与服务商关联表 Mapper
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Mapper
public interface RelBureauServiceProviderMapper extends BaseMapper<RelBureauServiceProviderEntity> {

    /**
     * 查询产业园区合作服务商
     *
     * @param relBureauId
     * @param serviceProviderName
     * @param page
     * @return
     */
    List<CooperationServiceProviderListVO> queryCooperationServiceProviderList(Long relBureauId, String serviceProviderName, IPage<CooperationServiceProviderListVO> page);
}

