package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.RelationshipType;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
@ApiModel(value = "MakerEnterpriseRelationVO对象", description = "MakerEnterpriseRelationVO对象")
public class MakerEnterpriseRelationVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //客户名称
    private String enterpriseName;

    //商户名字
    private String shopUserName;

    //法人名字
    private String legalPerson;

    //法人身份证
    private String legalPersonCard;

    //联系人职位
    private String contact1Position;

    //联系人电话
    private String contact1Phone;

    //营业执照编号
    private String socialCreditNo;

    //营业执照的url
    private String bizLicenceUrl;

    //企业id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    //0：关联，1关注，2：不关联不关注
    private RelationshipType relationshipType;

    //商户id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long shopId;

    //联系人1姓名
    private String contact1Name;

}
