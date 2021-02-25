package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.EnterpriseRule;
import com.lgyun.common.enumeration.MakerRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@ApiModel(description = "修改或编辑服务商业务规则DTO")
public class AddOrUpdateServiceProviderRuleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商-创客业务规则")
    private Set<MakerRule> makerRuleSet;

    @ApiModelProperty(value = "服务商-商户业务规则")
    private Set<EnterpriseRule> enterpriseRuleSet;

}
