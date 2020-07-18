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
 * Entity
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
@Data
@NoArgsConstructor
@TableName("diyi_deliver_material")
public class DeliverMaterialEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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
