package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.IdcardOcrSaveDto;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.wrapper.MakerWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 控制器
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Slf4j
@RestController
@RequestMapping("/maker")
@Validated
@AllArgsConstructor
@Api(value = "创客的基本信息相关接口", tags = "创客的基本信息相关接口")
public class MakerController {

    private IMakerService makerService;
    private IEnterpriseWorkerService enterpriseWorkerService;

    @PostMapping("/idcard_ocr")
    @ApiOperation(value = "身份证实名认证", notes = "身份证实名认证")
    public R idcardOcr(@ApiParam(value = "正面照片") @NotNull(message = "请选择正面照片") @RequestParam(required = false) String idcardPic, BladeUser bladeUser) {

        log.info("身份证实名认证");
        try {
            //获取当前创客
            R<MakerEntity> result = makerService.currentMaker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return makerService.idcardOcr(idcardPic, makerEntity);
        } catch (Exception e) {
            log.error("身份证实名认证异常", e);
        }
        return R.fail("身份证实名认证失败");
    }

    @PostMapping("/idcard_ocr_save")
    @ApiOperation(value = "身份证实名认证信息保存", notes = "身份证实名认证信息保存")
    public R idcardOcrSave(@Valid @RequestBody IdcardOcrSaveDto idcardOcrSaveDto, BladeUser bladeUser) {

        log.info("身份证实名认证信息保存");
        try {
            //获取当前创客
            R<MakerEntity> result = makerService.currentMaker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return makerService.idcardOcrSave(idcardOcrSaveDto, makerEntity);
        } catch (Exception e) {
            log.error("身份证实名认证信息保存异常", e);
        }
        return R.fail("身份证实名认证信息保存失败");
    }

    @PostMapping("/face_ocr")
    @ApiOperation(value = "身份实名认证", notes = "身份实名认证")
    public R faceOcr(BladeUser bladeUser) {

        log.info("身份实名认证");
        try {
            //获取当前创客
            R<MakerEntity> result = makerService.currentMaker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return makerService.faceOcr(makerEntity);
        } catch (Exception e) {
            log.error("身份实名认证异常", e);
        }
        return R.fail("身份实名认证失败");
    }

    @PostMapping("/face_ocr_notify")
    @ApiOperation(value = "身份实名认证异步回调", notes = "身份实名认证异步回调")
    public R faceOcrNotify(HttpServletRequest request) {

        log.info("身份实名认证异步回调");
        try {
            return makerService.faceOcrNotify(request);
        } catch (Exception e) {
            log.error("身份实名认证异步回调异常", e);
        }
        return R.fail("身份实名认证异步回调失败");
    }

    @PostMapping("/bank_card_ocr")
    @ApiOperation(value = "银行卡实名认证", notes = "银行卡实名认证")
    public R bankCardOcr(@ApiParam(value = "银行卡号") @NotNull(message = "请输入银行卡号") @RequestParam(required = false) String bankCardNo, BladeUser bladeUser) {

        log.info("银行卡实名认证");
        try {
            //获取当前创客
            R<MakerEntity> result = makerService.currentMaker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return makerService.bankCardOcr(bankCardNo, makerEntity);
        } catch (Exception e) {
            log.error("银行卡实名认证异常", e);
        }
        return R.fail("银行卡实名认证失败");
    }

    @PostMapping("/bank_card_ocr_notify")
    @ApiOperation(value = "银行卡实名认证异步回调", notes = "银行卡实名认证异步回调")
    public R bankCardOcrNotify(HttpServletRequest request) {

        log.info("银行卡实名认证异步回调");
        try {
            return makerService.bankCardOcrNotify(request);
        } catch (Exception e) {
            log.error("银行卡实名认证异步回调异常", e);
        }
        return R.fail("银行卡实名认证异步回调失败");
    }

    @PostMapping("/mobile_ocr")
    @ApiOperation(value = "手机号实名认证", notes = "手机号实名认证")
    public R mobileOcr(BladeUser bladeUser) {

        log.info("手机号实名认证");
        try {
            //获取当前创客
            R<MakerEntity> result = makerService.currentMaker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return makerService.mobileOcr(makerEntity);
        } catch (Exception e) {
            log.error("手机号实名认证异常", e);
        }
        return R.fail("手机号实名认证失败");
    }

    @PostMapping("/mobile_ocr_notify")
    @ApiOperation(value = "手机号实名认证异步回调", notes = "手机号实名认证异步回调")
    public R mobileOcrNotify(HttpServletRequest request) {

        log.info("手机号实名认证异步回调");
        try {
            return makerService.mobileOcrNotify(request);
        } catch (Exception e) {
            log.error("手机号实名认证异步回调异常", e);
        }
        return R.fail("手机号实名认证异步回调失败");
    }

    @PostMapping("/query_idcard_ocr")
    @ApiOperation(value = "查询当前创客身份证实名认证的照片", notes = "查询当前创客身份证实名认证的照片")
    public R queryIdcardOcr(BladeUser bladeUser) {

        log.info("查询当前创客身份证实名认证的照片");
        try {
            //获取当前创客
            R<MakerEntity> result = makerService.currentMaker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return makerService.queryIdcardOcr(makerEntity);
        } catch (Exception e) {
            log.error("查询当前创客身份证实名认证的照片异常", e);
        }
        return R.fail("查询当前创客身份证实名认证的照片失败");
    }

    @GetMapping("/get-real-name-authentication-state")
    @ApiOperation(value = "获取当前创客所有实名认证状态", notes = "获取当前创客所有实名认证状态")
    public R getRealNameAuthenticationState(BladeUser bladeUser) {

        log.info("获取当前创客所有实名认证状态");
        try {
            //获取当前创客
            R<MakerEntity> result = makerService.currentMaker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return makerService.getRealNameAuthenticationState(makerEntity.getId());
        } catch (Exception e) {
            log.error("获取当前创客所有实名认证状态异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get-info")
    @ApiOperation(value = "获取当前创客基本信息", notes = "获取当前创客基本信息")
    public R getInfo(BladeUser bladeUser) {

        log.info("获取当前创客基本信息");
        try {
            //获取当前创客
            R<MakerEntity> result = makerService.currentMaker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return makerService.getInfo(makerEntity.getId());
        } catch (Exception e) {
            log.error("获取当前创客基本信息异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get-enterprise-num-income")
    @ApiOperation(value = "查询当前创客关联商户数和收入情况", notes = "查询当前创客关联商户数和收入情况")
    public R getEnterpriseNumIncome(BladeUser bladeUser) {

        log.info("查询当前创客关联商户数和收入情况");
        try {
            //获取当前创客
            R<MakerEntity> result = makerService.currentMaker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return makerService.getEnterpriseNumIncome(makerEntity.getId());
        } catch (Exception e) {
            log.error("查询当前创客关联商户数和收入情况异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/current-detail")
    @ApiOperation(value = "获取当前创客详情", notes = "获取当前创客详情")
    public R currentDetail(BladeUser bladeUser) {

        log.info("获取当前创客详情");
        try {
            //获取当前创客
            R<MakerEntity> result = makerService.currentMaker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return R.data(MakerWrapper.build().entityVO(makerEntity));
        } catch (Exception e) {
            log.error("获取当前创客详情异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/update-password")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public R updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) {

        log.info("修改密码");
        try {
            return makerService.updatePassword(updatePasswordDto);
        } catch (Exception e) {
            log.error("修改密码异常", e);
        }
        return R.fail("修改失败");
    }

}
