package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.BureauType;
import com.lgyun.system.user.entity.RelBureauServiceProviderEntity;
import com.lgyun.system.user.vo.RelBureauServiceProviderVO;
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

    List<RelBureauServiceProviderVO> queryRelBureauServiceProvider(String serviceProviderName, BureauType bureauType, IPage<RelBureauServiceProviderVO> page);

    void removeById(Long bureauServiceProviderId);
}

