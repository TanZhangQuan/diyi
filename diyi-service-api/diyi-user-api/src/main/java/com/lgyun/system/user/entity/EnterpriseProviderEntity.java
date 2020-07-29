package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *  Entity
 *
 * @author jun
 * @since 2020-07-28 14:53:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_enterprise_provider")
public class EnterpriseProviderEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
        @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
        private Long id;

    /**
     * 企业ID
     */
        private Long enterpriseId;

    /**
     * 服务商id
     */
        private Long serviceProviderId;

    /**
     * 分配日期
     */
        private Date matchDate;

    /**
     * 分配人员
     */
        private String matchPerson;

    /**
     * 分配说明
     */
        private String matchDesc;

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
