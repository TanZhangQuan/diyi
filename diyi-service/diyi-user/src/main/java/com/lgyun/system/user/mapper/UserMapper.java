package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.entity.User;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author tzq
 * @since 2020/6/6 22:10
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询角色别名
     *
     * @param ids
     * @return
     */
    List<String> getRoleAlias(String[] ids);

}
