package com.lgyun.system.user.controller.partner;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.UpdatePartnerDeatilDTO;
import com.lgyun.system.user.dto.UpdatePhoneNumberDTO;
import com.lgyun.system.user.entity.PartnerEntity;
import com.lgyun.system.user.service.IPartnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/partner/home-page")
@Validated
@AllArgsConstructor
@Api(value = "合伙人端---首页管理模块相关接口", tags = "合伙人端---首页管理模块相关接口")
public class HomePagePartnerController {

    private IPartnerService partnerService;

    @GetMapping("/query-partner-info")
    @ApiOperation(value = "查询当前合伙人基本信息", notes = "查询当前合伙人基本信息")
    public R queryPartnerInfo(BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        PartnerEntity partnerEntity = result.getData();

        return partnerService.queryPartnerInfo(partnerEntity.getId());
    }

    @GetMapping("/query-current-partner-detail")
    @ApiOperation(value = "查询当前合伙人详情", notes = "查询当前合伙人详情")
    public R queryCurrentPartnerDetail(BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        PartnerEntity partnerEntity = result.getData();

        return partnerService.queryCurrentPartnerDetail(partnerEntity.getId());
    }

    @PostMapping("/update-partner-detail")
    @ApiOperation(value = "修改合伙人详情", notes = "修改合伙人详情")
    public R updatePartnerDetail(@Valid @RequestBody UpdatePartnerDeatilDTO updatePartnerDeatilDTO, BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        PartnerEntity partnerEntity = result.getData();

        return partnerService.updatePartnerDetail(updatePartnerDeatilDTO, partnerEntity);
    }

    @PostMapping("/update-phone-number")
    @ApiOperation(value = "修改手机号码", notes = "修改手机号码")
    public R updatePhoneNumber(@Valid @RequestBody UpdatePhoneNumberDTO updatePhoneNumberDTO, BladeUser bladeUser) {
        //查询当前合伙人
        R<PartnerEntity> result = partnerService.currentPartner(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        PartnerEntity partnerEntity = result.getData();

        return partnerService.updatePhoneNumber(updatePhoneNumberDTO, partnerEntity);
    }

}
