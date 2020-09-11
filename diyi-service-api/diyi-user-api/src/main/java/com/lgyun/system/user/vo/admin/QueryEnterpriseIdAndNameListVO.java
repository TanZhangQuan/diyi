package com.lgyun.system.user.vo.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 平台端---自然人创客管理---创建创客选择的商户vo
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class QueryEnterpriseIdAndNameListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //商户编号
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    //商户名称
    private String enterpriseName;

}
