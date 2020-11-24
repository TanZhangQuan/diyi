package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.AgentMainListDTO;
import com.lgyun.system.user.entity.AgentMainEntity;
import com.lgyun.system.user.vo.AgentMainListAdminVO;
import com.lgyun.system.user.vo.AgentMainUpdateDetailVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 渠道商信息表 Mapper
 *
 * @author tzq
 * @since 2020-10-20 18:25:03
 */
@Mapper
public interface AgentMainMapper extends BaseMapper<AgentMainEntity> {

    /**
     * 查询所有渠道商列表
     *
     * @param agentMainListDTO
     * @param page
     * @return
     */
    List<AgentMainListAdminVO> getAgentMainListAdmin(AgentMainListDTO agentMainListDTO, IPage<AgentMainListAdminVO> page);

    /**
     * 查询编辑渠道商详情
     *
     * @param agentMainId
     * @return
     */
    AgentMainUpdateDetailVO queryAgentMainUpdateDetail(Long agentMainId);

}

