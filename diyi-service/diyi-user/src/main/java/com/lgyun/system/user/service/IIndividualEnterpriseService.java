package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.vo.SelfHelpInvoiceYearMonthMoneyVO;
import com.lgyun.system.user.dto.IndividualEnterpriseAddDto;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
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

    //新增个独
    R save(IndividualEnterpriseAddDto individualEnterpriseAddDto, MakerEntity makerEntity);

    //通过创客id查询个独
    List<IndividualEnterpriseEntity> findMakerId(Long makerId);

    //查询当前创客的所有个独
    R listByMaker(IPage<IndividualEnterpriseListByMakerVO> page, Long makerId, Ibstate ibstate);

    //根据ID查询个独详情
    R<IndividualEnterpriseDetailVO> findById(Long individualEnterpriseId);

    //查询个独月度开票金额和年度开票金额
    R<SelfHelpInvoiceYearMonthMoneyVO> yearMonthMoney(Long individualEnterpriseId, MakerType makerType);

}

