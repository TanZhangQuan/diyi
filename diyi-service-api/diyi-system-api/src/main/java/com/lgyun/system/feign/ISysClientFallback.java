package com.lgyun.system.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.entity.Dept;
import com.lgyun.system.entity.Role;
import com.lgyun.system.vo.GrantRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统服务模块 Feign失败配置
 *
 * @author liangfeihu
 */
@Component
public class ISysClientFallback implements ISysClient {

    @Override
    public Dept getDept(Long id) {
        return null;
    }

    @Override
    public String getDeptName(Long id) {
        return null;
    }

    @Override
    public String getDeptIds(String tenantId, String deptNames) {
        return null;
    }

    @Override
    public List<String> getDeptNames(String deptIds) {
        return null;
    }

    @Override
    public String getPostIds(String tenantId, String postNames) {
        return null;
    }

    @Override
    public List<String> getPostNames(String postIds) {
        return null;
    }

    @Override
    public Role getRole(Long id) {
        return null;
    }

    @Override
    public String getRoleIds(String tenantId, String roleNames) {
        return null;
    }

    @Override
    public String getRoleName(Long id) {
        return null;
    }

    @Override
    public List<String> getRoleNames(String roleIds) {
        return null;
    }

    @Override
    public String getRoleAlias(Long id) {
        return null;
    }

    /**
     * 授权接口
     *
     * @param request
     * @return
     */
    @Override
    public R grantFeign(GrantRequest request) {
        return null;
    }

    /**
     * 获取权限
     *
     * @param roleId 主键
     * @return
     */
    @Override
    public List<String> getMenuIds(Long roleId) {
        return null;
    }
}
