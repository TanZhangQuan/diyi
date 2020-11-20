package com.lgyun.system.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 创客关联商户数和收入情况VO
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
public class MakerEnterpriseNumIncomeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 关联商户数
     */
    private String enterpriseNum;

    /**
     * 近30天收入
     */
    private BigDecimal monthIncome;

    /**
     * 近365天收入
     */
    private BigDecimal yearIncome;

    /**
     * 总收入
     */
    private BigDecimal allIncome;

}
