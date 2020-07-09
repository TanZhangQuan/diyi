package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 *  Entity
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Data
@NoArgsConstructor
@TableName("diyi_self_help_invoice_person")
public class SelfHelpInvoicePersonEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 开票人
     */
        @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
        private Long invoicePeopleId;

    /**
     * 创客id
     */
        private Long makerId;

    /**
     * 身份证号码
     */
        private String idCardNo;

    /**
     * 身份证姓名
     */
        private String idCardName;

    /**
     * 身份证正面图
     */
        private String idCardPic;

    /**
     * 身份证反面图
     */
        private String idCardPicBack;

    /**
     * 手机号码
     */
        private String phoneNumber;

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
