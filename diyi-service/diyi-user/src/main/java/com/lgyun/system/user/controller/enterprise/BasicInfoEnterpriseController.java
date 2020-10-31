package com.lgyun.system.user.controller.enterprise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.vo.EnterpriseContactVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/enterprise/basic-info")
@Validated
@AllArgsConstructor
@Api(value = "商户端---商户基本信息管理模块相关接口", tags = "商户端---商户基本信息管理模块相关接口")
public class BasicInfoEnterpriseController {

    private IEnterpriseWorkerService enterpriseWorkerService;
    private IEnterpriseService enterpriseService;

    @GetMapping("/query-enterprise-info")
    @ApiOperation(value = "查询商户基本信息", notes = "查询商户基本信息")
    public R basicInfo(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return enterpriseService.getBasicEnterpriseResponse(enterpriseWorkerEntity.getEnterpriseId());
    }

    @PostMapping("/upload-licence")
    @ApiOperation(value = "上传营业执照", notes = "上传营业执照")
    public R licenceImageUpload(@RequestParam("file") MultipartFile file, BladeUser bladeUser) throws Exception {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        enterpriseService.uploadEnterpriseLicence(enterpriseWorkerEntity.getEnterpriseId(), file);

        return R.success("上传成功");
    }

    @GetMapping("/query-contact")
    @ApiOperation(value = "查询当前商户所有联系人详情", notes = "查询当前商户所有联系人详情")
    public R currentDetail(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getEnterpriseId, enterpriseWorkerEntity.getEnterpriseId());
        List<EnterpriseWorkerEntity> list = enterpriseWorkerService.list(queryWrapper);

        List<EnterpriseContactVO> responseList = new ArrayList<>();
        list.forEach(entity -> {
            EnterpriseContactVO response = new EnterpriseContactVO();
            BeanUtils.copyProperties(entity, response);
            response.setWorkerSex(entity.getWorkerSex());
            response.setCreateTime(entity.getCreateTime().getTime());
            response.setEnterpriseWorkerState(entity.getEnterpriseWorkerState().getValue());
            response.setPositionName(entity.getPositionName().getDesc());

            responseList.add(response);
        });

        return R.data(responseList);
    }

}
