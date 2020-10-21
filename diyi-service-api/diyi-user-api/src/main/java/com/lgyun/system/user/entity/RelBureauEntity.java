package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.BureauType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 税务局管理表 Entity
 *
 * @author liangfeihu
 * @since 2020-10-20 18:47:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_rel_bureau")
public class RelBureauEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 0，税务局；1，市场监督管理局；2，产业园区；3，支付机构
     */
    private BureauType bureauType;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户名
     */
    private String relBuserUame;

    /**
     * 密码
     */
    private String relBpwd;

    /**
     * 税务局名称
     */
    private String relBureauName;

    /**
     * 地址
     */
    private String relBureauAddress;

    /**
     * 网址
     */
    private String relBureauWebsite;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系人职位
     */
    private String contactPosition;

    /**
     * 联系电话
     */
    private String telPhoneNo;

    /**
     * 联系手机
     */
    private String mobileNo;

    /**
     * 联系微信
     */
    private String wechatNo;

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
