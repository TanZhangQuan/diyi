package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *  Entity
 *
 * @author liangfeihu
 * @since 2020-07-06 14:14:06
 */
@Data
@NoArgsConstructor
@TableName("diyi_paysheet")
public class PaysheetEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    
    /**
     * 确认函ID
     */
        @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
        private Long paysheetId;
    
    /**
     * 支付ID
     */
        private Long payId;
    
    /**
     * 订单ID
     */
        private Long orderId;
    
    /**
     * 创客ID
     */
        private Long makerId;
    
    /**
     * 上传日期
     */
        private Date uploadDateTime;
    
    /**
     * 上传来源：平台，外包企业
     */
        private String paysheetUploadSource;
    
    /**
     * 上传人员
     */
        private String uploadDatePerson;
    
    /**
     * 确认函URL
     */
        private String paysheetUrl;
    
    /**
     * 确认函确认日期
     */
        private Date paysheetConfirmDateTime;
    
    /**
     * 确认函确认说明
     */
        private String paysheetConfirmMemo;
    
    /**
     * 收款确认日期
     */
        private Date incomeConfirmDate;
    
    /**
     * 收款确认说明
     */
        private String incomeConfirmMemo;
    
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
