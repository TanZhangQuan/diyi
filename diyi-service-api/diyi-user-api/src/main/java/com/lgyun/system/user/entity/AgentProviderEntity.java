package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 渠道商人员表 Entity
 *
 * @author tzq
 * @since 2020-10-20 18:25:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_agent_main_service_provider")
public class AgentProviderEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 渠道商ID
     */
    private Long agentMainId;

    /**
     * 服务商id
     */
    private Long serviceProviderId;

    /**
     * 分配日期
     */
    private Date matchDate;

    /**
     * 分配人员
     */
    private String matchPerson;

    /**
     * 分配说明
     */
    private String matchDesc;

    /**
     * 合作状态：合作中，停止合作；首次关联时默认为合作中
     */
    private CooperateStatus cooperateStatus;

    /**
     * 合作日期
     */
    private Date startDatetime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 暂停日期
     */
    private Date pauseDatetime;

    /**
     * 停止日期
     */
    private Date stopDatetime;
}
