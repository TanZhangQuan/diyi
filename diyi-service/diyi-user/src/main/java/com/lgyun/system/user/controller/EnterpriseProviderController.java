package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.EnterpriseProviderEntity;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseProviderService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.wrapper.EnterpriseProviderWrapper;
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
 * @author jun
 * @since 2020-07-28 14:53:11
 */
@Slf4j
@RestController
@RequestMapping("/enterpriseprovider")
@Validated
@AllArgsConstructor
@Api(value = "商户-服务商相关接口", tags = "商户-服务商相关接口")
public class EnterpriseProviderController {

    private IEnterpriseProviderService enterpriseProviderService;
	private IEnterpriseWorkerService enterpriseWorkerService;

    @PostMapping("/save")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody EnterpriseProviderEntity enterpriseProvider) {
        return R.status(enterpriseProviderService.save(enterpriseProvider));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody EnterpriseProviderEntity enterpriseProvider) {
        return R.status(enterpriseProviderService.updateById(enterpriseProvider));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public R detail(EnterpriseProviderEntity enterpriseProvider) {
        EnterpriseProviderEntity detail = enterpriseProviderService.getOne(Condition.getQueryWrapper(enterpriseProvider));
        return R.data(EnterpriseProviderWrapper.build().entityVO(detail));
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页", notes = "分页")
    public R list(EnterpriseProviderEntity enterpriseProvider, Query query) {
        IPage<EnterpriseProviderEntity> pages = enterpriseProviderService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(enterpriseProvider));
        return R.data(EnterpriseProviderWrapper.build().pageVO(pages));
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "删除")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(enterpriseProviderService.removeByIds(Func.toLongList(ids)));
    }

    @GetMapping("/list_by_enterprise_id")
    @ApiOperation(value = "根据商户ID查询所有合作的服务商", notes = "根据商户ID查询所有合作的服务商")
    public R listByEnterpriseId(Query query, BladeUser bladeUser) {

        log.info("根据商户ID查询所有合作的服务商");
        try{
			//获取当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

			return enterpriseProviderService.listByEnterpriseId(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
			log.error("根据商户ID查询所有合作的服务商异常", e);
        }
       return R.fail("查询失败");
    }

}
