package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.vo.EnterpriseWorkerDetailVO;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
@Mapper
public interface EnterpriseWorkerMapper extends BaseMapper<EnterpriseWorkerEntity> {

    /**
     * 查询当前商户员工详情
     *
     * @param enterpriseWorkerId
     * @return
     */
    EnterpriseWorkerDetailVO queryEnterpriseWorkerDetail(Long enterpriseWorkerId);

}

