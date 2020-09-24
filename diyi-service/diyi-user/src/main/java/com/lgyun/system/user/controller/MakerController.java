package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.IdcardOcrSaveDto;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.wrapper.MakerWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 控制器
 *
 * @author tzq
 * @since 2020-06-26 17:21:06
 */
@RestController
@RequestMapping("/maker")
@Validated
@AllArgsConstructor
@Api(value = "创客的基本信息相关接口", tags = "创客的基本信息相关接口")
public class MakerController {

    private IMakerService makerService;

    @PostMapping("/idcard_ocr")
    @ApiOperation(value = "身份证实名认证", notes = "身份证实名认证")
    public R idcardOcr(@ApiParam(value = "正面照片") @NotNull(message = "请选择正面照片") @RequestParam(required = false) String idcardPic, BladeUser bladeUser) throws Exception {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.idcardOcr(idcardPic, makerEntity);
    }

    @PostMapping("/idcard_ocr_save")
    @ApiOperation(value = "身份证实名认证信息保存", notes = "身份证实名认证信息保存")
    public R idcardOcrSave(@Valid @RequestBody IdcardOcrSaveDto idcardOcrSaveDto, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.idcardOcrSave(idcardOcrSaveDto, makerEntity);
    }

    @PostMapping("/face_ocr")
    @ApiOperation(value = "活体认证", notes = "活体认证")
    public R faceOcr(BladeUser bladeUser) throws Exception {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.faceOcr(makerEntity);
    }

    @PostMapping("/face_ocr_notify")
    @ApiOperation(value = "活体认证异步回调", notes = "活体认证异步回调")
    public R faceOcrNotify(HttpServletRequest request) throws Exception {
        return makerService.faceOcrNotify(request);
    }

    @PostMapping("/bank_card_ocr")
    @ApiOperation(value = "银行卡实名认证", notes = "银行卡实名认证")
    public R bankCardOcr(@ApiParam(value = "银行卡号") @NotNull(message = "请输入银行卡号") @RequestParam(required = false) String bankCardNo, BladeUser bladeUser) throws Exception {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.bankCardOcr(bankCardNo, makerEntity);
    }

    @PostMapping("/bank_card_ocr_notify")
    @ApiOperation(value = "银行卡实名认证异步回调", notes = "银行卡实名认证异步回调")
    public R bankCardOcrNotify(HttpServletRequest request) throws Exception {
        return makerService.bankCardOcrNotify(request);
    }

    @PostMapping("/mobile_ocr")
    @ApiOperation(value = "手机号实名认证", notes = "手机号实名认证")
    public R mobileOcr(BladeUser bladeUser) throws Exception {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.mobileOcr(makerEntity);
    }

    @PostMapping("/mobile_ocr_notify")
    @ApiOperation(value = "手机号实名认证异步回调", notes = "手机号实名认证异步回调")
    public R mobileOcrNotify(HttpServletRequest request) throws Exception {
        return makerService.mobileOcrNotify(request);
    }

    @PostMapping("/query_idcard_ocr")
    @ApiOperation(value = "查询当前创客身份证实名认证的照片", notes = "查询当前创客身份证实名认证的照片")
    public R queryIdcardOcr(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.queryIdcardOcr(makerEntity);
    }

    @GetMapping("/get-real-name-authentication-state")
    @ApiOperation(value = "查询当前创客所有实名认证状态", notes = "查询当前创客所有实名认证状态")
    public R getRealNameAuthenticationState(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.getRealNameAuthenticationState(makerEntity.getId());
    }

    @GetMapping("/get-info")
    @ApiOperation(value = "查询当前创客基本信息", notes = "查询当前创客基本信息")
    public R getInfo(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.getInfo(makerEntity.getId());
    }

    @GetMapping("/get-enterprise-num-income")
    @ApiOperation(value = "查询当前创客关联商户数和收入情况", notes = "查询当前创客关联商户数和收入情况")
    public R getEnterpriseNumIncome(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.getEnterpriseNumIncome(makerEntity.getId());
    }

    @GetMapping("/current-detail")
    @ApiOperation(value = "查询当前创客详情", notes = "查询当前创客详情")
    public R currentDetail(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return R.data(MakerWrapper.build().entityVO(makerEntity));
    }

}
