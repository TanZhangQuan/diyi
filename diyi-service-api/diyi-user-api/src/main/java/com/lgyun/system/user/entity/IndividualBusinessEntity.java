package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Data
@NoArgsConstructor
@TableName("diyi_individual_business")
public class IndividualBusinessEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 个体工商户信息ID
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long individualBusinessId;

    /**
     * 创客ID
     */
    private Long makerId;

    /**
     * 个体户名称
     */
    private String ibname;

    /**
     * 统一社会信用代码
     */
    private String ibtaxNo;

    /**
     * 营业执照的注册日期
     */
    private Date buildDatetie;

    /**
     * 经营场所
     */
    private String businessAddress;

    /**
     * 网络经营场所
     */
    private String netBusinessAddress;

    /**
     * 营业执照图片地址
     */
    private String businessLicenceUrl;

    /**
     * 个体工商户状态：注册中，税务登记中，运营中，已注销
     */
    private Ibstate ibstate;

    /**
     * 提交日期
     */
    private Date submitDateTime;

    /**
     * 税务登记日期
     */
    private Date taxRegisterDateTime;

    /**
     * 注销日期
     */
    private Date logoutDateTime;

}
