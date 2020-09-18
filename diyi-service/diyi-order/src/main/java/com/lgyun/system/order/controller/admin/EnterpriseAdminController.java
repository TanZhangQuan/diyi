package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AddressDto;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 平台端---商户管理controller
 *
 * @author tzq
 * @date 2020-09-9
 */
@Slf4j
@RestController
@RequestMapping("/admin/enterprise")
@Validated
@AllArgsConstructor
@Api(value = "平台端---商户管理模块相关接口", tags = "平台端---商户管理模块相关接口")
public class EnterpriseAdminController {

    private IAddressService addressService;
    private IPayEnterpriseService payEnterpriseService;

    @GetMapping("/query-address-list")
    @ApiOperation(value = "查询商户所有收货地址信息", notes = "查询商户所有收货地址信息")
    public R queryAddressList(@ApiParam(value = "商户ID") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId, Query query) {

        log.info("查询商户所有收货地址信息");
        try {
            return addressService.queryAddressList(ObjectType.ENTERPRISEPEOPLE, enterpriseId, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询商户所有收货地址信息异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/add-or-update-address")
    @ApiOperation(value = "添加/编辑收货地址", notes = "添加/编辑收货地址")
    public R addOrUpdateAddress(@ApiParam(value = "商户编号") @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, @Valid @RequestBody AddressDto addressDto) {

        log.info("添加/编辑收货地址");
        try {
            return addressService.addOrUpdateAddress(addressDto, enterpriseId, ObjectType.ENTERPRISEPEOPLE);
        } catch (Exception e) {
            log.error("添加/编辑收货地址异常", e);
        }

        return R.fail("操作失败");
    }

    @PostMapping("/remove-address")
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址")
    public R removeAddress(@ApiParam(value = "收货地址ID集合", required = true) @NotBlank(message = "请选择要删除的收货地址") @RequestParam(required = false) String ids) {

        log.info("删除收货地址");
        try {
            return R.status(addressService.removeByIds(Func.toLongList(ids)));
        } catch (Exception e) {
            log.error("删除收货地址异常", e);
        }

        return R.fail("删除失败");
    }

    @GetMapping("/enterprise-transaction")
    @ApiOperation(value = "查询商户交易数据", notes = "查询商户交易数据")
    public R transactionByEnterprise(@ApiParam(value = "商户ID") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId) {

        log.info("查询商户交易数据");
        try {
            return payEnterpriseService.transactionByEnterprise(enterpriseId);
        } catch (Exception e) {
            log.error("查询某商户交易数据异常", e);
        }
        return R.fail("查询失败");
    }

}
