package com.lgyun.system.user.vo.service;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ServiceAccountRequest
 *
 * @author liangfeihu
 * @since 2020/9/19 16:40
 */
@Data
@NoArgsConstructor
public class ServiceAccountRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Integer ACCOUNT_DEL = 1;
    public static final Integer ACCOUNT_STOP = 2;

    /**
     * 账号Id
     */
    private Long accountId;

    /**
     * 服务商ID
     */
    private Long serviceProviderId;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 1 删除账号
     * 2 停用账号
     */
    private Integer operationCode;

}
