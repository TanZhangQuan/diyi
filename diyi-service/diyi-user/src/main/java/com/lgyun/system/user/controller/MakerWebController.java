package com.lgyun.system.user.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.MakerAddDto;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.excel.MakerExcel;
import com.lgyun.system.user.excel.MakerImportListener;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * 控制器
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Slf4j
@RestController
@RequestMapping("/web/maker")
@Validated
@AllArgsConstructor
@Api(value = "创客相关接口(管理端)", tags = "创客相关接口(管理端)")
public class MakerWebController {

    private IMakerService makerService;
    private IEnterpriseWorkerService enterpriseWorkerService;
    private IMakerEnterpriseService makerEnterpriseService;

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

    @GetMapping("/get_relevance_enterprise_maker")
    @ApiOperation(value = "获取关联当前商户的所有创客", notes = "获取关联当前商户的所有创客")
    public R getRelevanceEnterpriseMaker(String keyword, Query query, BladeUser bladeUser) {

        log.info("获取关联当前商户的所有创客");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return makerEnterpriseService.getRelEnterpriseMaker(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId(), 0, keyword);
        } catch (Exception e) {
            log.error("获取关联当前商户的所有创客异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get_attention_enterprise_maker")
    @ApiOperation(value = "获取关注当前商户的所有创客", notes = "获取关注当前商户的所有创客")
    public R getAttentionEnterpriseMaker(String keyword, Query query, BladeUser bladeUser) {

        log.info("获取关注当前商户的所有创客");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return makerEnterpriseService.getRelEnterpriseMaker(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId(), 1, keyword);
        } catch (Exception e) {
            log.error("获取关注当前商户的所有创客异常", e);
        }
        return R.fail("查询失败");
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

    @PostMapping("/cancel_makers_attention")
    @ApiOperation(value = "批量取消创客关注", notes = "批量取消创客关注")
    public R cancelMakersAttention(@ApiParam(value = "创客ID") @NotEmpty(message = "请选择要取消关注的创客") @RequestParam(required = false) Set<Long> makerIds, BladeUser bladeUser) {

        log.info("批量取消创客关注");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return makerEnterpriseService.cancelMakersRel(makerIds, enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("批量取消创客关注异常", e);
        }
        return R.fail("取消关注失败");
    }

    @PostMapping("/cancel_relevance_makers")
    @ApiOperation(value = "批量取消关联创客", notes = "批量取消关联创客")
    public R cancelRelevanceMakers(@ApiParam(value = "创客ID") @NotEmpty(message = "请选择要取消关联的创客") @RequestParam(required = false) Set<Long> makerIds, BladeUser bladeUser) {

        log.info("批量取消关联创客");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return makerEnterpriseService.cancelRelMakers(makerIds, enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("批量取消关联创客异常", e);
        }
        return R.fail("取消关联失败");
    }

    @PostMapping("/relevance_makers")
    @ApiOperation(value = "批量关联创客", notes = "批量关联创客")
    public R relevanceMakers(@ApiParam(value = "创客ID") @NotEmpty(message = "请选择要关联的创客") @RequestParam(required = false) Set<Long> makerIds, BladeUser bladeUser) {

        log.info("批量关联创客");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return makerEnterpriseService.relMakers(makerIds, enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("批量关联创客异常", e);
        }
        return R.fail("关联失败");
    }

}