package com.lgyun.system.user.vo.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 平台端---商户管理---商户员工vo
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class QueryEnterpriseWorkerEnterpriseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //商户员工编号
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseWorkerId;

    //姓名
    private String workerName;

    //手机号码
    private String phoneNumber;

}
