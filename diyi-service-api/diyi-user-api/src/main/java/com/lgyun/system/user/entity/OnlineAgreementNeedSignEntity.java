package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.enumeration.SignPower;
import com.lgyun.common.enumeration.SignState;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("diyi_online_agreement_need_sign")
public class OnlineAgreementNeedSignEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一性控制
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 平台在线协议模板ID
     */
    private Long onlineAgreementTemplateId;

    /**
     * 对象身份1、创客本人2、商户人员3、服务商人员4、相关局人员5、渠道商人员6、合伙人本人
     */
    private ObjectType objectType;

    /**
     * 签字对象性质 甲方；2，乙方；3，丙方；4，丁方
     */
    private SignPower signPower;

    /**
     * 对象ID 1、创客ID2、商户ID，具体签署时可能是某个用户3、服务商ID，具体签署时可能是某个用户4、相关局ID，具体签署时可能是某个用户5、渠道商ID，具体签署时可能是某个用户6、合伙人ID
     */
    private Long objectId;

    /**
     * 签署状态
     */
    private SignState signState;
}
