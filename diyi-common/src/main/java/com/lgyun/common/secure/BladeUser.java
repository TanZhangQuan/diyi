package com.lgyun.common.secure;

import com.lgyun.common.enumeration.UserType;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体
 *
 * @author Chill
 */
@Data
public class BladeUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户类型
     */
    private UserType userType;

    /**
     * 用户ID
     */
    private Long userId;

}
