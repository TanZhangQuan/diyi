package com.lgyun.common.client;

import lombok.AllArgsConstructor;
import com.lgyun.common.constant.SecureConstant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 获取客户端详情
 *
 * @author liangfeihu
 * @since 2020/6/5 19:07
 */
@AllArgsConstructor
public class ClientDetailsServiceImpl implements IClientDetailsService {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public IClientDetails loadClientByClientId(String clientId) {
		try {
			return jdbcTemplate.queryForObject(SecureConstant.DEFAULT_SELECT_STATEMENT, new String[]{clientId}, new BeanPropertyRowMapper<>(ClientDetails.class));
		} catch (Exception ex) {
			return null;
		}
	}

}
