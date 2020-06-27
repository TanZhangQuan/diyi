package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
 * @since 2020-06-26 17:21:05
 */
@Data
@NoArgsConstructor
@TableName("diyi_deliver_material")
public class DeliverMaterialEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创客交付材料信息信息ID
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deliverMaterialId;

    /**
     * 确认函ID
     */
    private Long paysheetId;

    /**
     * 交付材料标题
     */
    private String materialTitle;

    /**
     * 交付材料说明
     */
    private String materialMemo;

    /**
     * 材料URL
     */
    private String materialUrl;

    /**
     * 第三方URL
     */
    private String thirdMaterialUrl;

    /**
     * 上传日期
     */
    private Date uploadDateTime;

    /**
     * 上传人员
     */
    private String uploadPerson;

}
