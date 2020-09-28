package com.lgyun.system.user.mapper;

import com.lgyun.common.enumeration.PositionName;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.admin.EnterpriseWorkerListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
@Mapper
public interface EnterpriseWorkerMapper extends BaseMapper<EnterpriseWorkerEntity> {

    /**
     * 查询商户员工
     *
     * @param enterpriseId
     * @param positionName
     * @return
     */
    List<EnterpriseWorkerListVO> queryEnterpriseWorkerList(Long enterpriseId, PositionName positionName);

}

