package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.vo.SelfHelpInvoiceListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceStatisticsVO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseWebAddDto;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.vo.EnterpriseReportsVO;
import com.lgyun.system.user.vo.IndividualBusinessEnterpriseDetailsVO;
import com.lgyun.system.user.vo.IndividualBusinessEnterpriseListByMakerVO;

import java.util.List;

/**
 * Service 接口
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
public interface IIndividualEnterpriseService extends BaseService<IndividualEnterpriseEntity> {

    /**
     * 新增个独
     *
     * @param individualBusinessEnterpriseAddDto
     * @param makerEntity
     * @return
     */
    R<String> save(IndividualBusinessEnterpriseAddDto individualBusinessEnterpriseAddDto, MakerEntity makerEntity);

    /**
     * 通过创客id查询个独
     *
     * @param makerId
     * @return
     */
    List<IndividualEnterpriseEntity> findMakerId(Long makerId);

    /**
     * 查询当前创客的所有个独
     *
     * @param query
     * @param makerId
     * @param ibstate
     * @return
     */
    R<IPage<IndividualBusinessEnterpriseListByMakerVO>> listByMaker(Query query, Long makerId, Ibstate ibstate);

    /**
     * 根据ID查询个独详情
     *
     * @param individualEnterpriseId
     * @return
     */
    R<IndividualBusinessEnterpriseDetailsVO> findById(Long individualEnterpriseId);

    /**
     * 查询个独月度开票金额和年度开票金额
     *
     * @param individualEnterpriseId
     * @param invoicePeopleType
     * @return
     */
    R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long individualEnterpriseId, InvoicePeopleType invoicePeopleType);

    /**
     * 查询当前商户的所有关联创客的个独
     *
     * @param page
     * @param enterpriseId
     * @param serviceProviderId
     * @param individualBusinessEnterpriseDto
     * @return
     */
    R<IPage<IndividualBusinessEnterpriseDetailsVO>> getIndividualBusinessList(IPage<IndividualBusinessEnterpriseDetailsVO> page, Long enterpriseId, Long serviceProviderId, IndividualBusinessEnterpriseDto individualBusinessEnterpriseDto);

    /**
     * 查询个独开票次数，月度开票金额，年度开票金额和总开票金额
     *
     * @param individualEnterpriseId
     * @param invoicePeopleType
     * @return
     */
    R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long individualEnterpriseId, InvoicePeopleType invoicePeopleType);

    /**
     * 查询个独开票记录
     *
     * @param query
     * @param individualEnterpriseId
     * @param invoicePeopleType
     * @return
     */
    R<IPage<SelfHelpInvoiceListVO>> selfHelpInvoiceList(Query query, Long individualEnterpriseId, InvoicePeopleType invoicePeopleType);

    /**
     * 当前商户申请创建个独
     *
     * @param individualBusinessEnterpriseWebAddDto
     * @param enterpriseId
     * @return
     */
    R<String> saveByEnterprise(IndividualBusinessEnterpriseWebAddDto individualBusinessEnterpriseWebAddDto, Long enterpriseId);

    /**
     * 根据创客ID, 统一社会信用代码查询个独
     *
     * @param makerId
     * @param ibtaxNo
     * @return
     */
    IndividualEnterpriseEntity findByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo);


    /**
     * 统一社会信用代码查询个独
     *
     * @param ibtaxNo
     * @return
     */
    IndividualEnterpriseEntity findByIbtaxNo(String ibtaxNo);

    /**
     * 查询个独年审信息
     *
     * @param query
     * @param individualEnterpriseId
     * @return
     */
    R<IPage<EnterpriseReportsVO>> queryEnterpriseReports(Query query, Long individualEnterpriseId);

    /**
     * 修改个独状态
     *
     * @param serviceProviderId
     * @param individualEnterpriseId
     * @param ibstate
     * @return
     */
    R<String> updateIbstate(Long serviceProviderId, Long individualEnterpriseId, Ibstate ibstate);

}

