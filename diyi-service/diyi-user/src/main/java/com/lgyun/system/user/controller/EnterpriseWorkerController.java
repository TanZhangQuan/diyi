package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.wrapper.EnterpriseWorkerWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 控制器
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
@Slf4j
@RestController
@RequestMapping("/enterprise_worker")
@Validated
@AllArgsConstructor
@Api(value = "商户员工相关接口", tags = "商户员工相关接口")
public class EnterpriseWorkerController {

    private IEnterpriseWorkerService enterpriseWorkerService;

//    @PostMapping("/save")
//    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody EnterpriseWorkerEntity enterpriseWorker) {
        return R.status(enterpriseWorkerService.save(enterpriseWorker));
    }

//    @PostMapping("/update")
//    @ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody EnterpriseWorkerEntity enterpriseWorker) {
        return R.status(enterpriseWorkerService.updateById(enterpriseWorker));
    }

//    @GetMapping("/detail")
//    @ApiOperation(value = "商户员工详情", notes = "商户员工详情")
    public R detail(EnterpriseWorkerEntity enterpriseWorker) {
        EnterpriseWorkerEntity detail = enterpriseWorkerService.getOne(Condition.getQueryWrapper(enterpriseWorker));
        return R.data(EnterpriseWorkerWrapper.build().entityVO(detail));
    }

//    @GetMapping("/list")
//    @ApiOperation(value = "分页", notes = "分页")
    public R list(EnterpriseWorkerEntity enterpriseWorker, Query query) {
        IPage<EnterpriseWorkerEntity> pages = enterpriseWorkerService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(enterpriseWorker));
        return R.data(EnterpriseWorkerWrapper.build().pageVO(pages));
    }

//    @PostMapping("/remove")
//    @ApiOperation(value = "删除", notes = "删除")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(enterpriseWorkerService.removeByIds(Func.toLongList(ids)));
    }

    @GetMapping("/current-detail")
    @ApiOperation(value = "获取当前商户员工详情", notes = "获取当前商户员工详情")
    public R currentDetail(BladeUser bladeUser) {

        log.info("获取当前商户员工详情");
        try {
            //获取当前创客
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return R.data(EnterpriseWorkerWrapper.build().entityVO(enterpriseWorkerEntity));
        } catch (Exception e) {
            log.error("获取当前商户员工详情异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/update-password")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public R updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) {

        log.info("修改密码");
        try {
            return enterpriseWorkerService.updatePassword(updatePasswordDto);
        } catch (Exception e) {
            log.error("修改密码异常", e);
        }
        return R.fail("修改失败");
    }

}
