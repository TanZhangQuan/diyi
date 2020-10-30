package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台端---商户管理---商户列表vo
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class EnterpriseListEnterpriseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户编号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商户名称
     */
    private String enterpriseName;

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
     * 商户承诺函(可能多张)
     */
    private String commitmentLetters;

    /**
     * 状态
     */
    private AccountState enterpriseState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
