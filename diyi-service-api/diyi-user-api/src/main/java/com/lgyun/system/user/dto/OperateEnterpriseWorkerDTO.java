package com.lgyun.system.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * OperateEnterpriseWorkerDTO
 *
 * @author tzq
 * @since 2020/8/24 11:40
 */
@Data
@NoArgsConstructor
public class OperateEnterpriseWorkerDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Integer ACCOUNT_DEL = 1;
    public static final Integer ACCOUNT_STOP = 2;

    /**
     * 账号Id
     */
    private Long accountId;

    /**
     * 商户ID
     */
    private Long enterpriseId;

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
