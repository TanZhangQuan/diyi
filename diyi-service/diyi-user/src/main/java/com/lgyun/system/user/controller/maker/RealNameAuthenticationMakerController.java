package com.lgyun.system.user.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.IdcardVerifyDTO;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/maker/real-name-authentication")
@Validated
@AllArgsConstructor
@Api(value = "创客端---实名认证管理模块相关接口", tags = "创客端---实名认证管理模块相关接口")
public class RealNameAuthenticationMakerController {

    private IMakerService makerService;

    @GetMapping("/query-real-name-authentication-states")
    @ApiOperation(value = "查询当前创客所有实名认证状态", notes = "查询当前创客所有实名认证状态")
    public R queryRealNameAuthenticationStates(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.getRealNameAuthenticationState(makerEntity.getId());
    }

    @GetMapping("/query-idcard-info")
    @ApiOperation(value = "查询当前创客身份证信息", notes = "查询当前创客身份证信息")
    public R queryIdcardInfo(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.queryIdcardOcr(makerEntity);
    }

    @PostMapping("/idcard-ocr")
    @ApiOperation(value = "身份证正面信息获取", notes = "身份证正面信息获取")
    public R idcardOcr(@ApiParam(value = "正面照片") @NotNull(message = "请选择正面照片") @RequestParam(required = false) String idcardPic, BladeUser bladeUser) throws Exception {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.idcardOcr(idcardPic, makerEntity);
    }

    @PostMapping("/idcard-verify")
    @ApiOperation(value = "身份证认证", notes = "身份证认证")
    public R idcardVerify(@Valid @RequestBody IdcardVerifyDTO idcardVerifyDTO, BladeUser bladeUser) throws Exception {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.idcardVerify(idcardVerifyDTO, makerEntity);
    }

    @PostMapping("/mobile-ocr")
    @ApiOperation(value = "手机号认证", notes = "手机号认证")
    public R mobileOcr(BladeUser bladeUser) throws Exception {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.mobileVerify(makerEntity);
    }

    @PostMapping("/bank-card-ocr")
    @ApiOperation(value = "银行卡认证", notes = "银行卡认证")
    public R bankCardOcr(@ApiParam(value = "银行卡号") @NotNull(message = "请输入银行卡号") @RequestParam(required = false) String bankCardNo, BladeUser bladeUser) throws Exception {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.bankCardVerify(bankCardNo, makerEntity);
    }

    @PostMapping("/face-ocr")
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

    @PostMapping("/face-ocr-notify")
    @ApiOperation(value = "活体认证异步回调", notes = "活体认证异步回调")
    public R faceOcrNotify(HttpServletRequest request) throws Exception {
        return makerService.faceOcrNotify(request);
    }

}
