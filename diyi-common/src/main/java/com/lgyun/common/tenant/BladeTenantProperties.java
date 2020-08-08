package com.lgyun.common.tenant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 多租户配置
 *
 * @author Chill
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "diyi.tenant")
public class BladeTenantProperties {

	/**
	 * 多租户字段名称
	 */
	private String column = "tenant_id";

	/**
	 * 多租户数据表
	 */
	private List<String> tables = new ArrayList<>();

	/**
	 * 多租户系统数据表
	 */
	private List<String> bladeTables = Arrays.asList("sys_notice", "sys_log_api", "sys_log_error", "sys_log_usual");

}
