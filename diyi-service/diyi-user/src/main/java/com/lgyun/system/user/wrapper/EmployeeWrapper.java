package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.EmployeeEntity;
import com.lgyun.system.user.vo.EmployeeVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
public class EmployeeWrapper extends BaseEntityWrapper<EmployeeEntity, EmployeeVO> {

    public static EmployeeWrapper build() {
        return new EmployeeWrapper();
    }

	@Override
	public EmployeeVO entityVO(EmployeeEntity employee) {
			EmployeeVO employeeVO = BeanUtil.copy(employee, EmployeeVO.class);

		return employeeVO;
	}

}
