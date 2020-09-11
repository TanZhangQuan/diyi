package com.lgyun.system.user.controller.enterprise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.vo.enterprise.EnterpriseContactRequest;
import com.lgyun.system.user.vo.enterprise.EnterpriseContactResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 商户联系人相关接口 控制器
 *
 * @author liangfeihu
 * @since 2020/8/19 11:37
 */
@Slf4j
@RestController
@RequestMapping("/enterprise/contact")
@AllArgsConstructor
@Api(value = "商户联系人相关接口", tags = "商户员工相关接")
public class EnterpriseContactController {

    private IEnterpriseWorkerService enterpriseWorkerService;

    @GetMapping("/list")
    @ApiOperation(value = "查询当前商户所有联系人详情", notes = "查询当前商户所有联系人详情")
    public R currentDetail(BladeUser bladeUser) {
        log.info("查询当前商户所有联系人详情");
        try {
            //查询当前创客
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(EnterpriseWorkerEntity::getEnterpriseId, enterpriseWorkerEntity.getEnterpriseId());
            List<EnterpriseWorkerEntity> list = enterpriseWorkerService.list(queryWrapper);

            List<EnterpriseContactResponse> responseList = new ArrayList<>();
            list.stream().forEach(entity -> {
                EnterpriseContactResponse response = new EnterpriseContactResponse();
                BeanUtils.copyProperties(entity, response);
                response.setWorkerSex(entity.getWorkerSex().getDesc());
                response.setCreateTime(entity.getCreateTime().getTime());
                response.setEnterpriseWorkerState(entity.getEnterpriseWorkerState().getValue());
                response.setPositionName(entity.getPositionName().getDesc());

                responseList.add(response);
            });
            return R.data(responseList);
        } catch (Exception e) {
            log.error("查询当前商户所有联系人详情，error", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/add")
    @ApiOperation(value = "新建商户联系人", notes = "新建商户联系人")
    public R addNewEnterpriseWorker(@RequestBody EnterpriseContactRequest request, BladeUser bladeUser) {
        log.info("新建商户联系人");
        try {
            //查询当前创客
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return enterpriseWorkerService.addNewEnterpriseWorker(request, enterpriseWorkerEntity, bladeUser);
        } catch (Exception e) {
            log.error("新建商户联系人 error=", e);
        }
        return R.fail("新建商户联系人失败");
    }

}
