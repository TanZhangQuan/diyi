package com.lgyun.system.user.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IdcardVerifyDTO;
import com.lgyun.system.user.dto.ImportMakerListDTO;
import com.lgyun.system.user.dto.MakerAddDTO;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/enterprise/natural-person-maker")
@Validated
@AllArgsConstructor
@Api(value = "商户端---自然人创客管理模块相关接口", tags = "商户端---自然人创客管理模块相关接口")
public class NaturalPersonMakerEnterpriseController {

    private IMakerService makerService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IEnterpriseWorkerService enterpriseWorkerService;

    @GetMapping("/query-relevance-maker-list")
    @ApiOperation(value = "查询当前商户的所有关联或关注创客", notes = "查询当前商户的所有关联或关注创客")
    public R queryRelevanceMakerList(@ApiParam(value = "创客商户关系") @NotNull(message = "请选择创客商户关系") @RequestParam(required = false) RelationshipType relationshipType,
                                     @ApiParam(value = "创客姓名/手机号/身份证号") @RequestParam(required = false) String keyword, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return makerService.queryMakerList(enterpriseWorkerEntity.getEnterpriseId(), null, null, relationshipType, null, keyword, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/create-maker")
    @ApiOperation(value = "新增单个创客", notes = "新增单个创客")
    public R createMaker(@Valid @RequestBody MakerAddDTO makerAddDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return makerService.makerAdd(makerAddDto, enterpriseWorkerEntity.getEnterpriseId());
    }

    @PostMapping("read-maker-list-excel")
    @ApiOperation(value = "导入创客Excel文件读取", notes = "导入创客Excel文件读取")
    public R readMakerListExcel(@ApiParam(value = "Excel文件", required = true) @NotNull(message = "请选择Excel文件") @RequestParam(required = false) MultipartFile file, BladeUser bladeUser) throws IOException {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.readMakerListExcel(file);
    }

    @PostMapping("import-maker-list")
    @ApiOperation(value = "导入创客", notes = "导入创客")
    public R importMakerList(@Valid @RequestBody List<ImportMakerListDTO> importMakerListDTOList, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return makerService.importMaker(importMakerListDTOList, enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query-maker-detail")
    @ApiOperation(value = "查询创客详情", notes = "查询创客详情")
    public R queryMakerDetail(@ApiParam(value = "创客") @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.queryMakerDetail(makerId);
    }

    @PostMapping("/cancel-relevance-or-attention-maker-list")
    @ApiOperation(value = "批量取消创客关联或关注", notes = "批量取消创客关联或关注")
    public R cancelRelevanceOrAttentionMakerList(@ApiParam(value = "创客") @NotEmpty(message = "请选择要取消关联或关注的创客") @RequestParam(required = false) Set<Long> makerIds, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return makerEnterpriseService.cancelRelevanceOrAttentionMakerList(makerIds, enterpriseWorkerEntity.getEnterpriseId());
    }

    @PostMapping("/relevance-maker-list")
    @ApiOperation(value = "批量关联创客", notes = "批量关联创客")
    public R relevanceMakerList(@ApiParam(value = "创客") @NotEmpty(message = "请选择要关联的创客") @RequestParam(required = false) Set<Long> makerIds, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return makerEnterpriseService.relevanceMakerList(makerIds, enterpriseWorkerEntity.getEnterpriseId());
    }

    @PostMapping("/idcard-ocr")
    @ApiOperation(value = "创客身份证正面信息获取", notes = "创客身份证正面信息获取")
    public R idcardOcr(@ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId,
                       @ApiParam(value = "正面照片") @NotBlank(message = "请选择正面照片") @RequestParam(required = false) String idcardPic, BladeUser bladeUser) throws Exception {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.idcardOcr(idcardPic, makerId);
    }

    @PostMapping("/idcard-verify")
    @ApiOperation(value = "创客身份证认证", notes = "创客身份证认证")
    public R idcardVerify(@ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId,
                          @Valid @RequestBody IdcardVerifyDTO idcardVerifyDTO, BladeUser bladeUser) throws Exception {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.idcardVerify(idcardVerifyDTO, makerId);
    }

    @PostMapping("/mobile-ocr")
    @ApiOperation(value = "手机号认证", notes = "手机号认证")
    public R mobileOcr(@ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId, BladeUser bladeUser) throws Exception {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.mobileVerify(makerId);
    }

    @PostMapping("/bank-card-ocr")
    @ApiOperation(value = "银行卡认证", notes = "银行卡认证")
    public R bankCardOcr(@ApiParam(value = "银行卡号", required = true) @NotBlank(message = "请输入银行卡号") @RequestParam(required = false) String bankCardNo,
                         @ApiParam(value = "创客", required = true) @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId, BladeUser bladeUser) throws Exception {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.bankCardVerify(bankCardNo, makerId);
    }

}
