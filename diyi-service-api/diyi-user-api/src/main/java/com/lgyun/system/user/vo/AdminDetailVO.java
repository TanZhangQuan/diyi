package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.PositionName;
import lombok.Data;

import java.io.Serializable;

/**
 * 平台端---首页管理---管理员详情VO
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class AdminDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 姓名
     */
    private String name;

    /**
     * 岗位性质
     */
    private PositionName positionName;

}
