package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.vo.SelfHelpInvoiceStatisticsVO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseWebAddDto;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
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
public interface IIndividualBusinessService extends BaseService<IndividualBusinessEntity> {

    /**
     * 新增个体户
     *
     * @param individualBusinessEnterpriseAddDto
     * @param makerEntity
     * @return
     */
    R<String> save(IndividualBusinessEnterpriseAddDto individualBusinessEnterpriseAddDto, MakerEntity makerEntity);

    /**
     * 通过创客id查询个体户
     *
     * @param makerId
     * @return
     */
    List<IndividualBusinessEntity> findMakerId(Long makerId);

    /**
     * 查询当前创客的所有个体户
     *
     * @param query
     * @param makerId
     * @param ibstate
     * @return
     */
    R<IPage<IndividualBusinessEnterpriseListByMakerVO>> listByMaker(Query query, Long makerId, Ibstate ibstate);

    /**
     * 根据ID查询个体户详情
     *
     * @param individualBusinessId
     * @return
     */
    R<IndividualBusinessEnterpriseDetailsVO> findById(Long individualBusinessId);

    /**
     * 查询个体户月度开票金额和年度开票金额
     *
     * @param individualBusinessId
     * @param invoicePeopleType
     * @return
     */
    R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long individualBusinessId, InvoicePeopleType invoicePeopleType);

    /**
     * 查询当前商户的所有关联创客的个体户
     *
     * @param page
     * @param enterpriseId
     * @param serviceProviderId
     * @param ibstate
     * @param individualBusinessEnterpriseDto
     * @return
     */
    R<IPage<IndividualBusinessEnterpriseDetailsVO>> getIndividualBusinessList(IPage<IndividualBusinessEnterpriseDetailsVO> page, Long enterpriseId, Long serviceProviderId, Ibstate ibstate, IndividualBusinessEnterpriseDto individualBusinessEnterpriseDto);

    /**
     * 查询个体户开票次数，月度开票金额，年度开票金额和总开票金额
     *
     * @param individualBusinessId
     * @param invoicePeopleType
     * @return
     */
    R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long individualBusinessId, InvoicePeopleType invoicePeopleType);

    /**
     * 查询个体户开票记录
     *
     * @param query
     * @param individualBusinessId
     * @param invoicePeopleType
     * @return
     */
    R selfHelpInvoiceList(Query query, Long individualBusinessId, InvoicePeopleType invoicePeopleType);

    /**
     * 当前商户申请创建个体户
     *
     * @param individualBusinessEnterpriseWebAddDto
     * @param enterpriseId
     * @return
     */
    R<String> saveByEnterprise(IndividualBusinessEnterpriseWebAddDto individualBusinessEnterpriseWebAddDto, Long enterpriseId);

    /**
     * 根据创客ID, 统一社会信用代码查询个体户
     *
     * @param makerId
     * @param ibtaxNo
     * @return
     */
    IndividualBusinessEntity findByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo);

    /**
     * 统一社会信用代码查询个体户
     *
     * @param ibtaxNo
     * @return
     */
    IndividualBusinessEntity findByIbtaxNo(String ibtaxNo);

    /**
     * 查询个体户年审信息
     *
     * @param query
     * @param individualBusinessId
     * @return
     */
    R<IPage<EnterpriseReportsVO>> queryEnterpriseReports(Query query, Long individualBusinessId);

    /**
     * 注销个体户
     *
     * @param individualBusinessId
     * @return
     */
    R<String> cancell(Long individualBusinessId);
}

