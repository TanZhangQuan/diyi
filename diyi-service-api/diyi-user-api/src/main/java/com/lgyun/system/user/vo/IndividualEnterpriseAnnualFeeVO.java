package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.IndividualEnterpriseAnnualFeeEntity;
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
@ApiModel(value = "IndividualEnterpriseAnnualFeeVO对象", description = "IndividualEnterpriseAnnualFeeVO对象")
public class IndividualEnterpriseAnnualFeeVO extends IndividualEnterpriseAnnualFeeEntity {
    private static final long serialVersionUID = 1L;

}
