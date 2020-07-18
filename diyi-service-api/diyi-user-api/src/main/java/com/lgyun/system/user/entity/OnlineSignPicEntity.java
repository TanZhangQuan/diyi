package com.lgyun.system.user.entity;

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
 * @author jun
 * @since 2020-07-18 15:59:14
 */
@Data
@NoArgsConstructor
@TableName("diyi_online_sign_pic")
public class OnlineSignPicEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一性控制
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 对象身份1、创客本人2、商户人员3、服务商人员4、相关局人员5、渠道商人员6、合伙人本人
     */
    private Integer objectType;

    /**
     * 对象身份ID
     */
    private Long objectId;

    /**
     * 相关人员ID 创客/合伙人，就取创客/合伙人ID，其余取相关工作人员ID
     */
    private Long workerSex;

    /**
     * 签字笔迹URL
     */
    private String signPic;

    /**
     * 签署日期
     */
    private Date signDatetime;
}
