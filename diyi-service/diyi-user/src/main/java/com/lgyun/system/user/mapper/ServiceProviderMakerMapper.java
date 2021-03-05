package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.entity.ServiceProviderMakerEntity;
import com.lgyun.system.user.vo.ServiceProviderMakerVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 服务商创客关联表 Mapper
 *
 * @author tzq
 * @since 2020-08-19 16:01:29
 */
@Mapper
public interface ServiceProviderMakerMapper extends BaseMapper<ServiceProviderMakerEntity> {

    List<ServiceProviderMakerVO> queryCooperationServiceProviderList(Long makerId);
}

