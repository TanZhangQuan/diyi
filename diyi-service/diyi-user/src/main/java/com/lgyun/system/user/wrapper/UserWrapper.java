package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.SpringUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.feign.IDictClient;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.service.IUserService;
import com.lgyun.system.user.vo.UserVO;

import java.util.List;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author tzq
 * @since 2020/6/6 22:07
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

	private static IUserService userService;
	private static IDictClient dictClient;

	static {
		userService = SpringUtil.getBean(IUserService.class);
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	public static UserWrapper build() {
		return new UserWrapper();
	}

	@Override
	public UserVO entityVO(User user) {

		if (user == null){
			return null;
		}

		UserVO userVO = BeanUtil.copy(user, UserVO.class);
		List<String> roleName = userService.getRoleName(user.getRoleId());
		List<String> deptName = userService.getDeptName(user.getDeptId());
		userVO.setRoleName(Func.join(roleName));
		userVO.setDeptName(Func.join(deptName));
		return userVO;
	}

}
