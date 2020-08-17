package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.dto.PayEnterpriseMakerListDto;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
@Mapper
public interface PayEnterpriseMapper extends BaseMapper<PayEnterpriseEntity> {

    /**
     * 根据创客id查询所有商户
     *
     * @param makerId
     * @param page
     * @return
     */
    List<InvoiceEnterpriseVO> getEnterpriseAll(Long makerId, IPage<InvoiceEnterpriseVO> page);

    /**
     * 根据创客id查询所有商户
     *
     * @param makerId
     * @param enterpriseId
     * @param page
     * @return
     */
    List<InvoiceEnterpriseVO> getEnterpriseMakerIdAll(Long makerId, Long enterpriseId, IPage<InvoiceEnterpriseVO> page);

    /**
     * 根据创客id查询所有商户
     *
     * @param makerId
     * @param enterpriseId
     * @param payMakerId
     * @return
     */
    InvoiceEnterpriseVO getEnterpriseMakerIdDetail(Long makerId, Long enterpriseId, Long payMakerId);

    /**
     * 查询当前商户所有总包支付清单
     *
     * @param enterpriseId
     * @param payEnterpriseMakerListDto
     * @param page
     * @return
     */
    List<PayEnterpriseMakersListVO> getPayEnterprisesByEnterprise(Long enterpriseId, PayEnterpriseMakerListDto payEnterpriseMakerListDto, IPage<PayEnterpriseMakersListVO> page);

    /**
     * 根据支付清单ID查询支付清单关联工单的创客
     *
     * @param payEnterpriseId
     * @param page
     * @return
     */
    List<PayEnterpriseMakerListVO> getMakers(Long payEnterpriseId, IPage<PayEnterpriseMakerListVO> page);

    /**
     * 根据商户查询总包发票
     * @param enterpriseId
     */
    List<EnterpriseLumpSumInvoiceVO> findEnterpriseLumpSumInvoice(String invoiceSerialNo,String serviceProviderName,String startTime,String endTime,Long enterpriseId,IPage<EnterpriseLumpSumInvoiceVO> page);

    /**
     * 查看总包发票详情
     * @param payEnterpriseId
     * @return
     */
    EnterpriseLumpSumInvoiceVO findPayEnterpriseDetails(Long payEnterpriseId);

    /**
     * 根据商户查询支付清单
     */
    List<EnterprisePaymentListVO> findEnterprisePaymentList(Long enterpriseId, String serviceProviderName,IPage<EnterprisePaymentListVO> page);


    /**
     *根据商户查询分包列表-汇总
     */
     List<EnterpriseSubcontractInvoiceVO> findEnterpriseSubcontractSummary(Long enterpriseId, String serviceProviderName, IPage<EnterpriseSubcontractInvoiceVO> page);


    /**
     *根据商户查询分包列表-门征
     */
    List<EnterpriseSubcontractPortalVO> findEnterpriseSubcontractPortal(Long enterpriseId, String serviceProviderName, IPage<EnterpriseSubcontractPortalVO> page);

    /**
     *分包列表详情-汇总
     */
    EnterpriseSubcontractInvoiceVO findDetailSummary(Long makerTotalInvoiceId);

    /**
     *分包列表详情-门征
     */
    EnterpriseSubcontractPortalVO findDetailSubcontractPortal(Long makerInvoiceId);
}

