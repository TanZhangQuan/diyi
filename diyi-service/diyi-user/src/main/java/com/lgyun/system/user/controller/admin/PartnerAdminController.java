package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.AddPartnerDTO;
import com.lgyun.system.user.dto.admin.QueryPartnerDTO;
import com.lgyun.system.user.dto.admin.UpdatePartnerDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IPartnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 平台端---合伙人管理模块相关接口
 *
 * @author xjw
 * @date 2020/10/21.
 * @time 21:17.
 */
@RestController
@RequestMapping("/admin/partner")
@Validated
@AllArgsConstructor
@Api(value = "平台端---合伙人管理模块相关接口", tags = "平台端---合伙人管理模块相关接口")
public class PartnerAdminController {
    private IAdminService adminService;
    private IPartnerService partnerService;

    @GetMapping("/query-partner-list")
    @ApiOperation(value = "查询所有合伙人", notes = "查询所有合伙人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partnerId", value = "合伙人编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "name", value = "合伙人名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R queryAgentMainList(QueryPartnerDTO queryPartnerDTO, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return partnerService.getPartnerList(Condition.getPage(query.setDescs("create_time")), queryPartnerDTO);
    }

    @PostMapping("/modify-illegal")
    @ApiOperation(value = "非法", notes = "非法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partnerId", value = "合伙人编号", paramType = "query", dataType = "long")
    })
    public R modifyIllegal(@NotBlank(message = "请选择合伙人") Long partnerId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return partnerService.updateIllegal(partnerId, adminEntity);
    }

    @PostMapping("/modify-freeze")
    @ApiOperation(value = "冻结", notes = "冻结")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partnerId", value = "合伙人编号", paramType = "query", dataType = "long")
    })
    public R modifyFreeze(@NotBlank(message = "请选择合伙人") Long partnerId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return partnerService.updateFreeze(partnerId, adminEntity);
    }

    @PostMapping("/modify-normal")
    @ApiOperation(value = "正常", notes = "正常")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partnerId", value = "合伙人编号", paramType = "query", dataType = "long")
    })
    public R modifyNormal(@NotBlank(message = "请选择合伙人") Long partnerId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return partnerService.updateNormal(partnerId, adminEntity);
    }

    @PostMapping("/create-partner")
    @ApiOperation(value = "添加合伙人信息", notes = "添加合伙人信息")
    public R createPartner(@Valid @RequestBody AddPartnerDTO addPartnerDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return partnerService.addPartner(addPartnerDTO, adminEntity);
    }

    @PostMapping("/modify-partner")
    @ApiOperation(value = "编辑合伙人信息", notes = "编辑合伙人信息")
    public R modifyPartner(@Valid @RequestBody UpdatePartnerDTO updatePartnerDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return partnerService.updatePartner(updatePartnerDTO, adminEntity);
    }


}
