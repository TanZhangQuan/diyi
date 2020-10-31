package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台端---服务商管理---服务商列表vo
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class ServiceProviderListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商编号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

    /**
     * 联系人
     */
    private String contact1Name;

    /**
     * 联系人电话
     */
    private String contact1Phone;

    /**
     * 加盟合同
     */
    private String joinContract;

    /**
     * 状态
     */
    private AccountState serviceProviderState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
