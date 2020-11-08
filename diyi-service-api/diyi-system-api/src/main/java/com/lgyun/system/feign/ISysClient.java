package com.lgyun.system.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.AppConstant;
import com.lgyun.common.enumeration.MenuType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.system.dto.GrantDTO;
import com.lgyun.system.dto.RoleMenusDTO;
import com.lgyun.system.entity.Dept;
import com.lgyun.system.entity.Role;
import com.lgyun.system.vo.RoleMenusVO;
import com.lgyun.system.vo.RolesVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 系统服务模块 Feign接口类
 *
 * @author liangfeihu
 */
@FeignClient(
        value = AppConstant.APPLICATION_SYSTEM_NAME,
        fallback = ISysClientFallback.class
)
public interface ISysClient {

    String API_PREFIX = "/sys";

    /**
     * 查询部门
     *
     * @param id 主键
     * @return Dept
     */
    @GetMapping(API_PREFIX + "/getDept")
    Dept getDept(@RequestParam("id") Long id);

    /**
     * 查询部门名
     *
     * @param id 主键
     * @return 部门名
     */
    @GetMapping(API_PREFIX + "/getDeptName")
    String getDeptName(@RequestParam("id") Long id);

    /**
     * 查询部门id
     *
     * @param tenantId  租户id
     * @param deptNames 部门名
     * @return 部门id
     */
    @GetMapping(API_PREFIX + "/getDeptIds")
    String getDeptIds(@RequestParam("tenantId") String tenantId, @RequestParam("deptNames") String deptNames);

    /**
     * 查询部门名
     *
     * @param deptIds 主键
     * @return
     */
    @GetMapping(API_PREFIX + "/getDeptNames")
    List<String> getDeptNames(@RequestParam("deptIds") String deptIds);

    /**
     * 查询岗位id
     *
     * @param tenantId  租户id
     * @param postNames 岗位名
     * @return 岗位id
     */
    @GetMapping(API_PREFIX + "/getPostIds")
    String getPostIds(@RequestParam("tenantId") String tenantId, @RequestParam("postNames") String postNames);

    /**
     * 查询岗位名
     *
     * @param postIds 主键
     * @return
     */
    @GetMapping(API_PREFIX + "/getPostNames")
    List<String> getPostNames(@RequestParam("postIds") String postIds);


    /**
     * 查询角色
     *
     * @param id 主键
     * @return Role
     */
    @GetMapping(API_PREFIX + "/getRole")
    Role getRole(@RequestParam("id") Long id);

    /**
     * 查询角色id
     *
     * @param tenantId  租户id
     * @param roleNames 角色名
     * @return 角色id
     */
    @GetMapping(API_PREFIX + "/getRoleIds")
    String getRoleIds(@RequestParam("tenantId") String tenantId, @RequestParam("roleNames") String roleNames);

    /**
     * 查询角色名
     *
     * @param id 主键
     * @return 角色名
     */
    @GetMapping(API_PREFIX + "/getRoleName")
    String getRoleName(@RequestParam("id") Long id);

    /**
     * 查询角色名
     *
     * @param roleIds 主键
     * @return
     */
    @GetMapping(API_PREFIX + "/getRoleNames")
    List<String> getRoleNames(@RequestParam("roleIds") String roleIds);

    /**
     * 查询角色别名
     *
     * @param id 主键
     * @return 角色别名
     */
    @GetMapping(API_PREFIX + "/getRoleAlias")
    String getRoleAlias(@RequestParam("id") Long id);

    /**
     * 授权接口
     *
     * @param request
     * @return
     */
    @PostMapping(API_PREFIX + "/grant")
    R grantFeign(@RequestBody GrantDTO request);


    /**
     * 创建或修改角色及角色拥有的菜单
     *
     * @param roleMenusDTO
     * @param account
     * @return
     */
    @PostMapping(API_PREFIX + "/create-or-update-role-menus")
    R createOrUpdateRoleMenus(@RequestBody RoleMenusDTO roleMenusDTO, @RequestParam("account") Long account);

    /**
     * 查询权限
     *
     * @param roleId 主键
     * @return
     */
    @GetMapping(API_PREFIX + "/menus")
    List<String> getMenuIds(@RequestParam("roleId") Long roleId);

    /**
     * 查询菜单名称
     *
     * @param roleId 主键
     * @return
     */
    @GetMapping(API_PREFIX + "/menu-names")
    List<String> getMenuNames(@RequestParam("roleId") Long roleId);

    /**
     * 查询所以菜单
     * @param menuType
     * @return
     */
    @PostMapping(API_PREFIX + "/menu-names-all")
    List<String> getMenuNamesAll(@RequestBody MenuType menuType);

    /**
     * 获取用户创建的角色
     * @param id
     * @param userType
     * @return
     */
    @PostMapping(API_PREFIX + "/roles")
    List<RolesVO> getRoles(@RequestParam("id") Long id, @RequestBody UserType userType);

    /**
     * 获取权限信息
     * @param id
     * @return
     */
    @GetMapping(API_PREFIX + "/get-role-menus-list")
    R<List<RoleMenusVO>> getRoleMenusList(@RequestParam("id") Long id);

    /**
     * 根据角色ID删除权限
     * @param roleId
     * @return
     */
    @GetMapping(API_PREFIX + "/remove-role")
    R removeRole(@RequestParam("id") Long roleId);

    /**
     * 根据菜单ID删除权限
     * @param menus
     * @return
     */
    @GetMapping(API_PREFIX + "/remove-role-menu")
    R removeRoleMenu(@RequestParam("menus") String menus);
}
