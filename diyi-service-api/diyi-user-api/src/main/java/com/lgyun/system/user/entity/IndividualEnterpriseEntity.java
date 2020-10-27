package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.BizType;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity
 *
 * @author tzq
 * @since 2020-07-02 17:44:02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_individual_enterprise")
public class IndividualEnterpriseEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创客ID
     */
    private Long makerId;

    /**
     * 服务商ID
     */
    private Long serviceProviderId;

    /**
     * 税种：小规模，一般纳税人
     */
    private BizType bizType;

    /**
     * 个独名称
     */
    private String ibname;

    /**
     * 统一社会信用代码
     */
    private String ibtaxNo;

    /**
     * 营业执照的注册日期
     */
    private Date buildDateTime;

    /**
     * 园区
     */
    private String bizPark;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 注册资金
     */
    private BigDecimal registeredMoney;

    /**
     * 经营场所
     */
    private String businessAddress;

    /**
     * 主要行业
     */
    private String mainIndustry;

    /**
     * 经营范围
     */
    private String bizScope;

    /**
     * 注册时候选名称
     */
    private String candidatedNames;

    /**
     * 网络经营场所
     */
    private String netBusinessAddress;

    /**
     * 营业执照正本
     */
    private String businessLicenceUrl;

    /**
     * 营业执照副本
     */
    private String businessLicenceCopyUrl;

    /**
     * 个独状态
     */
    private Ibstate ibstate = Ibstate.EDITING;

    /**
     * 提交日期
     */
    private Date submitDateTime;

    /**
     * 注册日期
     */
    private Date registeredDate;

    /**
     * 税务登记日期
     */
    private Date taxRegisterDateTime;

    /**
     * 注销日期
     */
    private Date logoutDateTime;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机号
     */
    private String contactPhone;

    /**
     * 服务费率
     */
    private BigDecimal serviceRate;

    /**
     * 手持承诺书照片
     */
    private String investorHandCommitment;

}
