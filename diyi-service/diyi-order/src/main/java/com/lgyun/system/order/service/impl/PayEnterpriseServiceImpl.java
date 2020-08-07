package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.common.enumeration.WorksheetState;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.PayEnterpriseUploadDto;
import com.lgyun.system.order.dto.PayListDto;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.entity.PayEnterpriseReceiptEntity;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.mapper.PayEnterpriseMapper;
import com.lgyun.system.order.service.IPayEnterpriseReceiptService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.order.vo.InvoiceEnterpriseVO;
import com.lgyun.system.order.vo.PayEnterpriseMakerListVO;
import com.lgyun.system.order.vo.PayEnterpriseStatisticalVO;
import com.lgyun.system.order.vo.PayListVO;
import com.lgyun.system.user.entity.EnterpriseProviderEntity;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayEnterpriseServiceImpl extends BaseServiceImpl<PayEnterpriseMapper, PayEnterpriseEntity> implements IPayEnterpriseService {

    private IPayEnterpriseReceiptService payEnterpriseReceiptService;
    private IWorksheetService worksheetService;
    private IUserClient userClient;

    @Override
    public R<IPage<InvoiceEnterpriseVO>> getEnterpriseAll(Long makerId, IPage<InvoiceEnterpriseVO> page) {
        return R.data(page.setRecords(baseMapper.getEnterpriseAll(makerId, page)));
    }

    @Override
    public R<IPage<InvoiceEnterpriseVO>> getEnterpriseMakerIdAll(Long makerId, Long enterpriseId, IPage<InvoiceEnterpriseVO> page) {
        return R.data(page.setRecords(baseMapper.getEnterpriseMakerIdAll(makerId, enterpriseId, page)));
    }

    @Override
    public R<InvoiceEnterpriseVO> getEnterpriseMakerIdDetail(Long makerId, Long enterpriseId, Long payMakerId) {
        InvoiceEnterpriseVO enterpriseMakerIdDetail = baseMapper.getEnterpriseMakerIdDetail(makerId, enterpriseId, payMakerId);
        if (null != enterpriseMakerIdDetail.getMakerNum() && enterpriseMakerIdDetail.getMakerNum() > 1) {
            return R.fail("抱歉，由于此发票人数过多，你没有权限观看");
        }
        return R.data(enterpriseMakerIdDetail);
    }

    @Override
    public R<PayEnterpriseStatisticalVO> statistical(Long enterpriseId) {
        return R.data(baseMapper.statistical(enterpriseId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> upload(PayEnterpriseUploadDto payEnterpriseUploadDto, Long enterpriseId) {

        //判断服务商和商户是否关联
        EnterpriseProviderEntity enterpriseProviderEntity = userClient.findByEnterpriseIdServiceProviderId(enterpriseId, payEnterpriseUploadDto.getServiceProviderId());
        if (enterpriseProviderEntity == null){
            return R.fail("服务商和商户未关联");
        }

        //判断工单是否属于商户已完毕工单
        if (payEnterpriseUploadDto.getWorksheetId() != null) {
            WorksheetEntity worksheetEntity = worksheetService.getById(payEnterpriseUploadDto.getWorksheetId());
            if (worksheetEntity == null) {
                return R.fail("工单不存在");
            }

            if (!(worksheetEntity.getEnterpriseId().equals(enterpriseId))) {
                return R.fail("工单不属于商户");
            }

            if (!(WorksheetState.FINISHED.equals(worksheetEntity.getWorksheetState()))) {
                return R.fail("工单未完毕");
            }

            if (!(WorkSheetType.SUBPACKAGE.equals(worksheetEntity.getWorksheetType()))) {
                return R.fail("工单类型有误");
            }
        }

        //新建总包支付清单
        PayEnterpriseEntity payEnterpriseEntity = new PayEnterpriseEntity();
        payEnterpriseEntity.setEnterpriseId(enterpriseId);
        payEnterpriseEntity.setServiceProviderId(payEnterpriseUploadDto.getServiceProviderId());
        payEnterpriseEntity.setChargeListUrl(payEnterpriseUploadDto.getChargeListUrl());
        payEnterpriseEntity.setWorksheetId(payEnterpriseUploadDto.getWorksheetId());
        save(payEnterpriseEntity);

        //支付回单拆分
        String[] split = payEnterpriseUploadDto.getPayReceiptUrl().split(",");
        for (int i = 0; i < split.length; i++) {
            if (StringUtils.isNotBlank(split[i])) {
                PayEnterpriseReceiptEntity payEnterpriseReceiptEntity = new PayEnterpriseReceiptEntity();
                payEnterpriseReceiptEntity.setPayEnterpriseId(payEnterpriseEntity.getId());
                payEnterpriseReceiptEntity.setEnterprisePayReceiptUrl(split[i]);
                payEnterpriseReceiptService.save(payEnterpriseReceiptEntity);
            }
        }

        return R.success("上传支付清单成功");

    }

    @Override
    public R<IPage<PayListVO>> getByDtoEnterprise(Long enterpriseId, PayListDto payListDto, IPage<PayListVO> page) {

        if (payListDto.getBeginDate() != null && payListDto.getEndDate() != null) {
            if (payListDto.getBeginDate().after(payListDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getByDtoEnterprise(enterpriseId, payListDto, page)));
    }

    @Override
    public R<IPage<PayEnterpriseMakerListVO>> getMakers(Long payEnterpriseId, IPage<PayEnterpriseMakerListVO> page) {
        return R.data(page.setRecords(baseMapper.getMakers(payEnterpriseId, page)));
    }
}
