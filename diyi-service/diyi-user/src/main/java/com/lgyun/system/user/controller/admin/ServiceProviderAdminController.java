package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.AddOrUpdateServiceProviderCertDTO;
import com.lgyun.system.user.dto.admin.AddServiceProviderDTO;
import com.lgyun.system.user.dto.admin.QueryServiceProviderListDTO;
import com.lgyun.system.user.dto.admin.UpdateServiceProviderDTO;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.service.IServiceProviderCertService;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import com.lgyun.system.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 平台端---服务商管理controller
 *
 * @author tzq
 * @date 2020-09-9
 */
@Slf4j
@RestController
@RequestMapping("/admin/service-provider")
@Validated
@AllArgsConstructor
@Api(value = "平台端---服务商管理模块相关接口", tags = "平台端---服务商管理模块相关接口")
public class ServiceProviderAdminController {

    private IServiceProviderService serviceProviderService;
    private IUserService userService;
    private IServiceProviderWorkerService serviceProviderWorkerService;
    private IServiceProviderCertService serviceProviderCertService;

    @PostMapping("/create-service-provider")
    @ApiOperation(value = "添加服务商", notes = "添加服务商")
    public R createEnterprise(@Valid @RequestBody AddServiceProviderDTO addServiceProviderDTO, BladeUser bladeUser) {

        log.info("添加服务商");
        try {
            //查询当前管理员
            R<User> result = userService.currentUser(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            User user = result.getData();

            return serviceProviderService.createServiceProvider(addServiceProviderDTO, user);
        } catch (Exception e) {
            log.error("添加服务商异常", e);
        }

        return R.fail("操作失败");
    }

    @PostMapping("/update-service-provider")
    @ApiOperation(value = "修改服务商", notes = "修改服务商")
    public R updateServiceProvider(@Valid @RequestBody UpdateServiceProviderDTO updateServiceProviderDTO, BladeUser bladeUser) {

        log.info("修改服务商");
        try {
            //查询当前管理员
            R<User> result = userService.currentUser(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            User user = result.getData();

            return serviceProviderService.updateServiceProvider(updateServiceProviderDTO, user);
        } catch (Exception e) {
            log.error("修改服务商异常", e);
        }

        return R.fail("操作失败");
    }

    @GetMapping("/query-service-provider-list")
    @ApiOperation(value = "查询所有服务商", notes = "查询所有服务商")
    public R queryServiceProviderList(QueryServiceProviderListDTO queryServiceProviderListDTO, Query query) {

        log.info("查询所有服务商");
        try {
            return serviceProviderService.queryServiceProviderListAdmin(queryServiceProviderListDTO, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询所有服务商异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query-service-provider-detail")
    @ApiOperation(value = "查询服务商基本信息", notes = "查询服务商基本信息")
    public R queryServiceProviderDetail(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long serviceProviderId) {

        log.info("查询服务商基本信息");
        try {
            return serviceProviderService.queryServiceProviderDetailServiceProvider(serviceProviderId);
        } catch (Exception e) {
            log.error("查询服务商基本信息异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query-service-provider-cert-list")
    @ApiOperation(value = "查询服务商资格信息", notes = "查询服务商资格信息")
    public R queryServiceProviderCertList(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long serviceProviderId, Query query) {

        log.info("查询服务商资格信息");
        try {
            return serviceProviderCertService.queryServiceProviderCertList(serviceProviderId, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询服务商资格信息异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/add-or-update-service-provider-cert")
    @ApiOperation(value = "添加或修改服务商资格信息", notes = "添加或修改服务商资格信息")
    public R addOrUpdateServiceProviderCert(@Valid @RequestBody AddOrUpdateServiceProviderCertDTO addOrUpdateServiceProviderCertDTO) {
        log.info("添加或修改服务商资格信息");
        try {
            return serviceProviderCertService.addOrUpdateServiceProviderCert(addOrUpdateServiceProviderCertDTO);
        } catch (Exception e) {
            log.error("添加或修改服务商资格信息异常", e);
        }

        return R.fail("操作失败");
    }

    @PostMapping("/remove-service-provider-cert")
    @ApiOperation(value = "删除服务商资格信息", notes = "删除服务商资格信息")
    public R removeServiceProviderCert(@ApiParam(value = "服务商资格信息ID集合", required = true) @NotBlank(message = "请选择要删除的服务商资格信息") @RequestParam(required = false) String ids) {

        log.info("删除服务商资格信息");
        try {
            return R.status(serviceProviderCertService.removeByIds(Func.toLongList(ids)));
        } catch (Exception e) {
            log.error("删除服务商资格信息异常", e);
        }

        return R.fail("删除失败");
    }

    @GetMapping("/query-service-provider-worker-list")
    @ApiOperation(value = "查询服务商员工", notes = "查询服务商员工")
    public R queryServiceProviderWorkerList(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long serviceProviderId,
                                            @ApiParam(value = "岗位性质") @NotNull(message = "请选择岗位性质") @RequestParam(required = false) PositionName positionName) {

        log.info("查询服务商员工");
        try {
            return serviceProviderWorkerService.queryServiceProviderWorkerList(serviceProviderId, positionName);
        } catch (Exception e) {
            log.error("查询服务商员工异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/update-service-provider-state")
    @ApiOperation(value = "更改服务商状态", notes = "更改服务商状态")
    public R updateServiceProviderState(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long serviceProviderId,
                                        @ApiParam(value = "服务商状态") @NotNull(message = "请选择服务商状态") @RequestParam(required = false) AccountState serviceProviderState) {

        log.info("更改服务商状态");
        try {
            return serviceProviderService.updateServiceProviderState(serviceProviderId, serviceProviderState);
        } catch (Exception e) {
            log.error("更改服务商状态异常", e);
        }

        return R.fail("更改服务商状态失败");
    }

}
