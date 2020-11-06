package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.AccountState;
import lombok.Data;

import java.util.List;

@Data
public class AdminVO{
    /**
     * 姓名
     */
    private String name;

    /**
     * 岗位性质
     */
    private String positionName;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户名
     */
    private String userName;


    /**
     * 管理员账户状态
     */
    private String adminState;


    /**
     * 上级主管
     */
    private Long upLevelId;

}
