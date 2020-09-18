package com.lgyun.system.user.mapper;

import com.lgyun.common.enumeration.PositionName;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.admin.QueryServiceProviderWorkerListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 服务商员工表 Mapper
 *
 * @author tzq
 * @since 2020-08-13 17:05:17
 */
@Mapper
public interface ServiceProviderWorkerMapper extends BaseMapper<ServiceProviderWorkerEntity> {

    /**
     * 查询服务商员工
     *
     * @param serviceProviderId
     * @param positionName
     * @return
     */
    List<QueryServiceProviderWorkerListVO> queryServiceProviderWorkerList(Long serviceProviderId, PositionName positionName);
}

