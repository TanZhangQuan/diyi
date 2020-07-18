package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.vo.SelfHelpInvoiceYearMonthMoneyVO;
import com.lgyun.system.user.dto.IndividualBusinessAddDto;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.vo.IndividualBusinessDetailVO;
import com.lgyun.system.user.vo.IndividualBusinessListByMakerVO;

import java.util.List;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
public interface IIndividualBusinessService extends BaseService<IndividualBusinessEntity> {

    //新增个体户
    R save(IndividualBusinessAddDto individualBusinessAddDto, MakerEntity makerEntity);

    //通过创客id查询个体户
    List<IndividualBusinessEntity> findMakerId(Long makerId);

    //查询当前创客的所有个体户
    R<IPage<IndividualBusinessListByMakerVO>> listByMaker(IPage<IndividualBusinessListByMakerVO> page, Long makerId, Ibstate ibstate);

    //根据ID查询个体户详情
    R<IndividualBusinessDetailVO> findById(Long individualBusinessId);

    //查询个体户月度开票金额和年度开票金额
    R<SelfHelpInvoiceYearMonthMoneyVO> yearMonthMoney(Long individualBusinessId, MakerType makerType);

}

