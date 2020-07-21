package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.VerifyStatus;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.feign.IOrderClient;
import com.lgyun.system.order.vo.SelfHelpInvoiceYearMonthMoneyVO;
import com.lgyun.system.user.dto.IndividualEnterpriseAddDto;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.mapper.IndividualEnterpriseMapper;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.vo.IndividualEnterpriseDetailVO;
import com.lgyun.system.user.vo.IndividualEnterpriseListByMakerVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@Service
@AllArgsConstructor
public class IndividualEnterpriseServiceImpl extends BaseServiceImpl<IndividualEnterpriseMapper, IndividualEnterpriseEntity> implements IIndividualEnterpriseService {

    private IMakerService makerService;
    private IOrderClient orderClient;

    @Override
    public R save(IndividualEnterpriseAddDto individualEnterpriseAddDto, MakerEntity makerEntity) {

        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经身份实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus()))) {
            return R.fail("请先进行身份实名认证");
        }

        IndividualEnterpriseEntity individualEnterpriseEntity = new IndividualEnterpriseEntity();
        BeanUtils.copyProperties(individualEnterpriseAddDto, individualEnterpriseEntity);
        individualEnterpriseEntity.setIbstate(Ibstate.REGISTERING);
        individualEnterpriseEntity.setMakerId(makerEntity.getId());
        save(individualEnterpriseEntity);

        return R.success("个独新增成功");
    }

    /**
     * 通过创客id查询
     */
    @Override
    public List<IndividualEnterpriseEntity> findMakerId(Long makerId){
        List<IndividualEnterpriseEntity> individualEnterpriseEntity = baseMapper.findMakerId(makerId);
        return individualEnterpriseEntity;
    }

    @Override
    public R<IPage<IndividualEnterpriseListByMakerVO>> listByMaker(IPage<IndividualEnterpriseListByMakerVO> page, Long makerId, Ibstate ibstate) {
        return R.data(page.setRecords(baseMapper.listByMaker(makerId, ibstate, page)));
    }

    @Override
    public R<IndividualEnterpriseDetailVO> findById(Long individualEnterpriseId) {
        IndividualEnterpriseDetailVO individualEnterpriseDetailVO = baseMapper.findById(individualEnterpriseId);
        String bizName = makerService.getName(individualEnterpriseDetailVO.getMakerId());
        individualEnterpriseDetailVO.setBizName(bizName);
        return R.data(individualEnterpriseDetailVO);
    }

    @Override
    public R<SelfHelpInvoiceYearMonthMoneyVO> yearMonthMoney(Long individualEnterpriseId, MakerType makerType) {
        return orderClient.yearMonthMoney(individualEnterpriseId, makerType);
    }

}
