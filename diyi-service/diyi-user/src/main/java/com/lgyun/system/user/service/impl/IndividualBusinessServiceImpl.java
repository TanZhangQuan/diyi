package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.VerifyStatus;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.feign.IOrderClient;
import com.lgyun.system.order.vo.SelfHelpInvoiceYearMonthMoneyVO;
import com.lgyun.system.user.dto.IndividualBusinessAddDto;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.mapper.IndividualBusinessMapper;
import com.lgyun.system.user.service.IIndividualBusinessService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.vo.IndividualBusinessDetailVO;
import com.lgyun.system.user.vo.IndividualBusinessListByMakerVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@Service
@AllArgsConstructor
public class IndividualBusinessServiceImpl extends BaseServiceImpl<IndividualBusinessMapper, IndividualBusinessEntity> implements IIndividualBusinessService {

    private IMakerService makerService;
    private IOrderClient orderClient;

    @Override
    public R save(IndividualBusinessAddDto individualBusinessAddDto, MakerEntity makerEntity) {

        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经刷脸实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus()))) {
            return R.fail("请先进行刷脸实名认证");
        }

        IndividualBusinessEntity individualBusinessEntity = new IndividualBusinessEntity();
        BeanUtils.copyProperties(individualBusinessAddDto, individualBusinessEntity);
        individualBusinessEntity.setIbstate(Ibstate.REGISTERING);
        individualBusinessEntity.setMakerId(makerEntity.getId());
        save(individualBusinessEntity);

        return R.success("个体户新增成功");
    }

    @Override
    public List<IndividualBusinessEntity> findMakerId(Long makerId) {
        return baseMapper.findMakerId(makerId);
    }

    @Override
    public R<IPage<IndividualBusinessListByMakerVO>> listByMaker(IPage<IndividualBusinessListByMakerVO> page, Long makerId, Ibstate ibstate) {
        return R.data(page.setRecords(baseMapper.listByMaker(makerId, ibstate, page)));
    }

    @Override
    public R<IndividualBusinessDetailVO> findById(Long individualBusinessId) {
        IndividualBusinessDetailVO individualBusinessDetailVO = baseMapper.findById(individualBusinessId);
        if (individualBusinessDetailVO != null) {
            String bizName = makerService.getName(individualBusinessDetailVO.getMakerId());
            individualBusinessDetailVO.setBizName(bizName);
        }
        return R.data(individualBusinessDetailVO);
    }

    @Override
    public R<SelfHelpInvoiceYearMonthMoneyVO> yearMonthMoney(Long individualBusinessId, MakerType makerType) {
        return orderClient.yearMonthMoney(individualBusinessId, makerType);
    }

}
