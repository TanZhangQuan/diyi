/**
 *
 */
package com.lgyun.common.dto;

import lombok.Data;

/**
 * 业务方交互上下文信息
 * @author tzq
 *
 */
@Data
public class ContextInfo {

    //发起方业务上下文标识:在异步通知时发送回发起方
    private String contextId;

    //发起方接收实名认证状态变更通知的地址
    private String notifyUrl;

}
