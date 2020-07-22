package com.lgyun.system.order.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.DeliverMaterialEntity;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.vo.DeliverMaterialVO;
import com.lgyun.system.order.wrapper.DeliverMaterialWrapper;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发票和完税证明接口
 * @author jun.
 * @date 2020/7/22.
 * @time 16:24.
 */
@Slf4j
@RestController
@RequestMapping("/order/invoice")
@Validated
@AllArgsConstructor
@Api(value = "发票和完税证明接口", tags = "发票和完税证明接口")
public class InvoiceController {

    private IPayEnterpriseService payEnterpriseService;
    private IUserClient iUserClient;


    /**
     * 根据创客id查询所有商户
     */
    @GetMapping("/getEnterpriseAll")
    @ApiOperation(value = "根据创客id查询所有商户", notes = "根据创客id查询所有商户")
    public R getEnterpriseAll(Query query,BladeUser bladeUser) {
        log.info("根据创客id查询所有商户");
        try{
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            return payEnterpriseService.getEnterpriseAll(makerEntity.getId(),Condition.getPage(query));
        }catch (Exception e){
            e.printStackTrace();
            return R.fail("根据创客id查询所有商户失败");
        }
    }

    /**
     * 根据创客id和商户id查询创客在商户下所开的票
     */
    @GetMapping("/getEnterpriseMakerIdAll")
    @ApiOperation(value = "根据创客id和商户id查询创客在商户下所开的票", notes = "根据创客id和商户id查询创客在商户下所开的票")
    public R getEnterpriseMakerIdAll(Query query,BladeUser bladeUser,Long enterpriseId) {
        log.info("根据创客id和商户id查询创客在商户下所开的票");
        try{
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            return payEnterpriseService.getEnterpriseMakerIdAll(makerEntity.getId(),enterpriseId,Condition.getPage(query));
        }catch (Exception e){
            e.printStackTrace();
            return R.fail("根据创客id和商户id查询创客在商户下所开的票失败");
        }
    }

    /**
     * 根据创客id,商户id和创客支付id查询票的详情
     */
    @GetMapping("/getEnterpriseMakerIdDetail")
    @ApiOperation(value = "根据创客id,商户id和创客支付id查询票的详情", notes = "根据创客id,商户id和创客支付id查询票的详情")
    public R getEnterpriseMakerIdDetail(Query query,BladeUser bladeUser,Long enterpriseId,Long payMakerId) {
        log.info("根据创客id,商户id和创客支付id查询票的详情");
        try{
            MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
            return payEnterpriseService.getEnterpriseMakerIdDetail(makerEntity.getId(),enterpriseId,payMakerId ,Condition.getPage(query));
        }catch (Exception e){
            e.printStackTrace();
            return R.fail("根据创客id,商户id和创客支付id查询票的详情失败");
        }
    }
}
