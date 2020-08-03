package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_deliver_material")
public class DeliverMaterialEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 确认函ID
     */
    private Long paysheetId;

    /**
     * 交付材料标题
     */
    private String materialTitle;

    /**
     * 交付材料说明
     */
    private String materialMemo;

    /**
     * 材料URL
     */
    private String materialUrl;

    /**
     * 第三方URL
     */
    private String thirdMaterialUrl;

    /**
     * 上传日期
     */
    private Date uploadDateTime;

    /**
     * 上传人员
     */
    private String uploadPerson;

}
