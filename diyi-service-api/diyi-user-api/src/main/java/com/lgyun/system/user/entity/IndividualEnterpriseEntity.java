package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@Data
@NoArgsConstructor
@TableName("diyi_individual_enterprise")
public class IndividualEnterpriseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 个独ID
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long individualEnterpriseId;

    /**
     * 创客ID
     */
    private Long makerId;

    /**
     * 个独类别：小规模纳税人个独，一般纳税人个独
     */
    private String bizType;

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
    private Date buildDateTime;

    /**
     * 园区
     */
    private String bizPark;

    /**
     * 所在省市县
     */
    private String provinceandCounty;

    /**
     * 注册资金
     */
    private BigDecimal registeredMoney;

    /**
     * 经营场所
     */
    private String businessAddress;

    /**
     * 主要行业
     */
    private String mainIndustry;

    /**
     * 经营范围
     */
    private String bizScope;

    /**
     * 注册时候选名称
     */
    private String candidatedNames;

    /**
     * 网络经营场所
     */
    private String netBusinessAddress;

    /**
     * 营业执照正本
     */
    private String businessLicenceUrl;

    /**
     * 营业执照副本
     */
    private String businessLicenceCopyUrl;

    /**
     * 个体户状态：注册中，税务登记中，运营中，已注销
     */
    private String ibstate;

    /**
     * 提交日期
     */
    private Date submitDateTime;

    /**
     * 注册日期
     */
    private Date registeredDate;

    /**
     * 税务登记日期
     */
    private Date taxRegisterDateTime;

    /**
     * 注销日期
     */
    private Date logoutDateTime;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机号
     */
    private String contactPhone;

    /**
     * 服务费率
     */
    private BigDecimal serviceRat;

    /**
     * 投资人手持身份证正面
     */
    private String investorHandIdcardPic;

    /**
     * 投资人手持身份证反面
     */
    private String investorHandIdcardPicBack;

    /**
     * 手持承诺书照片
     */
    private String investorHandCommitment;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态[1:正常]
     */
    private Integer status;

    /**
     * 状态[0:未删除,1:删除]
     */
    private Integer isDeleted;

}
