package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *  Entity
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
@Data
@NoArgsConstructor
@TableName("diyi_online_agreement_template")
public class OnlineAgreementTemplateEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一性控制
     */
        @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
        private Long id;

    /**
     * 协议类别 1,创客加盟协议；2，商户加盟协议；3，服务商加盟协议；4，渠道商加盟协议；5、合伙人加盟协议；6、园区合作协议；7、税局合作协议；8、工商合作协议；9、创客授权书；10、商户-创客补充协议；11、服务商-商户补充协议；12、创客单独税务事项委托授权书；13、创客单独支付事项委托授权书；14、其他协议
     */
        private Integer templateType;

    /**
     * 模板状态 1,应用中；2，已过期。同一个模板上传新模板后，原来的模板即为已过期
     */
        private Integer templateState;

    /**
     * 签署状态 1，开启中；2，已关闭
     */
        private Integer signState;

    /**
     * 协议模板
     */
        private String agreementTemplate;

    /**
     * 上传人员
     */
        private String uploadPerson;

    /**
     * 上传日期
     */
        private Date uploadDate;

    /**
     * 变更状态人员
     */
        private String changeStatePerson;

    /**
     * 变更日期
     */
        private Date shangeStateDate;

    /**
     * 商户全部创客 1,商户-创客，全部正常创客都需要签署,0 不是
     */
        private Integer allMakers;
    }
