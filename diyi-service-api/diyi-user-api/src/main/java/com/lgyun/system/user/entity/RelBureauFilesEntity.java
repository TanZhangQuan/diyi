package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 相关局监管文件：相关局监管文件管理表 Entity
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_rel_bureau_files")
public class RelBureauFilesEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 相关局编号
     */
    private Long relBureauId;

    /**
     * 文件标题
     */
    private String filesTitle;

    /**
     * 通知文件
     */
    private String filesDesc;

    /**
     * 监管文件
     */
    private String filesUrl;

    /**
     * 发布日期时间
     */
    private Date publishDatetime;

    /**
     * 监管文件状态:0，编辑中；1，已发布；2，已阅读；3，已下架；4，已作废
     */
    private String filesState;

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
    private String mobileNo;

    /**
     * 联系微信
     */
    private String wechatNo;

    /**
     * 联系电话
     */
    private String directorPhone;

}
