package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.*;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;

import java.util.Date;
import java.util.List;


/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface ISelfHelpInvoiceService extends BaseService<SelfHelpInvoiceEntity> {

//    /**
//     * 查询当前商户所有自助开票记录
//     *
//     * @param enterpriseId
//     * @param makerType
//     * @param selfHelpInvoicesByEnterpriseDto
//     * @param page
//     * @return
//     */
//    R<IPage<SelfHelpInvoiceListByEnterpriseVO>> getSelfHelfInvoicesByEnterprise(Long enterpriseId, MakerType makerType, SelfHelpInvoicesByEnterpriseDTO selfHelpInvoicesByEnterpriseDto, IPage<SelfHelpInvoiceListByEnterpriseVO> page);

    /**
     * 查询商户某条自助开票记录详情
     *
     * @param enterpriseId
     * @param selfHelpInvoiceId
     * @return
     */
    R<SelfHelpInvoiceSingleByEnterpriseVO> getSingleSelfHelfInvoiceByEnterprise(Long enterpriseId, Long selfHelpInvoiceId);

    /**
     * 查询商户某条自助开票记录的所有自助开票明细
     *
     * @param selfHelpInvoiceId
     * @param page
     * @return
     */
    R<IPage<SelfHelpInvoiceDetailListVO>> getSelfHelfInvoiceDetailListBySelfHelfInvoice(Long selfHelpInvoiceId, IPage<SelfHelpInvoiceDetailListVO> page);

    /**
     * 查询商户某条自助开票记录的所有快递信息
     *
     * @param enterpriseId
     * @param selfHelpInvoiceId
     * @param page
     * @return
     */
    R<IPage<SelfHelpInvoiceExpressByEnterpriseProviderVO>> getSelfHelfInvoiceExpressBySelfHelfInvoiceAndEnterprise(Long enterpriseId, Long selfHelpInvoiceId, IPage<SelfHelpInvoiceExpressByEnterpriseProviderVO> page);

    /**
     * 查询服务商所有自助开票记录
     *
     * @param serviceProviderId
     * @param makerType
     * @param selfHelpInvoiceSpApplyState
     * @param selfHelpInvoiceDetailsByServiceProviderDto
     * @param page
     * @return
     */
    R<IPage<SelfHelpInvoiceListByServiceProviderVO>> getSelfHelfInvoicesByServiceProvider(Long serviceProviderId, MakerType makerType, SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState, SelfHelpInvoiceDetailsByServiceProviderDTO selfHelpInvoiceDetailsByServiceProviderDto, IPage<SelfHelpInvoiceListByServiceProviderVO> page);

    /**
     * 查询服务商某条自助开票明细记录详情
     *
     * @param serviceProviderId
     * @param selfHelpInvoiceDetailId
     * @return
     */
    R<SelfHelpInvoiceSingleByServiceProviderVO> getSingleSelfHelfInvoiceByServiceProvider(Long serviceProviderId, Long selfHelpInvoiceDetailId);

    /**
     * 服务商自助开票上传发票税票
     *
     * @param serviceProviderWorkerId
     * @param selfHelpInvoiceDetailInvoiceTaxDto
     * @return
     */
    R<String> uploadInvoiceTaxByProvider(Long serviceProviderWorkerId, SelfHelpInvoiceDetailInvoiceTaxDTO selfHelpInvoiceDetailInvoiceTaxDto);

    /**
     * 服务商自助开票填写快递信息
     *
     * @param serviceProviderWorkerEntity
     * @param selfHelpInvoiceExpressDto
     * @return
     */
    R<String> fillExpressByProvider(ServiceProviderWorkerEntity serviceProviderWorkerEntity, SelfHelpInvoiceExpressDTO selfHelpInvoiceExpressDto);

    /**
     * 查询服务商某条自助开票记录的快递信息
     *
     * @param serviceProviderId
     * @param selfHelpInvoiceId
     * @return
     */
    R<SelfHelpInvoiceExpressByEnterpriseProviderVO> getSelfHelfInvoiceExpressBySelfHelfInvoiceAndProvider(Long serviceProviderId, Long selfHelpInvoiceId);

    /**
     * 查询开票次数，月度开票金额，年度开票金额和总开票金额
     *
     * @param businessEnterpriseId
     * @param makerType
     * @return
     */
    R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long businessEnterpriseId, MakerType makerType);

    /**
     * 查询自助开票记录
     *
     * @param page
     * @param businessEnterpriseId
     * @param makerType
     * @return
     */
    R<IPage<SelfHelpInvoiceListVO>> selfHelpInvoiceList(IPage<SelfHelpInvoiceListVO> page, Long businessEnterpriseId, MakerType makerType);

    /**
     * 根据商户查询众包/众采
     *
     * @param enterpriseId
     * @param serviceProviderName
     * @param page
     * @return
     */
    R findEnterpriseCrowdSourcing(Long enterpriseId, String serviceProviderName, IPage<SelfHelpInvoiceCrowdSourcingVO> page);

    /**
     * 查询详情接口-众包/众采
     *
     * @param selfHelpInvoiceId
     * @return
     */
    R findDetailCrowdSourcing(Long selfHelpInvoiceId);

    /**
     * 自助开票审核
     *
     * @param serviceProviderId
     * @param selfHelpInvoiceId
     * @param applyState
     * @return
     */
    R<String> audit(Long serviceProviderId, Long selfHelpInvoiceId, SelfHelpInvoiceSpApplyState applyState);

    /**
     * 查询众包/众采流水
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param relBureauId
     * @param timeType
     * @param beginDate
     * @param endDate
     * @return
     */
    R<List<TradeVO>> queryCrowdTrade(Long enterpriseId, Long serviceProviderId, Long relBureauId, TimeType timeType, Date beginDate, Date endDate);

    /**
     * 查询相关局众包/众采列表
     *
     * @param relBureauId
     * @return
     */
    R<IPage<TotalCrowdTradeListVO>> queryRelBureauCrowdList(Long relBureauId, IPage<TotalCrowdTradeListVO> page);

    /**
     * 平台跟据创客身份查询自助开票
     *
     * @param enterpriseName
     * @param startTime
     * @param endTime
     * @param makerType
     * @param page
     * @return
     */
    R getAdminMakerTypeSelfHelpInvoice(String enterpriseName, String startTime, String endTime, MakerType makerType, IPage<SelfHelpInvoiceAdminVO> page);

    /**
     * 平台跟据创客身份查询自助开票详情
     *
     * @param selfHelpInvoiceId
     * @return
     */
    R getMakerTypeSelfHelpInvoiceDetails(Long selfHelpInvoiceId);

    /**
     * 平台审核自助开票
     *
     * @param toExamineSelfHelpInvoiceDto
     * @return
     */
    R toExamineSelfHelpInvoice(ToExamineSelfHelpInvoiceDTO toExamineSelfHelpInvoiceDto);

    /**
     * 平台上传支付回单和匹配服务商开票
     *
     * @param selfHelpInvoiceId
     * @param selfHelpInvoiceFeeId
     * @param serviceProviderId
     * @param payCertificate
     * @return
     */
    R matchServiceProvider(Long selfHelpInvoiceId, Long selfHelpInvoiceFeeId, Long serviceProviderId, String payCertificate);

    /**
     * 平台上传快递
     *
     * @param selfHelpInvoiceId
     * @param serviceProviderId
     * @param expressNo
     * @param expressCompanyName
     * @return
     */
    R uploadAdminExpress(Long selfHelpInvoiceId, Long serviceProviderId, String expressNo, String expressCompanyName);

    /**
     * 平台上传发票
     *
     * @param selfHelpInvoiceApplyProviderDetailId
     * @param invoiceScanPictures
     * @param taxScanPictures
     * @return
     */
    R uploadAdminInvoice(Long selfHelpInvoiceApplyProviderDetailId, String invoiceScanPictures, String taxScanPictures);

    /**
     * 查询服务商的自助开票
     *
     * @param page
     * @param keyword
     * @param serviceProviderId
     * @return
     */
    R<IPage<SelfHelpInvoiceSerProVO>> querySelfHelpInvoiceList(Long serviceProviderId, String keyword, IPage<SelfHelpInvoiceSerProVO> page);

    /**
     * 根据服务商和自助开票ID查询自助开票详情
     *
     * @param page
     * @param selfHelpvoiceId
     * @return
     */
    R<IPage<SelfHelpInvoiceDetailProviderVO>> querySelfHelpInvoicePeopleList(Long selfHelpvoiceId, IPage<SelfHelpInvoiceDetailProviderVO> page);


    /**
     * 自助开票上传表单
     */
    R naturalPersonSubmitForm(ObjectType objectType, Long objectId,String listFile,Long serviceProviderId,InvoiceCategory invoiceCategory,MakerType makerType, CrowdSourcingPayType payType, String invoiceType, Long addressId) throws Exception;

    /**
     *自助开票确认提交表单
     */
    R naturalPersonConfirmSubmit(ObjectType objectType, Long objectId,NaturalPersonConfirmSubmitDTO naturalPersonConfirmSubmitDto);

    /**
     *商户查询自助开票
     */
    R querySelfInvoiceList(ObjectType objectType, Long objectId,MakerType makerType,String startTiem,String endTime,IPage<SelfInvoiceListVO> page);

    /**
     * 服务商查询自助开票
     */
    R queryServiceProviderSelfInvoiceList(Long serviceProviderId,MakerType makerType,String startTiem,String endTime,IPage<SelfInvoiceListVO> page);

    /**
     * 平台查询自助开票
     */
    R queryAdminSelfInvoiceList(MakerType makerType,String startTiem,String endTime,IPage<SelfInvoiceListVO> page);
    /**
     *商户查询自助开票详情
     */
    R querySelfInvoiceDetails(Long selfHelpInvoiceId);

    /**
     * 提交自助开票
     */
    R submitSelfHelpInvoice(Long selfHelpInvoiceId);

    /**
     *确认修改
     */
    R confirmModification(Long selfHelpInvoiceId, List<ModificationDTO> list);

    /**
     * 确认支付
     */
    R confirmPayment(Long selfHelpInvoiceId,Long selfHelpInvoiceFeeId,String payCertificate);

    /**
     * 开票
     */
    R createCrowdsourcingInvoice(CreateCrowdsourcingInvoiceDTO createCrowdsourcingInvoiceDTO);

}

