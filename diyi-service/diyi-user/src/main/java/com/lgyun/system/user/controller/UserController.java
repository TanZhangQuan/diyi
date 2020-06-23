package com.lgyun.system.user.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.service.IUserService;
import com.lgyun.system.user.wrapper.UserWrapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.Charsets;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.SecureUtil;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.excel.UserExcel;
import com.lgyun.system.user.excel.UserImportListener;
import com.lgyun.system.user.vo.UserVO;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author liangfeihu
 * @since 2020/6/6 22:13
 */
@RestController
@RequestMapping
@AllArgsConstructor
public class UserController {

    private IUserService userService;

    /**
     * 查询单条
     */
    @ApiOperation(value = "查看详情", notes = "传入id")
    @GetMapping("/detail")
    public R<UserVO> detail(User user) {
        User detail = userService.getOne(Condition.getQueryWrapper(user));
        return R.data(UserWrapper.build().entityVO(detail));
    }

    /**
     * 查询单条
     */
    @ApiOperation(value = "查看详情", notes = "传入id")
    @GetMapping("/info")
    public R<UserVO> info(BladeUser user) {
        User detail = userService.getById(user.getUserId());
        return R.data(UserWrapper.build().entityVO(detail));
    }

    /**
     * 用户列表
     */
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号名", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "realName", value = "姓名", paramType = "query", dataType = "string")
    })
    @ApiOperation(value = "列表", notes = "传入account和realName")
    public R<IPage<UserVO>> list(@ApiIgnore @RequestParam Map<String, Object> user, Query query, BladeUser bladeUser) {
        QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
        IPage<User> pages = userService.page(Condition.getPage(query), (!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(User::getTenantId, bladeUser.getTenantId()) : queryWrapper);
        return R.data(UserWrapper.build().pageVO(pages));
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperation(value = "新增或修改", notes = "传入User")
    public R submit(@Valid @RequestBody User user) {
        return R.status(userService.submit(user));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "传入User")
    public R update(@Valid @RequestBody User user) {
        return R.status(userService.updateById(user));
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "传入地基和")
    public R remove(@RequestParam String ids) {
        return R.status(userService.deleteLogic(Func.toLongList(ids)));
    }


    /**
     * 设置菜单权限
     *
     * @param userIds
     * @param roleIds
     * @return
     */
    @PostMapping("/grant")
    @ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
    public R grant(@ApiParam(value = "userId集合", required = true) @RequestParam String userIds,
                   @ApiParam(value = "roleId集合", required = true) @RequestParam String roleIds) {
        boolean temp = userService.grant(userIds, roleIds);
        return R.status(temp);
    }

    @PostMapping("/reset-password")
    @ApiOperation(value = "初始化密码", notes = "传入userId集合")
    public R resetPassword(@ApiParam(value = "userId集合", required = true) @RequestParam String userIds) {
        boolean temp = userService.resetPassword(userIds);
        return R.status(temp);
    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param newPassword1
     * @return
     */
    @PostMapping("/update-password")
    @ApiOperation(value = "修改密码", notes = "传入密码")
    public R updatePassword(BladeUser user, @ApiParam(value = "旧密码", required = true) @RequestParam String oldPassword,
                            @ApiParam(value = "新密码", required = true) @RequestParam String newPassword,
                            @ApiParam(value = "新密码", required = true) @RequestParam String newPassword1) {
        boolean temp = userService.updatePassword(user.getUserId(), oldPassword, newPassword, newPassword1);
        return R.status(temp);
    }

    /**
     * 用户列表
     *
     * @param user
     * @return
     */
    @GetMapping("/user-list")
    @ApiOperation(value = "用户列表", notes = "传入user")
    public R<List<User>> userList(User user) {
        List<User> list = userService.list(Condition.getQueryWrapper(user));
        return R.data(list);
    }


    /**
     * 导入用户
     */
    @PostMapping("import-user")
    @ApiOperation(value = "导入用户", notes = "传入excel")
    public R importUser(MultipartFile file, Integer isCovered) {
        String filename = file.getOriginalFilename();
        if (StringUtils.isEmpty(filename)) {
            throw new RuntimeException("请上传文件!");
        }
        if ((!StringUtils.endsWithIgnoreCase(filename, ".xls") && !StringUtils.endsWithIgnoreCase(filename, ".xlsx"))) {
            throw new RuntimeException("请上传正确的excel文件!");
        }
        InputStream inputStream;
        try {
            UserImportListener importListener = new UserImportListener(userService);
            inputStream = new BufferedInputStream(file.getInputStream());
            ExcelReaderBuilder builder = EasyExcel.read(inputStream, UserExcel.class, importListener);
            builder.doReadAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success("操作成功");
    }

    /**
     * 导出用户
     */
    @SneakyThrows
    @GetMapping("export-user")
    @ApiOperation(value = "导出用户", notes = "传入user")
    public void exportUser(@ApiIgnore @RequestParam Map<String, Object> user, BladeUser bladeUser, HttpServletResponse response) {
        QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
        if (!SecureUtil.isAdministrator()) {
            queryWrapper.lambda().eq(User::getTenantId, bladeUser.getTenantId());
        }
        queryWrapper.lambda().eq(User::getIsDeleted, BladeConstant.DB_NOT_DELETED);
        List<UserExcel> list = userService.exportUser(queryWrapper);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(Charsets.UTF_8.name());
        String fileName = URLEncoder.encode("用户数据导出", Charsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), UserExcel.class).sheet("用户数据表").doWrite(list);
    }

    /**
     * 导出模板
     */
    @SneakyThrows
    @GetMapping("export-template")
    @ApiOperation(value = "导出模板")
    public void exportUser(HttpServletResponse response) {
        List<UserExcel> list = new ArrayList<>();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(Charsets.UTF_8.name());
        String fileName = URLEncoder.encode("用户数据模板", Charsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), UserExcel.class).sheet("用户数据表").doWrite(list);
    }

}
