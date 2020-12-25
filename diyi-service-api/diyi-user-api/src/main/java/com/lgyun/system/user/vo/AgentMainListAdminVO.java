package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "XXXXXX")
public class AgentMainListAdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道商ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "渠道商名称")
    private String agentMainName;

    @ApiModelProperty(value = "联系人名称")
    private String contact1Name;

    @ApiModelProperty(value = "联系人电话")
    private String contact1Phone;

    @ApiModelProperty(value = "加盟合同")
    private String joinContract;

    @ApiModelProperty(value = "商户业务真实性承诺函(多张)")
    private String commitmentLetters;

    @ApiModelProperty(value = "状态")
    private AccountState agentMainState;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
