package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.vo.SelfHelpInvoiceListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceStatisticsVO;
import com.lgyun.system.user.dto.EnterpriseIndividualBusinessDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDto;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.vo.EnterpriseIndividualBusinessVO;
import com.lgyun.system.user.vo.IndividualBusinessDetailVO;
import com.lgyun.system.user.vo.IndividualBusinessListByMakerVO;

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
     * 根据个独名称查询个体户
     *
     * @param ibname
     * @return
     */
    IndividualBusinessEntity findIBName(String ibname);

    /**
     * 通过创客id查询个体户
     *
     * @param ibtaxNo
     * @return
     */
    IndividualBusinessEntity findIBTaxNo(String ibtaxNo);

    /**
     * 查询当前创客的所有个体户
     *
     * @param current
     * @param size
     * @param makerId
     * @param ibstate
     * @return
     */
    R<IPage<IndividualBusinessListByMakerVO>> listByMaker(Integer current, Integer size, Long makerId, Ibstate ibstate);

    /**
     * 根据ID查询个体户详情
     *
     * @param individualBusinessId
     * @return
     */
    R<IndividualBusinessDetailVO> findById(Long individualBusinessId);

    /**
     * 查询个体户月度开票金额和年度开票金额
     *
     * @param individualBusinessId
     * @param makerType
     * @return
     */
    R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long individualBusinessId, MakerType makerType);

    /**
     * 查询当前商户的所有关联创客的个体户
     *
     * @param page
     * @param enterpriseIndividualBusinessDto
     * @param enterpriseId
     * @return
     */
    R<IPage<EnterpriseIndividualBusinessVO>> getByDtoEnterprise(IPage<EnterpriseIndividualBusinessVO> page, EnterpriseIndividualBusinessDto enterpriseIndividualBusinessDto, Long enterpriseId);

    /**
     * 查询当前商户的关联创客的个体户详情
     *
     * @param individualBusinessId
     * @return
     */
    R<EnterpriseIndividualBusinessVO> findByIdEnterprise(Long individualBusinessId);

    /**
     * 查询个体户开票次数，月度开票金额，年度开票金额和总开票金额
     *
     * @param individualBusinessId
     * @param makerType
     * @return
     */
    R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long individualBusinessId, MakerType makerType);

    /**
     * 查询个体户开票记录
     *
     * @param current
     * @param size
     * @param individualBusinessId
     * @param makerType
     * @return
     */
    R<IPage<SelfHelpInvoiceListVO>> selfHelpInvoiceList(Integer current, Integer size, Long individualBusinessId, MakerType makerType);

    /**
     * 当前商户申请创建个体户
     *
     * @param individualBusinessEnterpriseAddEnterpriseDto
     * @param enterpriseId
     * @return
     */
    R<String> saveByEnterprise(IndividualBusinessEnterpriseAddEnterpriseDto individualBusinessEnterpriseAddEnterpriseDto, Long enterpriseId);
}

