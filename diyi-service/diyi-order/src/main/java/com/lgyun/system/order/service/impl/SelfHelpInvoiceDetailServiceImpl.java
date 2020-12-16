package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.enumeration.InvoicePrintState;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import com.lgyun.system.order.excel.InvoiceListExcel;
import com.lgyun.system.order.mapper.SelfHelpInvoiceDetailMapper;
import com.lgyun.system.order.service.ISelfHelpInvoiceApplyService;
import com.lgyun.system.order.service.ISelfHelpInvoiceDetailService;
import com.lgyun.system.order.service.ISelfHelpInvoicePersonService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Slf4j
@Service
@AllArgsConstructor
public class SelfHelpInvoiceDetailServiceImpl extends BaseServiceImpl<SelfHelpInvoiceDetailMapper, SelfHelpInvoiceDetailEntity> implements ISelfHelpInvoiceDetailService {

    private IUserClient userClient;
    private ISelfHelpInvoicePersonService selfHelpInvoicePersonService;
    @Autowired
    @Lazy
    private ISelfHelpInvoiceService selfHelpInvoiceService;
    private ISelfHelpInvoiceApplyService selfHelpInvoiceApplyService;

    @Override
    public R<AllIncomeYearMonthVO> queryCrowdNumAndAllIncome(MakerType makerType, Long makerId, Long year, Long month) {
        return R.data(baseMapper.queryCrowdNumAndAllIncome(makerType, makerId, year, month));
    }

    @Override
    public R<List<TradeVO>> queryCrowdMakerIncome(MakerType makerType, Long makerId, TimeType timeType, Date year, Date beginDate, Date endDate) {

        if (TimeType.PERIOD.equals(timeType) && (beginDate == null || endDate == null)){
                return R.fail("请选择开始时间和结束时间");
        }

        return R.data(baseMapper.queryCrowdMakerIncome(makerType, makerId, timeType.getValue(), year, beginDate, endDate));
    }

    @Override
    public R<IPage<AllIncomeYearMonthEnterpriseVO>> queryMakerToEnterpriseCrowdIncome(MakerType makerType, Long makerId, Long year, Long month, IPage<AllIncomeYearMonthEnterpriseVO> page) {
        return R.data(page.setRecords(baseMapper.queryMakerToEnterpriseCrowdIncome(makerType, makerId, year, month, page)));
    }

    @Override
    public R<IPage<IncomeDetailYearMonthVO>> queryCrowdIncomeDetail(MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId, IPage<IncomeDetailYearMonthVO> page) {
        return R.data(page.setRecords(baseMapper.queryCrowdIncomeDetail(makerType, makerId, year, month, enterpriseId, page)));
    }

    @Override
    public R<BigDecimal> queryCrowdDetailAllIncome(MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId) {
        return R.data(baseMapper.queryCrowdDetailAllIncome(makerType, makerId, year, month, enterpriseId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R uploadDeliverSheetUrl(Long selfHelpInvoiceDetailId, String deliverSheetUrl) {
        //TODO
        return R.success("上传成功");
    }

    @Override
    public void importSelfHelpInvoiceDetail(List<InvoiceListExcel> list) {

    }

    @Override
    public Boolean getSelfHelpInvoiceDetails(Long selfHelpInvoiceId, Long selfHelpInvoiceDetailId) {
        QueryWrapper<SelfHelpInvoiceDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SelfHelpInvoiceDetailEntity::getSelfHelpInvoiceId, selfHelpInvoiceId);
        List<SelfHelpInvoiceDetailEntity> selfHelpInvoiceDetailEntities = baseMapper.selectList(queryWrapper);
        int count = 0;
        int num = 0;
        for (SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity : selfHelpInvoiceDetailEntities) {
            if (!selfHelpInvoiceDetailEntity.getInvoicePrintState().equals(InvoicePrintState.INVOICESUCCESS)) {
                count++;
                if (selfHelpInvoiceDetailEntity.getId().equals(selfHelpInvoiceDetailId)) {
                    num++;
                }
            }
        }
        if ((count == 1 && num == 1) || count == 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<SelfHelpInvoiceDetailVO> getSelfHelpInvoiceId(Long selfHelpInvoiceId) {
        QueryWrapper<SelfHelpInvoiceDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SelfHelpInvoiceDetailEntity::getSelfHelpInvoiceId, selfHelpInvoiceId);
        List<SelfHelpInvoiceDetailEntity> selfHelpInvoiceDetailEntities = baseMapper.selectList(queryWrapper);
        List<SelfHelpInvoiceDetailVO> selfHelpInvoiceDetail = new ArrayList<>();
        for (SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity : selfHelpInvoiceDetailEntities) {
            SelfHelpInvoiceDetailVO copy = BeanUtil.copy(selfHelpInvoiceDetailEntity, SelfHelpInvoiceDetailVO.class);
            selfHelpInvoiceDetail.add(copy);
        }
        return selfHelpInvoiceDetail;
    }

    @Override
    public List<SelfHelpInvoiceDetailAdminVO> getSelfHelpInvoiceIdAll(Long selfHelpInvoiceId) {
        return baseMapper.getSelfHelpInvoiceIdAll(selfHelpInvoiceId);
    }

    @Override
    public R<IPage<AcceptPaysheetCsSelfHelpInvoiceDetailListVO>> queryCrowdAcceptPaysheetSelfHelpInvoiceDetailList(Long acceptPaysheetCsId, IPage<AcceptPaysheetCsSelfHelpInvoiceDetailListVO> page) {
        return R.data(page.setRecords(baseMapper.queryCrowdAcceptPaysheetSelfHelpInvoiceDetailList(acceptPaysheetCsId, page)));
    }

    @Override
    public R<IPage<SelfHelpInvoiceListMakerVO>> querySelfHelpInvoiceDetailListByMaker(Long makerId, IPage<SelfHelpInvoiceListMakerVO> page) {
        return R.data(page.setRecords(baseMapper.querySelfHelpInvoiceDetailListByMaker(makerId, page)));
    }

    @Override
    public List<SelfHelpInvoiceDetailEntity> querySelfHelpInvoiceId(Long selfHelpInvoiceId) {
        QueryWrapper<SelfHelpInvoiceDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SelfHelpInvoiceDetailEntity::getSelfHelpInvoiceId, selfHelpInvoiceId);
        return baseMapper.selectList(queryWrapper);
    }
}
