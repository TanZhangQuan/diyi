package com.lgyun.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单数据传输对象实体类
 *
 * @author tzq
 */
@Data
public class MenuDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String alias;
	private String path;
}
