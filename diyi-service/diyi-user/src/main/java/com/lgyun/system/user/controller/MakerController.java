package com.lgyun.system.user.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IdcardOcrSaveDto;
import com.lgyun.system.user.dto.MakerAddDto;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.excel.MakerExcel;
import com.lgyun.system.user.excel.MakerImportListener;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.vo.MakerVO;
import com.lgyun.system.user.wrapper.MakerWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

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
@Api(value = "创客（分包方）的基本信息相关接口", tags = "创客（分包方）的基本信息相关接口")
public class MakerController {

    private IMakerService makerService;
    private IEnterpriseWorkerService enterpriseWorkerService;

    @PostMapping("/save")
    @ApiOperation(value = "新增单个创客", notes = "新增单个创客")
    public R makerAdd(@Valid @RequestBody MakerAddDto makerAddDto, BladeUser bladeUser) {

        log.info("新增单个创客");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return makerService.makerAdd(makerAddDto, enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("新增单个创客异常", e);
        }

        return R.fail("新增失败");
    }

    //	@PostMapping("/update")
//	@ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody MakerEntity maker) {
        return R.status(makerService.updateById(maker));
    }

    //	@GetMapping("/detail")
//	@ApiOperation(value = "详情", notes = "详情")
    public R<MakerVO> detail(@ApiParam(value = "创客编号") @NotNull(message = "请输入创客编号") @RequestParam(required = false) Long makerId) {
        MakerEntity maker = new MakerEntity();
        maker.setId(makerId);
        MakerEntity detail = makerService.getOne(Condition.getQueryWrapper(maker));
        return R.data(MakerWrapper.build().entityVO(detail));
    }

    //	@GetMapping("/list")
//	@ApiOperation(value = "分页", notes = "分页")
    public R list(MakerEntity maker, Query query) {
        IPage<MakerEntity> pages = makerService.page(Condition.getPage(query), Condition.getQueryWrapper(maker));
        return R.data(MakerWrapper.build().pageVO(pages));
    }

    //	@PostMapping("/remove")
//	@ApiOperation(value = "删除", notes = "删除")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(makerService.removeByIds(Func.toLongList(ids)));
    }

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

    @PostMapping("import-maker")
    @ApiOperation(value = "导入创客", notes = "导入创客")
    public R importUser(@ApiParam(value = "文件") @NotNull(message = "请选择Excel文件") @RequestParam(required = false) MultipartFile file, BladeUser bladeUser) {

        log.info("导入创客");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            //判断文件内容是否为空
            if (file.isEmpty()) {
                return R.fail("Excel文件不能为空");
            }

            // 获取上传文件的后缀
            String suffix = file.getOriginalFilename();
            if ((!StringUtils.endsWithIgnoreCase(suffix, ".xls") && !StringUtils.endsWithIgnoreCase(suffix, ".xlsx"))) {
                return R.fail("请选择Excel文件");
            }

            MakerImportListener makerImportListener = new MakerImportListener(makerService, enterpriseWorkerEntity.getEnterpriseId());
            InputStream inputStream = new BufferedInputStream(file.getInputStream());
            ExcelReaderBuilder builder = EasyExcel.read(inputStream, MakerExcel.class, makerImportListener);
            builder.doReadAll();
        } catch (IOException e) {
            log.error("导入创客异常", e);
        }
        return R.success("操作成功");
    }

    @GetMapping("/get_maker_detail_by_id")
    @ApiOperation(value = "根据创客ID获取创客详情", notes = "根据创客ID获取创客详情")
    public R getMakerDetailById(@ApiParam(value = "创客ID") @NotNull(message = "请输入创客编号") @RequestParam(required = false) Long makerId, BladeUser bladeUser) {

        log.info("根据创客ID获取创客详情");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return makerService.getMakerDetailById(enterpriseWorkerEntity.getEnterpriseId(), makerId);
        } catch (Exception e) {
            log.error("根据创客ID获取创客详情异常", e);
        }
        return R.fail("查询失败");
    }

}
