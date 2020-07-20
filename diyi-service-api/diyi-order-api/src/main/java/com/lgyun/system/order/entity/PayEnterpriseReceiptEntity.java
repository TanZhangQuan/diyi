package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *  Entity
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
@Data
@NoArgsConstructor
@TableName("diyi_pay_enterprise_receipt")
public class PayEnterpriseReceiptEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    
    /**
     * 唯一性控制
     */
        @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
        private Long id;
    
    /**
     * 支付ID
     */
        private Long enPayId;
    
    /**
     * 支付回单
     */
        private String enterprisePayReceiptUrl;
    
    /**
     * 上传日期时间
     */
        private Date uploadDatetime;
    }
