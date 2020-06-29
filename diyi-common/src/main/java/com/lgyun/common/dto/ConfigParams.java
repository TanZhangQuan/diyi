/**
 *
 */
package com.lgyun.common.dto;

import lombok.Data;

/**
 * 认证配置信息
 * @author tzq
 *
 */
@Data
public class ConfigParams {

    //发起方业务上下文标识:在异步通知时发送回发起方
    private String[] indivUneditableInfo;

}
