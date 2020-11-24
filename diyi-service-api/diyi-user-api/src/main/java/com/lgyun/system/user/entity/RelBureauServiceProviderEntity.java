package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.RelBureauType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 相关局与服务商关联表 Entity
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_rel_bureau_service_provider")
public class RelBureauServiceProviderEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 相关局编号
     */
    private Long relBureauId;

    /**
     * 服务商编号，一个服务商只能属于一个税务局监管
     */
    private Long serviceProviderId;

    /**
     * 相关局的类型
     */
    private RelBureauType relBureauType;

    /**
     * 关联状态
     */
    private CooperateStatus cooperateStatus = CooperateStatus.COOPERATING;

    /**
     * 分配人员
     */
    private String matchPerson;

    /**
     * 分配说明
     */
    private String matchDesc;

}
