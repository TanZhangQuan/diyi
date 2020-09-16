package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.AddEnterpriseDTO;
import com.lgyun.system.user.dto.admin.QueryEnterpriseListDTO;
import com.lgyun.system.user.dto.admin.UpdateEnterpriseDTO;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.service.IEnterpriseServiceProviderService;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 平台端---商户管理controller
 *
 * @author tzq
 * @date 2020-09-9
 */
@Slf4j
@RestController
@RequestMapping("/admin/enterprise")
@Validated
@AllArgsConstructor
@Api(value = "平台端---商户管理模块相关接口", tags = "平台端---商户管理模块相关接口")
public class EnterpriseAdminController {

    private IEnterpriseService enterpriseService;
    private IEnterpriseServiceProviderService enterpriseProviderService;
    private IUserService userService;
    private IEnterpriseWorkerService enterpriseWorkerService;

    @PostMapping("/create-enterprise")
    @ApiOperation(value = "添加商户", notes = "添加商户")
    public R createEnterprise(@Valid @RequestBody AddEnterpriseDTO addEnterpriseDTO) {

        log.info("添加商户");
        try {
            return enterpriseService.createEnterprise(addEnterpriseDTO);
        } catch (Exception e) {
            log.error("添加商户异常", e);
        }

        return R.fail("操作失败");
    }

    @PostMapping("/update-enterprise")
    @ApiOperation(value = "修改商户", notes = "修改商户")
    public R updateEnterprise(@Valid @RequestBody UpdateEnterpriseDTO updateEnterpriseDTO) {

        log.info("修改商户");
        try {
            return enterpriseService.updateEnterprise(updateEnterpriseDTO);
        } catch (Exception e) {
            log.error("修改商户异常", e);
        }

        return R.fail("操作失败");
    }

    @GetMapping("/query-enterprise-list")
    @ApiOperation(value = "查询所有商户", notes = "查询所有商户")
    public R queryEnterpriseList(QueryEnterpriseListDTO queryEnterpriseListDTO, Query query) {

        log.info("查询所有商户");
        try {
            return enterpriseService.queryEnterpriseListEnterprise(queryEnterpriseListDTO, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询所有商户异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query-enterprise-detail")
    @ApiOperation(value = "查询商户基本信息", notes = "查询商户基本信息")
    public R queryEnterpriseDetail(@ApiParam(value = "商户ID") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId) {

        log.info("查询商户基本信息");
        try {
            return enterpriseService.queryEnterpriseDetailEnterprise(enterpriseId);
        } catch (Exception e) {
            log.error("查询商户基本信息异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query-enterprise-worker")
    @ApiOperation(value = "查询商户员工", notes = "查询商户员工")
    public R queryEnterpriseWorker(@ApiParam(value = "商户ID") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId,
                                   @ApiParam(value = "岗位性质") @NotNull(message = "请选择岗位性质") @RequestParam(required = false) PositionName positionName) {

        log.info("查询商户员工");
        try {
            return enterpriseWorkerService.queryEnterpriseWorkerEnterprise(enterpriseId, positionName);
        } catch (Exception e) {
            log.error("查询商户员工异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/update-enterprise-state")
    @ApiOperation(value = "更改商户状态", notes = "更改商户状态")
    public R updateEnterpriseState(@ApiParam(value = "商户ID") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId,
                                   @ApiParam(value = "商户状态") @NotNull(message = "请选择商户状态") @RequestParam(required = false) AccountState accountState) {

        log.info("更改商户状态");
        try {
            return enterpriseService.updateEnterpriseState(enterpriseId, accountState);
        } catch (Exception e) {
            log.error("更改商户状态异常", e);
        }

        return R.fail("更改商户状态失败");
    }

    @GetMapping("/query-service-provider-id-name-list")
    @ApiOperation(value = "查询所有服务商", notes = "查询所有服务商")
    public R queryServiceProviderIdNameList(@ApiParam(value = "商户名称") @RequestParam(required = false) String serviceProviderName, Query query) {

        log.info("查询所有服务商");
        try {
            return enterpriseProviderService.getServiceProviderByEnterpriseId(Condition.getPage(query.setDescs("create_time")), null, serviceProviderName);
        } catch (Exception e) {
            log.error("查询所有服务商异常", e);
        }

        return R.fail("查询失败");
    }

    @GetMapping("/query-enterprise-id-and-name")
    @ApiOperation(value = "查询商户编号名称", notes = "查询商户编号名称")
    public R queryEnterpriseIdAndName(@ApiParam(value = "商户ID") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId) {

        log.info("查询商户编号名称");
        try {
            return enterpriseService.queryEnterpriseIdAndName(enterpriseId);
        } catch (Exception e) {
            log.error("查询商户编号名称异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/relevance-enterprise-service-provider")
    @ApiOperation(value = "商户匹配服务商", notes = "商户匹配服务商")
    public R relevanceEnterpriseServiceProvider(@ApiParam(value = "商户ID") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId,
                                                @ApiParam(value = "服务商ID集合") @NotEmpty(message = "请选择服务商") @RequestParam(required = false) List<Long> serviceProviderIdList,
                                                BladeUser bladeUser) {
        log.info("商户匹配服务商");
        try {
            //查询当前管理员
            R<User> result = userService.currentUser(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            User user = result.getData();

            return enterpriseProviderService.relevanceEnterpriseServiceProvider(enterpriseId, serviceProviderIdList, user);
        } catch (Exception e) {
            log.error("商户匹配服务商异常", e);
        }

        return R.fail("商户匹配服务商失败");
    }

    @GetMapping("/query-cooperation-service-provider-list")
    @ApiOperation(value = "查询商户合作服务商", notes = "查询商户合作服务商")
    public R queryCooperationServiceProviderList(@ApiParam(value = "商户ID") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId, Query query) {

        log.info("查询商户合作服务商");
        try {
            return enterpriseService.queryCooperationServiceProviderList(enterpriseId, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询商户合作服务商异常", e);
        }
        return R.fail("查询失败");
    }

}
