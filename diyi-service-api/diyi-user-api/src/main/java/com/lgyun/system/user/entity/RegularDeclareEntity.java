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
 * 申报表 Entity
 *
 * @author liangfeihu
 * @since 2020-09-22 15:46:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_regular_declare")
public class RegularDeclareEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
        @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
        private Long id;

    /**
     * 服务商ID
     */
        private Long serviceProviderId;

    /**
     * 申报类别
     */
        private String subjectType;

    /**
     * 申报主体类别
     */
        private String subjectSubjectType;

    /**
     * 申报主体id
     */
        private Long declareSubjectId;

    /**
     * 申报主题
     */
        private String declareTheme;

    /**
     * 年度
     */
        private String declareYear;

    /**
     * 季度
     */
        private String declareQuarter;

    /**
     * 月度
     */
        private String declareMonthly;

    /**
     * 申报结果
     */
        private String declareResult;

    /**
     * 申报相关政府机关名称
     */
        private String governmentOfficeName;
    }
