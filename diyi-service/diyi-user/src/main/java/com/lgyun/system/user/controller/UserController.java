package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.service.IUserService;
import com.lgyun.system.user.wrapper.UserWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * User 控制器
 *
 * @author liangfeihu
 * @since 2020/6/6 22:13
 */
@Slf4j
@RestController
@RequestMapping("/web/user")
@AllArgsConstructor
@Validated
@Api(value = "平台端-管理员登陆模块相关接口", tags = "平台端-管理员登陆模块相关接口")
public class UserController {

    private IUserService userService;

    @GetMapping("/current-detail")
    @ApiOperation(value = "获取当前管理员详情", notes = "获取当前管理员详情")
    public R currentDetail(BladeUser bladeUser) {

        log.info("获取当前管理员详情");
        try {
            //获取当前管理员
            R<User> result = userService.currentUser(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            User user = result.getData();

            return R.data(UserWrapper.build().entityVO(user));
        } catch (Exception e) {
            log.error("获取当前管理员详情异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/update-password")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public R updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) {

        log.info("修改密码");
        try {
            return userService.updatePassword(updatePasswordDto);
        } catch (Exception e) {
            log.error("修改密码异常", e);
        }
        return R.fail("修改失败");
    }
    
//    @ApiOperation(value = "详情", notes = "详情")
//    @GetMapping("/detail")
//    public R detail(User user) {
//        User detail = userService.getOne(Condition.getQueryWrapper(user));
//        return R.data(UserWrapper.build().entityVO(detail));
//    }
//
//    @ApiOperation(value = "查看详情", notes = "传入id")
//    @GetMapping("/info")
//    public R info(BladeUser user) {
//        User detail = userService.getById(user.getUserId());
//        return R.data(UserWrapper.build().entityVO(detail));
//    }
//
//    @GetMapping("/list")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "account", value = "账号名", paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = "realName", value = "姓名", paramType = "query", dataType = "string")
//    })
//    @ApiOperation(value = "列表", notes = "分页")
//    public R list(@ApiIgnore @RequestParam Map<String, Object> user, Query query, BladeUser bladeUser) {
//        QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
//        IPage<User> pages = userService.page(Condition.getPage(query.setDescs("create_time")), (!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(User::getTenantId, bladeUser.getTenantId()) : queryWrapper);
//        return R.data(UserWrapper.build().pageVO(pages));
//    }
//
//    @PostMapping("/submit")
//    @ApiOperation(value = "新增或修改", notes = "传入User")
//    public R submit(@Valid @RequestBody User user) {
//        return R.status(userService.submit(user));
//    }
//
//    @PostMapping("/update")
//    @ApiOperation(value = "修改", notes = "修改")
//    public R update(@Valid @RequestBody User user) {
//        return R.status(userService.updateById(user));
//    }
//
//    @PostMapping("/remove")
//    @ApiOperation(value = "删除", notes = "删除")
//    public R remove(@RequestParam String ids) {
//        return R.status(userService.deleteLogic(Func.toLongList(ids)));
//    }
//
//    @PostMapping("/grant")
//    @ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
//    public R grant(@ApiParam(value = "userId集合", required = true) @RequestParam String userIds,
//                   @ApiParam(value = "roleId集合", required = true) @RequestParam String roleIds) {
//        boolean temp = userService.grant(userIds, roleIds);
//        return R.status(temp);
//    }
//
//    @PostMapping("/reset-password")
//    @ApiOperation(value = "初始化密码", notes = "传入userId集合")
//    public R resetPassword(@ApiParam(value = "userId集合", required = true) @RequestParam String userIds) {
//        boolean temp = userService.resetPassword(userIds);
//        return R.status(temp);
//    }
//
//    @GetMapping("/user-list")
//    @ApiOperation(value = "用户列表", notes = "传入user")
//    public R userList(User user) {
//        List<User> list = userService.list(Condition.getQueryWrapper(user));
//        return R.data(list);
//    }
//
//    @PostMapping("import-user")
//    @ApiOperation(value = "导入用户", notes = "传入excel")
//    public R importUser(MultipartFile file, Integer isCovered) {
//        String filename = file.getOriginalFilename();
//        if (StringUtils.isEmpty(filename)) {
//            throw new RuntimeException("请上传文件!");
//        }
//        if ((!StringUtils.endsWithIgnoreCase(filename, ".xls") && !StringUtils.endsWithIgnoreCase(filename, ".xlsx"))) {
//            throw new RuntimeException("请上传正确的excel文件!");
//        }
//        InputStream inputStream;
//        try {
//            UserImportListener importListener = new UserImportListener(userService);
//            inputStream = new BufferedInputStream(file.getInputStream());
//            ExcelReaderBuilder builder = EasyExcel.read(inputStream, UserExcel.class, importListener);
//            builder.doReadAll();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return R.success("操作成功");
//    }
//
//    @SneakyThrows
//    @GetMapping("export-user")
//    @ApiOperation(value = "导出用户", notes = "传入user")
//    public void exportUser(@ApiIgnore @RequestParam Map<String, Object> user, BladeUser bladeUser, HttpServletResponse response) {
//        QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
//        if (!SecureUtil.isAdministrator()) {
//            queryWrapper.lambda().eq(User::getTenantId, bladeUser.getTenantId());
//        }
//        queryWrapper.lambda().eq(User::getIsDeleted, BladeConstant.DB_NOT_DELETED);
//        List<UserExcel> list = userService.exportUser(queryWrapper);
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding(Charsets.UTF_8.name());
//        String fileName = URLEncoder.encode("用户数据导出", Charsets.UTF_8.name());
//        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//        EasyExcel.write(response.getOutputStream(), UserExcel.class).sheet("用户数据表").doWrite(list);
//    }
//
//    @SneakyThrows
//    @GetMapping("export-template")
//    @ApiOperation(value = "导出模板")
//    public void exportUser(HttpServletResponse response) {
//        List<UserExcel> list = new ArrayList<>();
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding(Charsets.UTF_8.name());
//        String fileName = URLEncoder.encode("用户数据模板", Charsets.UTF_8.name());
//        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//        EasyExcel.write(response.getOutputStream(), UserExcel.class).sheet("用户数据表").doWrite(list);
//    }

}
