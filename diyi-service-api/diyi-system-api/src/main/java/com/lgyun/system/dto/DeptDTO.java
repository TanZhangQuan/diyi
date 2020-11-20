package com.lgyun.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lgyun.system.entity.Dept;

/**
 * 部门数据传输对象实体类
 *
 * @author tzq
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptDTO extends Dept {
	private static final long serialVersionUID = 1L;

}
