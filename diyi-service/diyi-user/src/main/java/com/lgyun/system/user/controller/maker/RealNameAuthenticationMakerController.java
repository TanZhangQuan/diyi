package com.lgyun.system.user.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.IdcardOcrSaveDTO;
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

/**
 * 创客端---实名认证管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
@RequestMapping("/maker/real-name-authentication")
@Validated
@AllArgsConstructor
@Api(value = "创客端---实名认证管理模块相关接口", tags = "创客端---实名认证管理模块相关接口")
public class RealNameAuthenticationMakerController {

    private IMakerService makerService;

    @GetMapping("/get-real-name-authentication-states")
    @ApiOperation(value = "查询当前创客所有实名认证状态", notes = "查询当前创客所有实名认证状态")
    public R getRealNameAuthenticationStates(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.getRealNameAuthenticationState(makerEntity.getId());
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

    @PostMapping("/idcard-ocr-save")
    @ApiOperation(value = "身份证实名认证信息保存", notes = "身份证实名认证信息保存")
    public R idcardOcrSave(@Valid @RequestBody IdcardOcrSaveDTO idcardOcrSaveDTO, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.idcardOcrSave(idcardOcrSaveDTO, makerEntity);
    }

    @PostMapping("/query-idcard-info")
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

    @PostMapping("/mobile-ocr")
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

    @PostMapping("/mobile-ocr-notify")
    @ApiOperation(value = "手机号实名认证异步回调", notes = "手机号实名认证异步回调")
    public R mobileOcrNotify(HttpServletRequest request) throws Exception {
        return makerService.mobileOcrNotify(request);
    }

    @PostMapping("/bank-card-ocr")
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

    @PostMapping("/bank-card-ocr-notify")
    @ApiOperation(value = "银行卡实名认证异步回调", notes = "银行卡实名认证异步回调")
    public R bankCardOcrNotify(HttpServletRequest request) throws Exception {
        return makerService.bankCardOcrNotify(request);
    }

}
