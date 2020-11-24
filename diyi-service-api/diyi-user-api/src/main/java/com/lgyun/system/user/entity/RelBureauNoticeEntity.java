package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.NoticeState;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 相关局通知管理表 Entity
 *
 * @author tzq
 * @since 2020-11-24 17:03:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_rel_bureau_notice")
public class RelBureauNoticeEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 相关局编号
     */
    private Long relBureauId;

    /**
     * 通知标题
     */
    private String noticeTitle;

    /**
     * 通知摘要
     */
    private String noticeDesc;

    /**
     * 通知文件
     */
    private String noticeUrl;

    /**
     * 发布日期时间
     */
    private Date publishDatetime;

    /**
     * 通知状态:1，编辑中；2，已发布；3，已阅读；4，已作废
     */
    private NoticeState noticeState = NoticeState.EDITING;

    /**
     * 作废日期时间
     */
    private Date cancelDatetime;

    /**
     * 发布联系人
     */
    private String contactPerson;

    /**
     * 联系人手机
     */
    private String contactPhone;

    /**
     * 联系人微信
     */
    private String contactWechat;

}
