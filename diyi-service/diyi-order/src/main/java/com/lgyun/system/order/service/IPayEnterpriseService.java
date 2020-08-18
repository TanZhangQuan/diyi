package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDto;
import com.lgyun.system.order.dto.PayEnterpriseMakerListDto;
import com.lgyun.system.order.dto.PayEnterpriseUploadDto;
import com.lgyun.system.order.dto.SelfHelpInvoicePayDto;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;

/**
 * Service 接口
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
public interface IPayEnterpriseService extends BaseService<PayEnterpriseEntity> {

    /**
     * 根据创客id查询所有商户
     *
     * @param makerId
     * @param page
     * @return
     */
    R<IPage<InvoiceEnterpriseVO>> getEnterpriseAll(Long makerId, IPage<InvoiceEnterpriseVO> page);


    /**
     * 根据创客id和商户id查询创客在商户下所开的票
     *
     * @param makerId
     * @param enterpriseId
     * @param page
     * @return
     */
    R<IPage<InvoiceEnterpriseVO>> getEnterpriseMakerIdAll(Long makerId, Long enterpriseId, IPage<InvoiceEnterpriseVO> page);

    /**
     * 根据创客id,商户id和创客支付id查询票的详情
     *
     * @param makerId
     * @param enterpriseId
     * @param payMakerId
     * @return
     */
    R<InvoiceEnterpriseVO> getEnterpriseMakerIdDetail(Long makerId, Long enterpriseId, Long payMakerId);

    /**
     * 上传支付清单
     *
     * @param payEnterpriseUploadDto
     * @param enterpriseId
     * @return
     */
    R<String> upload(PayEnterpriseUploadDto payEnterpriseUploadDto, Long enterpriseId);

    /**
     * 查询当前商户所有总包支付清单
     *
     * @param enterpriseId
     * @param payEnterpriseMakerListDto
     * @param page
     * @return
     */
    R<IPage<PayEnterpriseMakersListVO>> getPayEnterprisesByEnterprise(Long enterpriseId, PayEnterpriseMakerListDto payEnterpriseMakerListDto, IPage<PayEnterpriseMakersListVO> page);

    /**
     * 根据支付清单ID查询支付清单关联工单的创客
     *
     * @param payEnterpriseId
     * @param page
     * @return
     */
    R<IPage<PayEnterpriseMakerListVO>> getMakers(Long payEnterpriseId, IPage<PayEnterpriseMakerListVO> page);

    /**
     * 根据商户查询总包发票
     *
     * @param enterpriseId
     * @param page
     * @return
     */
    R<IPage<EnterpriseLumpSumInvoiceVO>> findEnterpriseLumpSumInvoice(String invoiceSerialNo, String serviceProviderName, String startTime, String endTime, Long enterpriseId, IPage<EnterpriseLumpSumInvoiceVO> page);

    /**
     * 取消申请
     */
    R withdraw(Long applicationId);

    /**
     * 查看总包发票详情
     */
    R findPayEnterpriseDetails(Long payEnterpriseId);

    /**
     * 根据商户查询支付清单
     */
    R findEnterprisePaymentList(Long enterpriseId, String serviceProviderName, IPage<EnterprisePaymentListVO> page);

    /**
     * 获取当前商户所有已完毕的总包+分包类型的工单
     *
     * @param query
     * @param enterpriseId
     * @param subpackage
     * @param worksheetNo
     * @param worksheetName
     * @return
     */
    R<IPage<WorksheetByEnterpriseVO>> getWorksheetByEnterpriseId(Query query, Long enterpriseId, WorkSheetType subpackage, String worksheetNo, String worksheetName);

    /**
     * 获取当前商户关联服务商
     *
     * @param query
     * @param enterpriseId
     * @param serviceProviderName
     * @return
     */
    R getServiceProviderByEnterpriseId(Query query, Long enterpriseId, String serviceProviderName);

    /**
     * 上传总包交付支付验收单
     *
     * @param acceptPaysheet
     * @param enterpriseWorkerEntity
     * @return
     */
    R<String> uploadAcceptPaysheet(AcceptPaysheetSaveDto acceptPaysheet, EnterpriseWorkerEntity enterpriseWorkerEntity);

    /**
     * 查询当前商户所有自主开票记录(众包)
     *
     * @param enterpriseId
     * @param selfHelpInvoicePayDto
     * @param page
     * @return
     */
    R<IPage<SelfHelpInvoicePayVO>> getSelfHelfInvoiceByEnterpriseId(Long enterpriseId, SelfHelpInvoicePayDto selfHelpInvoicePayDto, IPage<SelfHelpInvoicePayVO> page);

    /**
     * 根据商户查询分包列表-汇总
     */
    R findEnterpriseSubcontractSummary(Long enterpriseId, String serviceProviderName, IPage<EnterpriseSubcontractInvoiceVO> page);

    /**
     * 根据商户查询分包列表-门征
     */
    R findEnterpriseSubcontractPortal(Long enterpriseId, String serviceProviderName, IPage<EnterpriseSubcontractPortalVO> page);

    /**
     * 查询详情接口-汇总
     */
    R findDetailSummary(Long makerTotalInvoiceId);

    /**
     * 查询详情接口-门征
     */
    R findDetailSubcontractPortal(Long makerInvoiceId);

    /**
     * 根据工单编号获取工单
     *
     * @param worksheetNo
     * @return
     */
    R<WorksheetNoIdVO> getWorksheetByWorksheetNo(String worksheetNo);
}

