package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.RelationshipType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class MakerEnterpriseRelationVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "店铺名称")
    private String shopUserName;

    @ApiModelProperty(value = "法人名字")
    private String legalPersonName;

    @ApiModelProperty(value = "法人身份证")
    private String legalPersonIdcard;

    @ApiModelProperty(value = "联系人职位")
    private String contact1Position;

    @ApiModelProperty(value = "联系人电话")
    private String contact1Phone;

    @ApiModelProperty(value = "营业执照编号")
    private String socialCreditNo;

    @ApiModelProperty(value = "营业执照")
    private String bizLicenceUrl;

    @ApiModelProperty(value = "企业ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    @ApiModelProperty(value = "创客商户关系")
    private RelationshipType relationshipType;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long shopId;

    @ApiModelProperty(value = "联系人1姓名")
    private String contact1Name;

}
