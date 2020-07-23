package com.lgyun.system.order.vo;

import com.lgyun.system.order.entity.MakerTaxRecordEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jun.
 * @date 2020/7/18.
 * @time 20:53.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "MakerTaxRecordVO对象", description = "MakerTaxRecordVO对象")
public class MakerTaxRecordVO extends MakerTaxRecordEntity {
}
