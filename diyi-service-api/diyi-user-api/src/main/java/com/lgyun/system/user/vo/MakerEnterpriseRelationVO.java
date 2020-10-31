package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.common.enumeration.RelationshipType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tzq
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
public class MakerEnterpriseRelationVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 商户名字
     */
    private String shopUserName;

    /**
     * 法人名字
     */
    private String legalPersonName;

    /**
     * 法人身份证
     */
    private String legalPersonIdcard;

    /**
     * 联系人职位
     */
    private PositionName contact1Position;

    /**
     * 联系人电话
     */
    private String contact1Phone;

    /**
     * 营业执照编号
     */
    private String socialCreditNo;

    /**
     * 营业执照
     */
    private String bizLicenceUrl;

    /**
     * 企业ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 创客商户关系
     */
    private RelationshipType relationshipType;

    /**
     * 商户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long shopId;

    /**
     * 联系人1姓名
     */
    private String contact1Name;

}
