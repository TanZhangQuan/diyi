package com.lgyun.common.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.SecureUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Blade控制器封装类
 *
 * @author tzq
 * @since 2020/6/6 23:28
 */
public class BladeController {

	/**
	 * ============================     REQUEST    =================================================
	 */

	@Autowired
	private HttpServletRequest request;

	/**
	 * 查询request
	 *
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest() {
		return this.request;
	}

	/**
	 * 查询当前用户
	 *
	 * @return BladeUser
	 */
	public BladeUser getUser() {
		return SecureUtil.getUser();
	}

	/** ============================     API_RESULT    =================================================  */

	/**
	 * 返回ApiResult
	 *
	 * @param data 数据
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public <T> R<T> data(T data) {
		return R.data(data);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param data 数据
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public <T> R<T> data(T data, String msg) {
		return R.data(data, msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param data 数据
	 * @param msg  消息
	 * @param code 状态码
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public <T> R<T> data(T data, String msg, int code) {
		return R.data(code, data, msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param msg 消息
	 * @return R
	 */
	public R success(String msg) {
		return R.success(msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param msg 消息
	 * @return R
	 */
	public R fail(String msg) {
		return R.fail(msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param flag 是否成功
	 * @return R
	 */
	public R status(boolean flag) {
		return R.status(flag);
	}

}
