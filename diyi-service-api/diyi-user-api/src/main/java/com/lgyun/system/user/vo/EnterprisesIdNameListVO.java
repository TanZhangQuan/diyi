package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tzq.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
@ApiModel(value = "EnterprisesByWorksheetListVO对象", description = "EnterprisesByWorksheetListVO对象")
public class EnterprisesIdNameListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //商户ID
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    //商户名称
    private String enterpriseName;

}
