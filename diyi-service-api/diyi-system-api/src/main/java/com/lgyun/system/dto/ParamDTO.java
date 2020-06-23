package com.lgyun.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lgyun.system.entity.Param;

/**
 * 数据传输对象实体类
 *
 * @author liangfeihu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ParamDTO extends Param {
    private static final long serialVersionUID = 1L;

}
