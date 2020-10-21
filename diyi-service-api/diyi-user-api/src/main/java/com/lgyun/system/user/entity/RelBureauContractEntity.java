package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 相关局合作协议表 Entity
 *
 * @author liangfeihu
 * @since 2020-10-20 18:47:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_rel_bureau_contract")
public class RelBureauContractEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 相关局编号
     */
    private Long relBureauId;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 合同说明
     */
    private String contractDesc;

    /**
     * 合同名称
     */
    private String contractUrl;

}
