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
import com.lgyun.system.order.vo.SelfHelpInvoiceYearMonthMoneyVO;
import com.lgyun.system.user.dto.IndividualBusinessAddDto;
import com.lgyun.system.user.dto.IndividualBusinessListDto;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IIndividualBusinessService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.vo.IndividualBusinessDetailVO;
import com.lgyun.system.user.vo.IndividualBusinessListByMakerVO;
import com.lgyun.system.user.vo.IndividualBusinessVO;
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

    @PostMapping("/save")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@Valid @RequestBody IndividualBusinessAddDto individualBusinessAddDto, BladeUser bladeUser) {

        log.info("新增个体户");
        try {
            //获取当前创客
            MakerEntity makerEntity = iMakerService.current(bladeUser);
            return individualBusinessService.save(individualBusinessAddDto, makerEntity);
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
    public R<IndividualBusinessVO> detail(@ApiParam(value = "个体户编号") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

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
    public R<IPage<IndividualBusinessVO>> list(IndividualBusinessListDto individualBusinessListDto, Query query) {

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
    public R<IPage<IndividualBusinessListByMakerVO>> listByMaker(Ibstate ibstate, Query query, BladeUser bladeUser) {

        log.info("查询当前创客的所有个体户");
        try {
            MakerEntity makerEntity = iMakerService.current(bladeUser);
            return individualBusinessService.listByMaker(Condition.getPage(query.setDescs("create_time")), makerEntity.getId(), ibstate);
        } catch (Exception e) {
            log.error("查询当前创客的所有个体户异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/find-by-id")
    @ApiOperation(value = "查询个体户详情", notes = "查询个体户详情")
    public R<IndividualBusinessDetailVO> findById(@ApiParam(value = "个体户编号") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

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
    public R<SelfHelpInvoiceYearMonthMoneyVO> yearMonthMoney(@ApiParam(value = "个体户编号") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {

        log.info("查询个体户月度开票金额和年度开票金额");
        try {
            return individualBusinessService.yearMonthMoney(individualBusinessId, MakerType.INDIVIDUALBUSINESS);
        } catch (Exception e) {
            log.error("查询个体户月度开票金额和年度开票金额异常", e);
        }
        return R.fail("查询失败");
    }

}
