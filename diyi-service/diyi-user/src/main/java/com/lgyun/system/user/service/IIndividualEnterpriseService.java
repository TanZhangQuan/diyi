package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.vo.SelfHelpInvoiceListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceStatisticsVO;
import com.lgyun.system.user.dto.EnterpriseIndividualEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddEnterpriseDto;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.vo.EnterpriseIndividualEnterpriseVO;
import com.lgyun.system.user.vo.IndividualEnterpriseDetailVO;
import com.lgyun.system.user.vo.IndividualEnterpriseListByMakerVO;

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
     * @param current
     * @param size
     * @param makerId
     * @param ibstate
     * @return
     */
    R<IPage<IndividualEnterpriseListByMakerVO>> listByMaker(Integer current, Integer size, Long makerId, Ibstate ibstate);

    /**
     * 根据ID查询个独详情
     *
     * @param individualEnterpriseId
     * @return
     */
    R<IndividualEnterpriseDetailVO> findById(Long individualEnterpriseId);

    /**
     * 查询个独月度开票金额和年度开票金额
     *
     * @param individualEnterpriseId
     * @param makerType
     * @return
     */
    R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long individualEnterpriseId, MakerType makerType);

    /**
     * 查询当前商户的所有关联创客的个独
     *
     * @param page
     * @param enterpriseIndividualEnterpriseDto
     * @param enterpriseId
     * @return
     */
    R<IPage<EnterpriseIndividualEnterpriseVO>> getByDtoEnterprise(IPage<EnterpriseIndividualEnterpriseVO> page, EnterpriseIndividualEnterpriseDto enterpriseIndividualEnterpriseDto, Long enterpriseId);

    /**
     * 查询当前商户的关联创客的个独详情
     *
     * @param individualEnterpriseId
     * @return
     */
    R<EnterpriseIndividualEnterpriseVO> findByIdEnterprise(Long individualEnterpriseId);

    /**
     * 查询个独开票次数，月度开票金额，年度开票金额和总开票金额
     *
     * @param individualBusinessId
     * @param makerType
     * @return
     */
    R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long individualBusinessId, MakerType makerType);

    /**
     * 查询个独开票记录
     *
     * @param current
     * @param size
     * @param individualBusinessId
     * @param makerType
     * @return
     */
    R<IPage<SelfHelpInvoiceListVO>> selfHelpInvoiceList(Integer current, Integer size, Long individualBusinessId, MakerType makerType);

    /**
     * 当前商户申请创建个独
     *
     * @param individualBusinessEnterpriseAddEnterpriseDto
     * @param enterpriseId
     * @return
     */
    R<String> saveByEnterprise(IndividualBusinessEnterpriseAddEnterpriseDto individualBusinessEnterpriseAddEnterpriseDto, Long enterpriseId);
}

