package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.BureauServiceProviderStatus;
import com.lgyun.common.enumeration.BureauType;
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
     * 相关局的类型
     */
    private BureauType bureauType;

    /**
     * 服务商编号，一个服务商只能属于一个税局监管
     */
    private Long serviceProviderId;

    /**
     * 相关局匹配的服务商状态
     */
    BureauServiceProviderStatus bureauServiceProviderStatus;

}
