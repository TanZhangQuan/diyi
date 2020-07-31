package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.EnterpriseIndividualBusinessEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessListDto;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IIndividualBusinessService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.wrapper.IndividualBusinessWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 控制器
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@Slf4j
@RestController
@RequestMapping("/individual-business")
@Validated
@AllArgsConstructor
@Api(value = "个体户相关接口", tags = "个体户相关接口")
public class IndividualBusinessController {

    private IIndividualBusinessService individualBusinessService;
    private IMakerService iMakerService;
    private IEnterpriseService enterpriseService;

    @PostMapping("/save")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody IndividualBusinessEnterpriseAddDto individualBusinessEnterpriseAddDto, BladeUser bladeUser) {

        log.info("新增个体户");
        try {
            //获取当前创客
            MakerEntity makerEntity = iMakerService.current(bladeUser);
            return individualBusinessService.save(individualBusinessEnterpriseAddDto, makerEntity);
        } catch (Exception e) {
            log.error("新增个体户异常", e);
        }
        return R.fail("新增个体户失败");

    }

//    @PostMapping("/update")
//    @ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody IndividualBusinessEntity individualBusiness) {
        return R.status(individualBusinessService.updateById(individualBusiness));
    }

//    @GetMapping("/detail")
//    @ApiOperation(value = "查询个体户详情", notes = "查询个体户详情")
    public R detail(@ApiParam(value = "个体户编号") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

        log.info("查询个体户详情");
        try {
            IndividualBusinessEntity individualBusiness = new IndividualBusinessEntity();
            individualBusiness.setId(individualBusinessId);
            IndividualBusinessEntity detail = individualBusinessService.getOne(Condition.getQueryWrapper(individualBusiness));
            return R.data(IndividualBusinessWrapper.build().entityVO(detail));
        } catch (Exception e) {
            log.error("查询个体户详情异常", e);
        }
        return R.fail("查询失败");
    }

//    @GetMapping("/list")
//    @ApiOperation(value = "查询所有个体户", notes = "查询所有个体户")
    public R list(IndividualBusinessListDto individualBusinessListDto, Query query) {

        log.info("查询所有个体户");
        try {
            IndividualBusinessEntity individualBusiness = new IndividualBusinessEntity();
            BeanUtil.copy(individualBusinessListDto, individualBusiness);
            IPage<IndividualBusinessEntity> pages = individualBusinessService.page(Condition.getPage(query), Condition.getQueryWrapper(individualBusiness));
            return R.data(IndividualBusinessWrapper.build().pageVO(pages));
        } catch (Exception e) {
            log.error("查询所有个体户异常", e);
        }
        return R.fail("查询失败");
    }

//    @PostMapping("/remove")
//    @ApiOperation(value = "删除个体户", notes = "删除个体户")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {

        log.info("删除个体户");
        try {
            return R.status(individualBusinessService.removeByIds(Func.toLongList(ids)));
        } catch (Exception e) {
            log.error("删除个体户异常", e);
        }
        return R.fail("删除失败");
    }

    @GetMapping("/list-by-maker")
    @ApiOperation(value = "查询当前创客的所有个体户", notes = "查询当前创客的所有个体户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ibstate", value = "工商个体户状态", paramType = "query", dataType = "string"),
    })
    public R listByMaker(Ibstate ibstate, Query query, BladeUser bladeUser) {

        log.info("查询当前创客的所有个体户");
        try {
            MakerEntity makerEntity = iMakerService.current(bladeUser);
            return individualBusinessService.listByMaker(query.getCurrent(), query.getSize(), makerEntity.getId(), ibstate);
        } catch (Exception e) {
            log.error("查询当前创客的所有个体户异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get_by_dto_enterprise")
    @ApiOperation(value = "查询当前商户的关联创客的所有个体户", notes = "查询当前商户的所有关联创客的所有个体户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个体户编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "ibname", value = "个体户名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R getByDtoEnterprise(@NotNull(message = "请选择个体户状态") @RequestParam(required = false) Ibstate ibstate, EnterpriseIndividualBusinessEnterpriseDto enterpriseIndividualBusinessEnterpriseDto, Query query, BladeUser bladeUser) {

        log.info("查询当前商户的关联创客的所有个体户");
        try {
            //获取当前商户
//            EnterpriseEntity enterpriseEntity = enterpriseService.current(bladeUser);
            return individualBusinessService.getByDtoEnterprise(Condition.getPage(query.setDescs("create_time")), 1L, ibstate, enterpriseIndividualBusinessEnterpriseDto);
        } catch (Exception e) {
            log.error("查询当前商户的关联创客的所有个体户异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/find_by_id_enterprise")
    @ApiOperation(value = "查询当前商户的关联创客的个体户详情", notes = "查询当前商户的关联创客的个体户详情")
    public R findByIdEnterprise(@ApiParam(value = "个体户编号") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

        log.info("查询当前商户的关联创客的个体户详情");
        try {
            return individualBusinessService.findByIdEnterprise(individualBusinessId);
        } catch (Exception e) {
            log.error("查询当前商户的关联创客的个体户详情异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/find-by-id")
    @ApiOperation(value = "查询个体户详情", notes = "查询个体户详情")
    public R findById(@ApiParam(value = "个体户编号") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

        log.info("查询个体户详情");
        try {
            return individualBusinessService.findById(individualBusinessId);
        } catch (Exception e) {
            log.error("查询个体户详情异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/year-month-money")
    @ApiOperation(value = "查询个体户月度开票金额和年度开票金额", notes = "查询个体户月度开票金额和年度开票金额")
    public R yearMonthMoney(@ApiParam(value = "个体户编号") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

        log.info("查询个体户月度开票金额和年度开票金额");
        try {
            return individualBusinessService.yearMonthMoney(individualBusinessId, MakerType.INDIVIDUALBUSINESS);
        } catch (Exception e) {
            log.error("查询个体户月度开票金额和年度开票金额异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/self_help_invoice_statistics")
    @ApiOperation(value = "查询个体户开票次数，月度开票金额，年度开票金额和总开票金额", notes = "查询个体户开票次数，月度开票金额，年度开票金额和总开票金额")
    public R selfHelpInvoiceStatistics(@ApiParam(value = "个体户ID") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

        log.info("查询个体户开票次数，月度开票金额，年度开票金额和总开票金额");
        try {
            return individualBusinessService.selfHelpInvoiceStatistics(individualBusinessId, MakerType.INDIVIDUALBUSINESS);
        } catch (Exception e) {
            log.error("查询个体户开票次数，月度开票金额，年度开票金额和总开票金额异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/self_help_invoice_list")
    @ApiOperation(value = "查询个体户开票记录", notes = "查询个体户开票记录")
    public R selfHelpInvoiceList(Query query, @ApiParam(value = "个体户ID") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

        log.info("查询个体户开票记录");
        try {
            return individualBusinessService.selfHelpInvoiceList(query.getCurrent(), query.getSize(), individualBusinessId, MakerType.INDIVIDUALBUSINESS);
        } catch (Exception e) {
            log.error("查询个体户开票记录异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/save_by_enterprise")
    @ApiOperation(value = "当前商户申请创建个体户", notes = "当前商户申请创建个体户")
    public R saveByEnterprise(@Valid @RequestBody IndividualBusinessEnterpriseAddEnterpriseDto individualBusinessEnterpriseAddEnterpriseDto, BladeUser bladeUser) {

        log.info("当前商户申请创建个体户");
        try {
            //获取当前商户
            EnterpriseEntity enterpriseEntity = enterpriseService.current(bladeUser);
            return individualBusinessService.saveByEnterprise(individualBusinessEnterpriseAddEnterpriseDto, enterpriseEntity.getId());
        } catch (Exception e) {
            log.error("当前商户申请创建个体户异常", e);
        }
        return R.fail("新增个体户失败");

    }

}
