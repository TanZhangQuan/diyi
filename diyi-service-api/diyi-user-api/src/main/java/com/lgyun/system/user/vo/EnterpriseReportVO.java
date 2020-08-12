package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.EnterpriseReportEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图实体类
 *
 * @author jun
 * @date 2020/6/29.
 * @time 14:49.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "EnterpriseReportVO对象", description = "EnterpriseReportVO对象")
public class EnterpriseReportVO extends EnterpriseReportEntity {
    private static final long serialVersionUID = 1L;

}
