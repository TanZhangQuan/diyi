package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.AgreementEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图实体类
 *
 * @author liangfeihu
 * @since 2020/6/6 00:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AgreementVO对象", description = "AgreementVO对象")
public class AgreementVO extends AgreementEntity {
    private static final long serialVersionUID = 1L;


}
