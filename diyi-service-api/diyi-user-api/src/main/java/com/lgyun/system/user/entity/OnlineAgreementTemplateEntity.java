package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.TemplateSignState;
import com.lgyun.common.enumeration.TemplateState;
import com.lgyun.common.enumeration.TemplateType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_online_agreement_template")
public class OnlineAgreementTemplateEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 协议类别
     */
    private AgreementType agreementType;

    /**
     * 模板状态 1,应用中；2，已过期。同一个模板上传新模板后，原来的模板即为已过期
     */
    private TemplateState templateState = TemplateState.APPLICATION;

    /**
     * 1，开启中；2，已关闭
     */
    private TemplateSignState templateSignState = TemplateSignState.OPEN;

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
     * 是否需要全部创客签署
     */
    private Boolean boolAllMakers;

    /**
     * 模板的页数
     */
    private Integer templateCount;

    /**
     * 模板类型
     */
    private TemplateType templateType;
}
