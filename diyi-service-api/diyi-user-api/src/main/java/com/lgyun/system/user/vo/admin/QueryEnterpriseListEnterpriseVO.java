package com.lgyun.system.user.vo.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 平台端---商户管理vo
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class QueryEnterpriseListEnterpriseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //商户编号
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    //商户名称
    private String enterpriseName;

    //联系人
    private String contact1Name;

    //联系人电话
    private String contact1Phone;

}
