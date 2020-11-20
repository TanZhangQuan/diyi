package com.lgyun.common.api;

import java.io.Serializable;

/**
 * 业务代码接口
 *
 * @author tzq
 * @since 2020/6/5 18:41
 */
public interface IResultCode extends Serializable {

    /**
     * 消息
     *
     * @return String
     */
    String getMessage();

    /**
     * 状态码
     *
     * @return int
     */
    int getCode();

}
