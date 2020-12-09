package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.RelBureauNoticeFileState;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 相关局监管文件管理表 Entity
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_rel_bureau_file")
public class RelBureauFileEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 相关局ID
     */
    private Long relBureauId;

    /**
     * 文件标题
     */
    private String fileTitle;

    /**
     * 文件摘要
     */
    private String fileDesc;

    /**
     * 监管文件
     */
    private String fileUrl;

    /**
     * 发布日期时间
     */
    private Date publishDatetime;

    /**
     * 监管文件状态
     */
    private RelBureauNoticeFileState fileState;

    /**
     * 作废日期时间
     */
    private Date cancelDatetime;

    /**
     * 发布联系人
     */
    private String contactPerson;

    /**
     * 联系手机
     */
    private String contactPhone;

    /**
     * 联系微信
     */
    private String contactWechat;

}
