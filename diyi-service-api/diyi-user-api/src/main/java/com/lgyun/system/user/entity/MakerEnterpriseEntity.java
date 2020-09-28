package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.EnterpriseMakerRelType;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity
 *
 * @author tzq
 * @since 2020-06-26 17:21:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("diyi_maker_enterprise")
public class MakerEnterpriseEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    private Long enterpriseId;

    /**
     * 创客ID
     */
    private Long makerId;

    /**
     * 外包岗位ID
     */
    private Long positionId;

    /**
     * 创客商户关系
     */
    private RelationshipType relationshipType;

    /**
     * 关联类型：创客主动关联，企业主动关联，平台关联
     */
    private EnterpriseMakerRelType relType;

    /**
     * 关系备注
     */
    private String relMemo;

    /**
     * 合作状态：合作中，停止合作；首次关联时默认为合作中
     */
    private CooperateStatus cooperateStatus;

    /**
     * 创客第一次合作
     */
    private Boolean firstCooperation;

    /**
     * 合作开始日期
     */
    private Date cooperationStartTime;

    /**
     * 合作终止日期
     */
    private Date cooperationEndTime;

}
