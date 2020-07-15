package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.vo.SelfHelpInvoiceYearMonthMoneyVO;
import com.lgyun.system.user.dto.IndividualBusinessAddDto;
import com.lgyun.system.user.dto.IndividualBusinessListByMakerDto;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.vo.IndividualBusinessDetailVO;
import com.lgyun.system.user.vo.IndividualBusinessListByMakerVO;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
public interface IIndividualBusinessService extends BaseService<IndividualBusinessEntity> {

    //新增个体户
    R save(IndividualBusinessAddDto individualBusinessAddDto, BladeUser bladeUser);

    //通过创客id查询个体户
    IndividualBusinessEntity findMakerId(Long makerId);

    //查询当前创客的所有个体户
    R listByMaker(IPage<IndividualBusinessListByMakerVO> page, IndividualBusinessListByMakerDto individualBusinessListByMakerDto);

    //根据ID查询个体户详情
    R<IndividualBusinessDetailVO> findById(Long individualBusinessId);

    //查询个体户月度开票金额和年度开票金额
    R<SelfHelpInvoiceYearMonthMoneyVO> yearMonthMoney(Long individualBusinessId, MakerType makerType);

}

