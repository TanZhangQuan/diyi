package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.SignState;
import com.lgyun.common.enumeration.VerifyStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2020/8/17.
 * @time 9:22.
 */
@Data
@ApiModel(value = "MakerWorksheetVO对象", description = "MakerWorksheetVO对象")
public class MakerWorksheetVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客id
     */
    private Long id;

    /**
     * 创客名字
     */
    private String name;

    /**
     * 实名认证
     */
    private VerifyStatus bankCardVerifyStatus;


    /**
     * 合同
     */
    private SignState empowerSignState;

    /**
     * 协议
     */
    private SignState joinSignState;

    /**
     * 协议认证状态
     */
    private SignState signState;
}
