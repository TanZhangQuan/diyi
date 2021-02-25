package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.CertificationState;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IdcardVerifyDTO;
import com.lgyun.system.user.dto.ImportMakerListDTO;
import com.lgyun.system.user.dto.MakerAddDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IAgreementService;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/natural-person-maker")
@Validated
@AllArgsConstructor
@Api(value = "平台端---自然人创客管理模块相关接口", tags = "平台端---自然人创客管理模块相关接口")
public class NaturalPersonMakerAdminController {

    private IAdminService adminService;
    private IMakerService makerService;
    private IAgreementService agreementService;

    @GetMapping("/query-maker-list")
    @ApiOperation(value = "查询所有创客", notes = "查询所有创客")
    public R queryMakerList(@ApiParam(value = "认证状态", required = true) @NotNull(message = "请选择认证状态") @RequestParam(required = false) CertificationState certificationState,
                            @ApiParam(value = "创客姓名/手机号/身份证号") @RequestParam(required = false) String keyword, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.queryMakerList(null, null, null, null, certificationState, keyword, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/create-maker")
    @ApiOperation(value = "新增单个创客", notes = "新增单个创客")
    public R createMaker(@Valid @RequestBody MakerAddDTO makerAddDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.makerAdd(makerAddDto, null);
    }

    @PostMapping("read-maker-list-excel")
    @ApiOperation(value = "读取Excel表获取导入的创客列表", notes = "读取Excel表获取导入的创客列表")
    public R readMakerListExcel(@ApiParam(value = "Excel文件", required = true) @NotNull(message = "请选择Excel文件") @RequestParam(required = false) MultipartFile file, BladeUser bladeUser) throws IOException {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.readMakerListExcel(file);
    }

    @PostMapping("import-maker-list")
    @ApiOperation(value = "导入创客", notes = "导入创客")
    public R importMakerList(@Valid @RequestBody List<ImportMakerListDTO> importMakerListDTOList, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.importMaker(importMakerListDTOList, null);
    }

    @GetMapping("/query-maker-detail")
    @ApiOperation(value = "查询创客详情", notes = "查询创客详情")
    public R queryMakerDetail(@ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.queryMakerDetail(makerId);
    }

    @PostMapping("/idcard-ocr")
    @ApiOperation(value = "创客身份证正面信息获取", notes = "创客身份证正面信息获取")
    public R idcardOcr(@ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId,
                       @ApiParam(value = "正面照片") @NotBlank(message = "请选择正面照片") @RequestParam(required = false) String idcardPic, BladeUser bladeUser) throws Exception {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.idcardOcr(idcardPic, makerId);
    }

    @PostMapping("/idcard-verify")
    @ApiOperation(value = "创客身份证认证", notes = "创客身份证认证")
    public R idcardVerify(@ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId,
                          @Valid @RequestBody IdcardVerifyDTO idcardVerifyDTO, BladeUser bladeUser) throws Exception {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.idcardVerify(idcardVerifyDTO, makerId);
    }

    @PostMapping("/mobile-ocr")
    @ApiOperation(value = "手机号认证", notes = "手机号认证")
    public R mobileOcr(@ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId, BladeUser bladeUser) throws Exception {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.mobileVerify(makerId);
    }

    @PostMapping("/bank-card-ocr")
    @ApiOperation(value = "银行卡认证", notes = "银行卡认证")
    public R bankCardOcr(@ApiParam(value = "银行卡号", required = true) @NotBlank(message = "请输入银行卡号") @RequestParam(required = false) String bankCardNo,
                         @ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId, BladeUser bladeUser) throws Exception {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.bankCardVerify(bankCardNo, makerId);
    }

    @PostMapping("/create-agreement")
    @ApiOperation(value = "创客添加合同", notes = "创客添加合同")
    public R createAgreement(@ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId,
                             @ApiParam(value = "合同协议类别", required = true) @NotNull(message = "请选择合同协议类别") @RequestParam(required = false) AgreementType agreementType,
                             @ApiParam(value = "合同协议", required = true) @NotBlank(message = "请上传合同协议") @RequestParam(required = false) String agreementUrl, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.saveAdminAgreement(makerId, null, null, makerId, ObjectType.MAKERPEOPLE, agreementType, agreementUrl);
    }


}
