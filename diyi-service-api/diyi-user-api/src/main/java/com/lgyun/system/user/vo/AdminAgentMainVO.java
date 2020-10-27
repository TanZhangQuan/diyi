package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.AccountState;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author .
 * @date 2020/10/20.
 * @time 16:34.
 */
@Data
public class AdminAgentMainVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long agentMainId;

    /**
     * 渠道商名称
     */
    private String enterpriseName;

    /**
     * 联系人1名称
     */
    private String contact1Name;

    /**
     * 联系人1电话
     */
    private String contact1Phone;

    /**
     * 加盟合同
     */
    private String franchiseContract;

    /**
     * 渠道承诺函
     */
    private String channelCommitmentLetter;

    /**
     * 状态
     */
    private AccountState agentState=AccountState.NORMAL;

    /**
     * 创建时间
     */
    private Date createTime;
}