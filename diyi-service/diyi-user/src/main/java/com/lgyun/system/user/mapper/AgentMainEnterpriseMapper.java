package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.AgentMainEnterpriseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.CooperationEnterprisesListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 渠道商-商户关联表 Mapper
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
@Mapper
public interface AgentMainEnterpriseMapper extends BaseMapper<AgentMainEnterpriseEntity> {

    /**
     * 查询渠道商合作商户
     *
     * @param agentMainId
     * @param enterpriseName
     * @param page
     * @return
     */
    List<CooperationEnterprisesListVO> queryCooperationEnterpriseList(Long agentMainId, String enterpriseName, IPage<CooperationEnterprisesListVO> page);
}

