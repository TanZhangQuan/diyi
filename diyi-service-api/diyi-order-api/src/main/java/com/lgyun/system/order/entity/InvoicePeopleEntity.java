package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 *  Entity
 *
 * @author jun
 * @since 2020-07-02 16:21:19
 */
@Data
@NoArgsConstructor
@TableName("diyi_invoice_people")
public class InvoicePeopleEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 开票人
     */
        @ApiModelProperty(value = "主键")
        @TableId(type = IdType.ASSIGN_ID)
        @JsonSerialize(using = ToStringSerializer.class)
        private Long invoicePeopleId;

    @ApiModelProperty(value = "创客id")
    @JsonSerialize(using = ToStringSerializer.class)
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
    }
