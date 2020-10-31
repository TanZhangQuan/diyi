package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.MakerInvoiceEntity;
import com.lgyun.system.order.entity.MakerTaxRecordEntity;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.lgyun.system.order.excel.PayEnterpriseExcel;
import com.lgyun.system.order.mapper.PayMakerMapper;
import com.lgyun.system.order.service.IMakerInvoiceService;
import com.lgyun.system.order.service.IMakerTaxRecordService;
import com.lgyun.system.order.service.IPayMakerService;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import com.lgyun.system.user.vo.MakerEnterpriseNumIncomeVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service 实现
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayMakerServiceImpl extends BaseServiceImpl<PayMakerMapper, PayMakerEntity> implements IPayMakerService {

    private IUserClient iUserClient;
    private IMakerInvoiceService makerInvoiceService;
    private IMakerTaxRecordService makerTaxRecordService;

    @Override
    public R<MakerEnterpriseNumIncomeVO> getEnterpriseNumIncome(Long makerId) {
        return R.data(baseMapper.getEnterpriseNumIncome(makerId));
    }

    @Override
    public R<AllIncomeYearMonthVO> queryTotalSubNumAndAllIncome(MakerType makerType, Long makerId, Long year, Long month) {
        return R.data(baseMapper.queryTotalSubNumAndAllIncome(makerType, makerId, year, month));
    }

    @Override
    public R<IncomeYearVO> queryEveryYearTotalSubIncome(MakerType makerType, Long makerId) {
        return R.data(baseMapper.queryEveryYearTotalSubIncome(makerType, makerId));
    }

    @Override
    public R<YearTradeVO> queryEveryMonthTotalSubIncome(MakerType makerType, Long makerId, Long year) {
        return R.data(baseMapper.queryEveryMonthTotalSubIncome(makerType, makerId, year));
    }

    @Override
    public R<IPage<AllIncomeYearMonthEnterpriseVO>> queryMakerToEnterpriseTotalSubIncome(MakerType makerType, Long makerId, Long year, Long month, IPage<AllIncomeYearMonthEnterpriseVO> page) {
        return R.data(page.setRecords(baseMapper.queryMakerToEnterpriseTotalSubIncome(makerType, makerId, year, month, page)));
    }

    @Override
    public R<IPage<IncomeDetailYearMonthVO>> queryTotalSubIncomeDetail(MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId, IPage<IncomeDetailYearMonthVO> page) {
        return R.data(page.setRecords(baseMapper.queryTotalSubIncomeDetail(makerType, makerId, year, month, enterpriseId, page)));
    }

    @Override
    public R<BigDecimal> queryTotalSubDetailAllIncome(MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId) {
        return R.data(baseMapper.queryTotalSubDetailAllIncome(makerType, makerId, year, month, enterpriseId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importPayMakerList(List<PayEnterpriseExcel> list, Long payEnterpriseId, MakerType makerType) {

        for (PayEnterpriseExcel payEnterpriseExcel : list) {
            log.info(String.valueOf(payEnterpriseExcel));

            if (StringUtils.isBlank(payEnterpriseExcel.getMakerName())) {
                log.error("缺少创客姓名数据");
                continue;
            }

            if (StringUtils.isBlank(payEnterpriseExcel.getMakerIdcardNo())) {
                log.error("缺少创客身份证号数据");
                continue;
            }

            MakerEntity makerEntity = iUserClient.queryMakerByIdcardNo(payEnterpriseExcel.getMakerIdcardNo());
            if (makerEntity == null) {
                log.error("创客不存在");
                continue;
            }

            if (StringUtils.isBlank(makerEntity.getName())) {
                log.error("创客姓名为空");
                continue;
            }

            if (!(makerEntity.getName().equals(payEnterpriseExcel.getMakerName()))) {
                log.error("创客姓名和Excel创客姓名不一致");
                continue;
            }

            int payMakerNum = baseMapper.selectCount(Wrappers.<PayMakerEntity>query().lambda().eq(PayMakerEntity::getPayEnterpriseId, payEnterpriseId).eq(PayMakerEntity::getMakerId, makerEntity.getId()));
            if (payMakerNum > 0) {
                log.error("分包已存在");
                continue;
            }

            if (payEnterpriseExcel.getMakerNetIncome() == null) {
                log.error("缺少创客到手数据");
                continue;
            }

            if (payEnterpriseExcel.getServiceRate() == null) {
                log.error("缺少服务税费率数据");
                continue;
            }

            if (payEnterpriseExcel.getMakerTaxFee() == null) {
                log.error("缺少服务税费数据");
                continue;
            }

            if (payEnterpriseExcel.getMakerNeIncome() == null) {
                log.error("缺少服务外包费数据");
                continue;
            }

            if (payEnterpriseExcel.getPayFee() == null) {
                log.error("缺少第三方支付手续费数据");
                continue;
            }

            if (payEnterpriseExcel.getTotalFee() == null) {
                log.error("缺少价税合计企业总支付额数据");
                continue;
            }

            //个体户或个独ID
            Long individualId = null;
            BigDecimal enterpriseBusinessAnnualFee = BigDecimal.ZERO;
            switch (makerType) {

                case NATURALPERSON:
                    if (payEnterpriseExcel.getAuditFee() == null) {
                        log.error("缺少创客首次身份验证费数据");
                        continue;
                    }

                    break;

                case INDIVIDUALBUSINESS:
                    if (StringUtils.isBlank(payEnterpriseExcel.getIndividualBusinessName())) {
                        log.error("缺少个体户名称数据");
                        continue;
                    }

                    if (StringUtils.isBlank(payEnterpriseExcel.getIndividualBusinessIbtaxNo())) {
                        log.error("缺少个体户统一社会信用代码数据");
                        continue;
                    }

                    if (payEnterpriseExcel.getIndividualBusinessAnnualFee() == null) {
                        log.error("缺少个体户年费数据");
                        continue;
                    }

                    IndividualBusinessEntity individualBusinessEntity = iUserClient.queryIndividualBusinessByIbtaxNo(payEnterpriseExcel.getIndividualBusinessIbtaxNo());
                    if (individualBusinessEntity == null) {
                        log.error("个体户不存在");
                        continue;
                    }

                    if (!(individualBusinessEntity.getMakerId().equals(makerEntity.getId()))) {
                        log.error("个体户不属于创客");
                        continue;
                    }

                    if (!(individualBusinessEntity.getIbname().equals(payEnterpriseExcel.getIndividualBusinessName()))) {
                        log.error("个体户名称和Excel个体户名称不一致");
                        continue;
                    }

                    if (!(Ibstate.OPERATING.equals(individualBusinessEntity.getIbstate()))) {
                        log.error("个体户状态非营运中");
                        continue;
                    }

                    individualId = individualBusinessEntity.getId();
                    enterpriseBusinessAnnualFee = payEnterpriseExcel.getIndividualBusinessAnnualFee();
                    break;

                case INDIVIDUALENTERPRISE:
                    if (StringUtils.isBlank(payEnterpriseExcel.getIndividualEnterpriseName())) {
                        log.error("缺少个独名称数据");
                        continue;
                    }

                    if (StringUtils.isBlank(payEnterpriseExcel.getIndividualEnterpriseIbtaxNo())) {
                        log.error("缺少个独统一社会信用代码数据");
                        continue;
                    }

                    if (payEnterpriseExcel.getIndividualEnterpriseAnnualFee() == null) {
                        log.error("缺少个独年费数据");
                        continue;
                    }

                    IndividualEnterpriseEntity individualEnterpriseEntity = iUserClient.queryIndividualEnterpriseByIbtaxNo(payEnterpriseExcel.getIndividualEnterpriseIbtaxNo());
                    if (individualEnterpriseEntity == null) {
                        log.error("个独不存在");
                        continue;
                    }

                    if (!(individualEnterpriseEntity.getMakerId().equals(makerEntity.getId()))) {
                        log.error("个独不属于创客");
                        continue;
                    }

                    if (!(individualEnterpriseEntity.getIbname().equals(payEnterpriseExcel.getIndividualEnterpriseName()))) {
                        log.error("个独名称和Excel个独名称不一致");
                        continue;
                    }

                    if (!(Ibstate.OPERATING.equals(individualEnterpriseEntity.getIbstate()))) {
                        log.error("个独状态非营运中");
                        continue;
                    }

                    individualId = individualEnterpriseEntity.getId();
                    enterpriseBusinessAnnualFee = payEnterpriseExcel.getIndividualEnterpriseAnnualFee();
                    break;

                default:
                    log.error("支付清单创客类型有误");
                    continue;
            }

            //创建分包支付
            PayMakerEntity payMakerEntity = new PayMakerEntity();
            payMakerEntity.setPayEnterpriseId(payEnterpriseId);
            payMakerEntity.setMakerId(makerEntity.getId());
            payMakerEntity.setMakerType(makerType);
            payMakerEntity.setIndividualId(individualId);
            payMakerEntity.setEnterpriseBusinessAnnualFee(enterpriseBusinessAnnualFee);
            payMakerEntity.setCompanyApplyDatetime(new Date());
            payMakerEntity.setCompanyPayOkDatetime(new Date());
            BeanUtils.copyProperties(payEnterpriseExcel, payMakerEntity);
            save(payMakerEntity);

        }
    }

    @Override
    public List<PayMakerVO> getPayEnterpriseId(Long payEnterpriseId) {
        QueryWrapper<PayMakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayMakerEntity::getPayEnterpriseId, payEnterpriseId);
        List<PayMakerEntity> payMakerEntities = baseMapper.selectList(queryWrapper);
        List<PayMakerVO> payMakerVOList = new ArrayList<>();
        for (PayMakerEntity payMakerEntity : payMakerEntities) {
            payMakerVOList.add(BeanUtil.copy(payMakerEntity, PayMakerVO.class));
        }
        return payMakerVOList;
    }

    @Override
    public List<PayMakerVO> getPayEnterprise(Long payEnterpriseId) {
        QueryWrapper<PayMakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayMakerEntity::getPayEnterpriseId, payEnterpriseId);
        List<PayMakerEntity> payMakerEntities = baseMapper.selectList(queryWrapper);
        List<PayMakerVO> payMakerVOList = new ArrayList<>();
        for (PayMakerEntity payMakerEntity : payMakerEntities) {
            PayMakerVO payMakerVO = BeanUtil.copy(payMakerEntity, PayMakerVO.class);
            MakerInvoiceEntity makerInvoiceEntity = makerInvoiceService.getById(payMakerEntity.getMakerId());
            MakerTaxRecordEntity makerTaxRecordEntity = makerTaxRecordService.getById(payMakerEntity.getMakerId());
            payMakerVO.setVoiceTypeNo(makerInvoiceEntity.getVoiceTypeNo());
            payMakerVO.setVoiceSerialNo(makerInvoiceEntity.getVoiceSerialNo());
            payMakerVO.setMakerVoiceUrl(makerInvoiceEntity.getMakerVoiceUrl());
            payMakerVO.setTaxVoiceTypeNo(makerTaxRecordEntity.getVoiceTypeNo());
            payMakerVO.setTaxVoiceSerialNo(makerTaxRecordEntity.getVoiceSerialNo());
            payMakerVO.setMakerTaxUrl(makerTaxRecordEntity.getMakerTaxUrl());
            payMakerVOList.add(payMakerVO);
        }
        return payMakerVOList;
    }

    @Override
    public R<IndividualYearMonthVO> yearMonthMoney(Long individualId, MakerType makerType) {
        return R.data(baseMapper.yearMonthMoney(individualId, makerType));
    }

    @Override
    public void deleteByPayEnterpriseId(Long payEnterpriseId) {
        baseMapper.deleteByPayEnterpriseId(payEnterpriseId);
    }

}
