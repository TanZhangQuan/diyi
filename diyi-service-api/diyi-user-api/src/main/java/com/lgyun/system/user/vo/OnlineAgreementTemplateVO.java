package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.OnlineAgreementTemplateEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jun.
 * @date 2020/7/18.
 * @time 14:49.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OnlineAgreementTemplateVO对象", description = "OnlineAgreementTemplateVO对象")
public class OnlineAgreementTemplateVO extends OnlineAgreementTemplateEntity {
    private static final long serialVersionUID = 1L;
}
