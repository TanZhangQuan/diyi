package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.AuditState;
import com.lgyun.common.enumeration.AuthorizationAudit;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author .
 * @date 2021/3/22.
 * @time 14:01.
 */
@Data
@ApiModel(description = "创客待审核列表Vo")
public class MakerToExamineListVO implements Serializable {

    /**
     * 创客id
     */
    private Long makerId;
    /**
     * 签名id
     */
    private Long signPicId;
    /**
     * 创客名称
     */
    private String makerName;
    /**
     * 审核状态
     */
    private AuditState auditState;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 签名图片
     */
    private String signPic;


}
