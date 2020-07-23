package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.tool.DateUtil;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("diyi_run_company")
public class RunCompanyEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创客Id
     */
    private Long makerId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司地址
     */
    private String employeeName;

    /**
     * 纳税人识别号
     */
    private String taxNo;

    /**
     * 银行账户名
     */
    private String bankName;

    /**
     * 银行账户
     */
    private String bankAccount;

    /**
     * 联系人
     */
    private String contacterName;

    /**
     * 联系电话
     */
    private String phoneNo;

    /**
     * 联系邮箱
     */
    private String emailAddress;

    /**
     * 配置日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date setDate;

    /**
     * 备注
     */
    private String memoInfo;

}
