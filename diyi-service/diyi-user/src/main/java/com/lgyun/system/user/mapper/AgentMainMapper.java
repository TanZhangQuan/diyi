package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.AgentMainListDTO;
import com.lgyun.system.user.entity.AgentMainEntity;
import com.lgyun.system.user.vo.*;
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

    /**
     * 查询渠道商基本信息
     *
     * @param agentMainId
     * @return
     */
    AgentMainInfoVO queryEnterpriseInfo(Long agentMainId);

    /**
     * 查询当前渠道商所有联系人
     *
     * @param agentMainId
     * @return
     */
    ContactInfoVO queryContact(Long agentMainId);

    /**
     * 查询当前渠道商的开票信息
     *
     * @param agentMainId
     * @return
     */
    InvoiceVO queryeInvoice(Long agentMainId);
}

