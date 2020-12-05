package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.common.enumeration.RelBureauType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 相关局管理表 Entity
 *
 * @author tzq
 * @since 2020-11-20 19:42:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_rel_bureau")
public class RelBureauEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 相关局账户状态
     */
    private AccountState relBureauState = AccountState.NORMAL;

    /**
     * 相关局类型
     */
    private RelBureauType relBureauType;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户名
     */
    private String relBureauUserName;

    /**
     * 密码
     */
    private String relBureauPwd;

    /**
     * 相关局名称
     */
    private String relBureauName;

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
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 网址
     */
    private String relBureauWebsite;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人职位
     */
    private PositionName contactPosition;

    /**
     * 联系人电话手机（必填）
     */
    private String contactPhone;

    /**
     * 联系人邮箱
     */
    private String contactMail;

    /**
     * 联系人微信
     */
    private String contactWechat;

    /**
     * 局长姓名
     */
    private String directorName;

    /**
     * 局长联系电话
     */
    private String directorPhone;

    /**
     * 副局长姓名
     */
    private String viceDirectorName;

    /**
     * 副局长联系电话
     */
    private String viceDirectorPhone;

}
