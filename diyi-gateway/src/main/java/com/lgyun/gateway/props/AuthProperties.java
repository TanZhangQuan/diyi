package com.lgyun.gateway.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限过滤, 可根据配置中心动态配置
 *
 * @author tzq
 * @since 2020/6/2 23:30
 */
@Data
@RefreshScope
@ConfigurationProperties("blade.secure")
public class AuthProperties {

	/**
	 * 放行API集合
	 */
	private final List<String> skipUrl = new ArrayList<>();

}
