package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.exception.CustomException;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.MakerInvoiceEntity;
import com.lgyun.system.order.entity.MakerTaxRecordEntity;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.lgyun.system.order.excel.PayEnterpriseExcel;
import com.lgyun.system.order.mapper.PayMakerMapper;
import com.lgyun.system.order.service.IMakerInvoiceService;
import com.lgyun.system.order.service.IMakerTaxRecordService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.IPayMakerService;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import com.lgyun.system.user.vo.MakerEnterpriseNumIncomeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
@RequiredArgsConstructor
public class PayMakerServiceImpl extends BaseServiceImpl<PayMakerMapper, PayMakerEntity> implements IPayMakerService {

    private final IUserClient userClient;
    private final IMakerInvoiceService makerInvoiceService;
    private final IMakerTaxRecordService makerTaxRecordService;

    @Autowired
    @Lazy
    private IPayEnterpriseService payEnterpriseService;

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
    public void importPayMakerList(List<PayEnterpriseExcel> list, Long payEnterpriseId, MakerType makerType, Long enterpriseId) {

        if (list.isEmpty()) {
            throw new CustomException("Excel内容不能为空");
        }

        //企业总支付额价税合计总额=服务外包费总额+身份验证费总额/个体户年费总额+第三方支付手续费总额
        BigDecimal payToPlatformAmount = BigDecimal.ZERO;

        //服务税费总额=服务外包费总额*服务税费率
        BigDecimal totalTaxFee = BigDecimal.ZERO;

        //创客到手总额
        BigDecimal totalMakerNetIncome = BigDecimal.ZERO;

        //服务税费率
        BigDecimal serviceRate = BigDecimal.ZERO;

        //服务外包费总额
        BigDecimal sourcingAmount = BigDecimal.ZERO;

        //企业年费总额，个体户，个独，有限公司都有年费，自然人没有年费
        BigDecimal enterpriseBusinessAnnualFee = BigDecimal.ZERO;

        //身份验证费总额
        BigDecimal identifyFee = BigDecimal.ZERO;

        //第三方支付手续费总额
        BigDecimal serviceFee = BigDecimal.ZERO;

        //创客数
        Integer makerNum = list.size();

        //企业年费总额，个体户，个独，有限公司都有年费，自然人没有年费
        BigDecimal singleEnterpriseBusinessAnnualFee = BigDecimal.ZERO;
        for (int i = 1; i <= list.size(); i++) {
            //获取Excel数据
            PayEnterpriseExcel payEnterpriseExcel = list.get(i-1);
            log.info(String.valueOf(payEnterpriseExcel));

            if (StringUtils.isBlank(payEnterpriseExcel.getMakerName())) {
                throw new CustomException("第" + i + "条数据缺少创客姓名");
            }

            if (StringUtils.isBlank(payEnterpriseExcel.getMakerIdcardNo())) {
                throw new CustomException("第" + i + "条数据缺少创客身份证号");
            }

            MakerEntity makerEntity = userClient.queryMakerByIdcardNo(payEnterpriseExcel.getMakerIdcardNo());
            if (makerEntity == null) {
                throw new CustomException("第" + i + "条数据身份证号码为" + payEnterpriseExcel.getMakerIdcardNo() + "的系统创客不存在");
            }

            if (StringUtils.isBlank(makerEntity.getName())) {
                throw new CustomException("第" + i + "条数据的系统创客姓名为空");
            }

            if (!(makerEntity.getName().equals(payEnterpriseExcel.getMakerName()))) {
                throw new CustomException("第" + i + "条数据的Excel创客姓名和系统创客姓名不一致");
            }

            if (!(SignState.SIGNED.equals(makerEntity.getJoinSignState()))) {
                throw new CustomException("第" + i  + "条数据的创客未签署加盟合同");
            }

            int entMakSupplementaryAgreementNum = userClient.queryEntMakSupplementaryAgreementNum(makerEntity.getId(), enterpriseId);
            if (entMakSupplementaryAgreementNum <= 0) {
                throw new CustomException("第" + i  + "条数据的创客未签署商户-创客补充协议");
            }

            int makerEnterpriseNum = userClient.queryMakerEnterpriseRelevanceCount(enterpriseId, makerEntity.getId());
            if (makerEnterpriseNum <= 0) {
                throw new CustomException("第" + i + "条数据身份证号码为" + payEnterpriseExcel.getMakerIdcardNo() + "的创客与商户不存在关联关系");
            }

            int payMakerNum = baseMapper.selectCount(Wrappers.<PayMakerEntity>query().lambda().eq(PayMakerEntity::getPayEnterpriseId, payEnterpriseId).eq(PayMakerEntity::getMakerId, makerEntity.getId()));
            if (payMakerNum > 0) {
                throw new CustomException("第" + i + "条数据的分包已存在");
            }

            if (payEnterpriseExcel.getMakerNetIncome() == null) {
                throw new CustomException("第" + i + "条数据缺少创客到手数据");
            }

            if (payEnterpriseExcel.getServiceRate() == null) {
                throw new CustomException("第" + i + "条数据缺少服务税费率数据");
            }

            //获取服务税费率
            if (i == 1) {
                serviceRate = payEnterpriseExcel.getServiceRate();
            } else {
                if (serviceRate.compareTo(payEnterpriseExcel.getServiceRate()) != 0) {
                    throw new CustomException("第" + i + "条数据服务税费率不一致");
                }
            }

            if (payEnterpriseExcel.getMakerTaxFee() == null) {
                throw new CustomException("第" + i + "条数据缺少服务税费数据");
            }

            if (payEnterpriseExcel.getMakerNeIncome() == null) {
                throw new CustomException("第" + i + "条数据缺少服务外包费数据");
            }

            if (payEnterpriseExcel.getPayFee() == null) {
                throw new CustomException("第" + i + "条数据缺少第三方支付手续费数据");
            }

            if (payEnterpriseExcel.getTotalFee() == null) {
                throw new CustomException("第" + i + "条数据缺少价税合计企业总支付额数据");
            }

            //个体户或个独ID
            Long individualId = null;
            switch (makerType) {

                case NATURALPERSON:
                    if (payEnterpriseExcel.getAuditFee() == null) {
                        throw new CustomException("第" + i + "条数据缺少创客首次身份验证费数据");
                    }

                    //总创客首次身份验证费
                    identifyFee = identifyFee.add(payEnterpriseExcel.getAuditFee());

                    break;

                case INDIVIDUALBUSINESS:
                    if (StringUtils.isBlank(payEnterpriseExcel.getIndividualBusinessName())) {
                        throw new CustomException("第" + i + "条数据缺少个体户名称数据");
                    }

                    if (StringUtils.isBlank(payEnterpriseExcel.getIndividualBusinessIbtaxNo())) {
                        throw new CustomException("第" + i + "条数据缺少个体户统一社会信用代码数据");
                    }

                    if (payEnterpriseExcel.getIndividualBusinessAnnualFee() == null) {
                        throw new CustomException("第" + i + "条数据缺少个体户年费数据");
                    }

                    IndividualBusinessEntity individualBusinessEntity = userClient.queryIndividualBusinessByIbtaxNo(payEnterpriseExcel.getIndividualBusinessIbtaxNo());
                    if (individualBusinessEntity == null) {
                        throw new CustomException("第" + i + "条数据的个体户不存在");
                    }

                    if (!(individualBusinessEntity.getMakerId().equals(makerEntity.getId()))) {
                        throw new CustomException("第" + i + "条数据的个体户不属于创客");
                    }

                    if (!(individualBusinessEntity.getIbname().equals(payEnterpriseExcel.getIndividualBusinessName()))) {
                        throw new CustomException("第" + i + "条数据的Excel个体户名称和系统个体户名称不一致");
                    }

                    if (!(Ibstate.OPERATING.equals(individualBusinessEntity.getIbstate()))) {
                        throw new CustomException("第" + i + "条数据的个体户状态非营运中");
                    }

                    //个体户编号
                    individualId = individualBusinessEntity.getId();
                    //单个个体户年费
                    singleEnterpriseBusinessAnnualFee = payEnterpriseExcel.getIndividualBusinessAnnualFee();
                    break;

                case INDIVIDUALENTERPRISE:
                    if (StringUtils.isBlank(payEnterpriseExcel.getIndividualEnterpriseName())) {
                        throw new CustomException("第" + i + "条数据缺少个独名称数据");
                    }

                    if (StringUtils.isBlank(payEnterpriseExcel.getIndividualEnterpriseIbtaxNo())) {
                        throw new CustomException("第" + i + "条数据缺少个独统一社会信用代码数据");
                    }

                    if (payEnterpriseExcel.getIndividualEnterpriseAnnualFee() == null) {
                        throw new CustomException("第" + i + "条数据缺少个独年费数据");
                    }

                    IndividualEnterpriseEntity individualEnterpriseEntity = userClient.queryIndividualEnterpriseByIbtaxNo(payEnterpriseExcel.getIndividualEnterpriseIbtaxNo());
                    if (individualEnterpriseEntity == null) {
                        throw new CustomException("第" + i + "条数据的个独不存在");
                    }

                    if (!(individualEnterpriseEntity.getMakerId().equals(makerEntity.getId()))) {
                        throw new CustomException("第" + i + "条数据的个独不属于创客");
                    }

                    if (!(individualEnterpriseEntity.getIbname().equals(payEnterpriseExcel.getIndividualEnterpriseName()))) {
                        throw new CustomException("第" + i + "条数据的Excel个独名称和系统个独名称不一致");
                    }

                    if (!(Ibstate.OPERATING.equals(individualEnterpriseEntity.getIbstate()))) {
                        throw new CustomException("第" + i + "条数据的个独状态非营运中");
                    }

                    //个独编号
                    individualId = individualEnterpriseEntity.getId();
                    //单个个独年费
                    singleEnterpriseBusinessAnnualFee = payEnterpriseExcel.getIndividualEnterpriseAnnualFee();
                    break;

                default:
                    throw new CustomException("支付清单创客类型有误");
            }

            //创建分包支付
            PayMakerEntity payMakerEntity = new PayMakerEntity();
            payMakerEntity.setPayEnterpriseId(payEnterpriseId);
            payMakerEntity.setMakerId(makerEntity.getId());
            payMakerEntity.setMakerType(makerType);
            payMakerEntity.setIndividualId(individualId);
            payMakerEntity.setEnterpriseBusinessAnnualFee(singleEnterpriseBusinessAnnualFee);
            payMakerEntity.setCompanyApplyDatetime(new Date());
            payMakerEntity.setCompanyPayOkDatetime(new Date());
            BeanUtils.copyProperties(payEnterpriseExcel, payMakerEntity);
            save(payMakerEntity);

            //企业总支付额价税合计总额=服务外包费总额+身份验证费总额/个体户年费总额+第三方支付手续费总额
            payToPlatformAmount = payToPlatformAmount.add(payEnterpriseExcel.getTotalFee());

            //服务税费总额=服务外包费总额*服务税费率
            totalTaxFee = totalTaxFee.add(payEnterpriseExcel.getMakerTaxFee());

            //创客到手总额
            totalMakerNetIncome = totalMakerNetIncome.add(payEnterpriseExcel.getMakerNetIncome());

            //服务外包费总额
            sourcingAmount = sourcingAmount.add(payEnterpriseExcel.getMakerNeIncome());

            //企业年费总额，个体户，个独，有限公司都有年费，自然人没有年费
            enterpriseBusinessAnnualFee = enterpriseBusinessAnnualFee.add(singleEnterpriseBusinessAnnualFee);

            //第三方支付手续费总额
            serviceFee = serviceFee.add(payEnterpriseExcel.getPayFee());
        }

        //编辑支付清单
        PayEnterpriseEntity payEnterpriseEntity = payEnterpriseService.getById(payEnterpriseId);
        payEnterpriseEntity.setPayToPlatformAmount(payToPlatformAmount);
        payEnterpriseEntity.setTotalTaxFee(totalTaxFee);
        payEnterpriseEntity.setTotalMakerNetIncome(totalMakerNetIncome);
        payEnterpriseEntity.setServiceRate(serviceRate);
        payEnterpriseEntity.setSourcingAmount(sourcingAmount);
        payEnterpriseEntity.setEnterpriseBusinessAnnualFee(enterpriseBusinessAnnualFee);
        payEnterpriseEntity.setIdentifyFee(identifyFee);
        payEnterpriseEntity.setServiceFee(serviceFee);
        payEnterpriseEntity.setMakerNum(makerNum);
        payEnterpriseService.updateById(payEnterpriseEntity);
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

    @Override
    public R<IPage<AcceptPaysheetPayMakerListVO>> queryTotalSubAcceptPaysheetPayMakerList(Long acceptPaysheetId, IPage<AcceptPaysheetPayMakerListVO> page) {
        return R.data(page.setRecords(baseMapper.queryTotalSubAcceptPaysheetPayMakerList(acceptPaysheetId, page)));
    }

    @Override
    public R<String> confirmPayMaker(Long makerId, Long payMakerId) {

        PayMakerEntity payMakerEntity = getById(payMakerId);
        if (payMakerEntity == null) {
            return R.fail("创客支付明细不存在");
        }

        if (payMakerEntity.getMakerId().equals(makerId)) {
            return R.fail("创客支付明细不属于当前创客");
        }

        if (!(payMakerEntity.getPayState().equals(PayMakerPayState.PLATFORMPAID))) {
            return R.fail("服务商未支付，不可确认收款");
        }

        if (payMakerEntity.getPayState().equals(PayMakerPayState.CONFIRMPAID)) {
            return R.fail("创客已确认收款");
        }

        //确认收款
        payMakerEntity.setPayState(PayMakerPayState.CONFIRMPAID);
        payMakerEntity.setMakerConfirmDatetime(new Date());
        updateById(payMakerEntity);

        //判断是否所有分包已确认收款
        int payMakerNum = count(Wrappers.<PayMakerEntity>query().lambda()
                .eq(PayMakerEntity::getPayEnterpriseId, payMakerEntity.getPayEnterpriseId()));

        int confirmpaidPayMakerNum = count(Wrappers.<PayMakerEntity>query().lambda()
                .eq(PayMakerEntity::getPayEnterpriseId, payMakerEntity.getPayEnterpriseId())
                .eq(PayMakerEntity::getPayState, PayMakerPayState.CONFIRMPAID));

        if (payMakerNum == confirmpaidPayMakerNum) {
            PayEnterpriseEntity payEnterpriseEntity = payEnterpriseService.getById(payMakerEntity.getPayEnterpriseId());
            payEnterpriseEntity.setPayState(PayEnterprisePayState.CONFIRMPAY);
            payEnterpriseService.updateById(payEnterpriseEntity);
        }

        return R.success("确认收款成功");
    }

    @Override
    public Integer getPayMakerCount(String payEnterpriseIds) {
        QueryWrapper<PayMakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(PayMakerEntity::getPayEnterpriseId, payEnterpriseIds);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public R<IPage<PayMakerListMakerVO>> queryPayMakerListByMaker(Long makerId, IPage<PayMakerListMakerVO> page) {
        return R.data(page.setRecords(baseMapper.queryPayMakerListByMaker(makerId, page)));
    }

}
