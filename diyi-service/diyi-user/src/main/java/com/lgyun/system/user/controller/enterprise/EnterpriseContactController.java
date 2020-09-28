package com.lgyun.system.user.controller.enterprise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.enterprise.AddOrUpdateEnterpriseContactDTO;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.vo.enterprise.EnterpriseContactResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 商户联系人相关接口 控制器
 *
 * @author tzq
 * @since 2020/8/19 11:37
 */
@RestController
@RequestMapping("/enterprise/contact")
@AllArgsConstructor
@Api(value = "商户联系人相关接口", tags = "商户员工相关接")
public class EnterpriseContactController {

    private IEnterpriseWorkerService enterpriseWorkerService;

    @GetMapping("/list")
    @ApiOperation(value = "查询当前商户所有联系人详情", notes = "查询当前商户所有联系人详情")
    public R currentDetail(BladeUser bladeUser) {
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
        list.forEach(entity -> {
            EnterpriseContactResponse response = new EnterpriseContactResponse();
            BeanUtils.copyProperties(entity, response);
            response.setWorkerSex(entity.getWorkerSex().getDesc());
            response.setCreateTime(entity.getCreateTime().getTime());
            response.setEnterpriseWorkerState(entity.getEnterpriseWorkerState().getValue());
            response.setPositionName(entity.getPositionName().getDesc());

            responseList.add(response);
        });

        return R.data(responseList);
    }

    @PostMapping("/add-or-update-enterprise-contact")
    @ApiOperation(value = "添加或修改商户联系人", notes = "添加或修改商户联系人")
    public R addOrUpdateEnterpriseContact(@Valid @RequestBody AddOrUpdateEnterpriseContactDTO addOrUpdateEnterpriseContactDto, BladeUser bladeUser) {
        //查询当前创客
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return enterpriseWorkerService.addOrUpdateEnterpriseContact(addOrUpdateEnterpriseContactDto, enterpriseWorkerEntity.getId());
    }

}
