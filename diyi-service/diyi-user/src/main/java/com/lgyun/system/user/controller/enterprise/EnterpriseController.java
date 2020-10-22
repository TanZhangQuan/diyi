package com.lgyun.system.user.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/enterprise")
@Validated
@AllArgsConstructor
@Api(value = "商户基本信息相关接口", tags = "商户基本信息相关接口")
public class EnterpriseController {

    private IEnterpriseService enterpriseService;
    private IEnterpriseWorkerService enterpriseWorkerService;

    @GetMapping("/basicInfo")
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

    @PostMapping("/upload/licence")
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

}
