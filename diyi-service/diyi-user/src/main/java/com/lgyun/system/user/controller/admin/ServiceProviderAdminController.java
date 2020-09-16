package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.QueryServiceProviderListDTO;
import com.lgyun.system.user.service.IServiceProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//    private IserviceProviderServiceProviderService enterpriseProviderService;
//    private IUserService userService;
//    private IEnterpriseWorkerService enterpriseWorkerService;
//
//    @PostMapping("/create-enterprise")
//    @ApiOperation(value = "添加服务商", notes = "添加服务商")
//    public R createEnterprise(@Valid @RequestBody AddEnterpriseDTO addEnterpriseDTO) {
//
//        log.info("添加服务商");
//        try {
//            return serviceProviderService.createEnterprise(addEnterpriseDTO);
//        } catch (Exception e) {
//            log.error("添加服务商异常", e);
//        }
//
//        return R.fail("操作失败");
//    }
//
//    @PostMapping("/update-enterprise")
//    @ApiOperation(value = "修改服务商", notes = "修改服务商")
//    public R updateEnterprise(@Valid @RequestBody UpdateEnterpriseDTO updateEnterpriseDTO) {
//
//        log.info("修改服务商");
//        try {
//            return serviceProviderService.updateEnterprise(updateEnterpriseDTO);
//        } catch (Exception e) {
//            log.error("修改服务商异常", e);
//        }
//
//        return R.fail("操作失败");
//    }

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

//    @GetMapping("/query-enterprise-detail")
//    @ApiOperation(value = "查询服务商基本信息", notes = "查询服务商基本信息")
//    public R queryEnterpriseDetail(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long enterpriseId) {
//
//        log.info("查询服务商基本信息");
//        try {
//            return serviceProviderService.queryEnterpriseDetailEnterprise(enterpriseId);
//        } catch (Exception e) {
//            log.error("查询服务商基本信息异常", e);
//        }
//        return R.fail("查询失败");
//    }
//
//    @GetMapping("/query-enterprise-worker")
//    @ApiOperation(value = "查询服务商员工", notes = "查询服务商员工")
//    public R queryEnterpriseWorker(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long enterpriseId,
//                                   @ApiParam(value = "岗位性质") @NotNull(message = "请选择岗位性质") @RequestParam(required = false) PositionName positionName) {
//
//        log.info("查询服务商员工");
//        try {
//            return enterpriseWorkerService.queryEnterpriseWorkerEnterprise(enterpriseId, positionName);
//        } catch (Exception e) {
//            log.error("查询服务商员工异常", e);
//        }
//        return R.fail("查询失败");
//    }
//
//    @PostMapping("/update-enterprise-state")
//    @ApiOperation(value = "更改服务商状态", notes = "更改服务商状态")
//    public R updateEnterpriseState(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long enterpriseId,
//                                   @ApiParam(value = "服务商状态") @NotNull(message = "请选择服务商状态") @RequestParam(required = false) AccountState accountState) {
//
//        log.info("更改服务商状态");
//        try {
//            return serviceProviderService.updateEnterpriseState(enterpriseId, accountState);
//        } catch (Exception e) {
//            log.error("更改服务商状态异常", e);
//        }
//
//        return R.fail("更改服务商状态失败");
//    }
//
//    @GetMapping("/query-service-provider-id-name-list")
//    @ApiOperation(value = "查询所有服务商", notes = "查询所有服务商")
//    public R queryServiceProviderIdNameList(@ApiParam(value = "服务商名称") @RequestParam(required = false) String serviceProviderName, Query query) {
//
//        log.info("查询所有服务商");
//        try {
//            return enterpriseProviderService.getServiceProviderByEnterpriseId(Condition.getPage(query.setDescs("create_time")), null, serviceProviderName);
//        } catch (Exception e) {
//            log.error("查询所有服务商异常", e);
//        }
//
//        return R.fail("查询失败");
//    }
//
//    @GetMapping("/query-enterprise-id-and-name")
//    @ApiOperation(value = "查询服务商编号名称", notes = "查询服务商编号名称")
//    public R queryEnterpriseIdAndName(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long enterpriseId) {
//
//        log.info("查询服务商编号名称");
//        try {
//            return serviceProviderService.queryEnterpriseIdAndName(enterpriseId);
//        } catch (Exception e) {
//            log.error("查询服务商编号名称异常", e);
//        }
//        return R.fail("查询失败");
//    }
//
//    @PostMapping("/relevance-enterprise-service-provider")
//    @ApiOperation(value = "服务商匹配服务商", notes = "服务商匹配服务商")
//    public R relevanceserviceProviderServiceProvider(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long enterpriseId,
//                                                @ApiParam(value = "服务商ID集合") @NotEmpty(message = "请选择服务商") @RequestParam(required = false) List<Long> serviceProviderIdList,
//                                                BladeUser bladeUser) {
//        log.info("服务商匹配服务商");
//        try {
//            //查询当前管理员
//            R<User> result = userService.currentUser(bladeUser);
//            if (!(result.isSuccess())) {
//                return result;
//            }
//            User user = result.getData();
//
//            return enterpriseProviderService.relevanceserviceProviderServiceProvider(enterpriseId, serviceProviderIdList, user);
//        } catch (Exception e) {
//            log.error("服务商匹配服务商异常", e);
//        }
//
//        return R.fail("服务商匹配服务商失败");
//    }
//
//    @GetMapping("/query-cooperation-service-provider-list")
//    @ApiOperation(value = "查询服务商合作服务商", notes = "查询服务商合作服务商")
//    public R queryCooperationServiceProviderList(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long enterpriseId, Query query) {
//
//        log.info("查询服务商合作服务商");
//        try {
//            return serviceProviderService.queryCooperationServiceProviderList(enterpriseId, Condition.getPage(query.setDescs("create_time")));
//        } catch (Exception e) {
//            log.error("查询服务商合作服务商异常", e);
//        }
//        return R.fail("查询失败");
//    }

}
