package com.lgyun.system.order.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.order.service.IPayMakerService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * 创客端---首页管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
@RequestMapping("/maker/home-page")
@Validated
@AllArgsConstructor
@Api(value = "创客端---首页管理模块相关接口", tags = "创客端---首页管理模块相关接口")
public class HomePageMakerController {

    private IUserClient userClient;
    private IPayMakerService payMakerService;
    private IWorksheetService worksheetService;

    @GetMapping("/query-enterprise-num-and-income")
    @ApiOperation(value = "查询当前创客关联商户数和收入情况", notes = "查询当前创客关联商户数和收入情况")
    public R queryEnterpriseNumAndIncome(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return payMakerService.getEnterpriseNumIncome(makerEntity.getId());
    }
    
    @PostMapping("/grab-worksheet")
    @ApiOperation(value = "抢单", notes = "抢单")
    public R grabWorksheet(@ApiParam(value = "工单ID", required = true) @NotNull(message = "请选择工单") @RequestParam(required = false) Long worksheetId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = userClient.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return worksheetService.orderGrabbing(worksheetId, makerEntity.getId());
    }

}
