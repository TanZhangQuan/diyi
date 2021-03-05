package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.ServiceProviderMakerRelType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2021/2/19.
 * @time 17:16.
 */
@Data
public class ServiceProviderMakerVO implements Serializable {
    /**
     * id
     */
    private Long serviceProviderMakerId;
    /**
     * 服务商id
     */
    private Long serviceProviderId;
    /**
     * 创客id
     */
    private Long makerId;
    /**
     * 服务商名字
     */
    private String serviceProviderName;
    /**
     * 创客名字
     */
    private String makerName;

    /**
     * 关联类型：总包+分包支付关联；众包代开票关联
     */
    private ServiceProviderMakerRelType relType;
}
