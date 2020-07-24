package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.excel.UserExcel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author liangfeihu
 * @since 2020/6/6 22:10
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取角色名
     *
     * @param ids
     * @return
     */
    List<String> getRoleName(String[] ids);

    /**
     * 获取角色别名
     *
     * @param ids
     * @return
     */
    List<String> getRoleAlias(String[] ids);

    /**
     * 获取部门名
     *
     * @param ids
     * @return
     */
    List<String> getDeptName(String[] ids);

    /**
     * 获取导出用户数据
     *
     * @param queryWrapper
     * @return
     */
    List<UserExcel> exportUser(@Param("ew") Wrapper<User> queryWrapper);

}
