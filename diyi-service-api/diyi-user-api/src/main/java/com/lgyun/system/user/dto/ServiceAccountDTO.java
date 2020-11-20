package com.lgyun.system.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ServiceAccountDTO
 *
 * @author tzq
 * @since 2020/9/19 16:40
 */
@Data
public class ServiceAccountDTO implements Serializable {
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
