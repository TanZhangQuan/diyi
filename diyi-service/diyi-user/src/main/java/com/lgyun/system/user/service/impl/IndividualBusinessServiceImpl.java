package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.VerifyStatus;
import com.lgyun.common.tool.BeanUtil;
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
import java.util.stream.Collectors;

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
    public R<String> save(IndividualBusinessAddDto individualBusinessAddDto, MakerEntity makerEntity) {

        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经身份实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus()))) {
            return R.fail("请先进行身份实名认证");
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
        QueryWrapper<IndividualBusinessEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualBusinessEntity::getMakerId, makerId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public R<IPage<IndividualBusinessListByMakerVO>> listByMaker(Integer current, Integer size, Long makerId, Ibstate ibstate) {

        QueryWrapper<IndividualBusinessEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualBusinessEntity::getMakerId, makerId)
                .eq(ibstate != null, IndividualBusinessEntity::getIbstate, ibstate)
                .orderByDesc(IndividualBusinessEntity::getCreateTime);

        IPage<IndividualBusinessEntity> pages = this.page(new Page<>(current, size), queryWrapper);

        List<IndividualBusinessListByMakerVO> records = pages.getRecords().stream().map(individualBusinessEntity -> BeanUtil.copy(individualBusinessEntity, IndividualBusinessListByMakerVO.class)).collect(Collectors.toList());

        IPage<IndividualBusinessListByMakerVO> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
        pageVo.setRecords(records);

        return R.data(pageVo);
    }

    @Override
    public R<IndividualBusinessDetailVO> findById(Long individualBusinessId) {

        QueryWrapper<IndividualBusinessEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualBusinessEntity::getId, individualBusinessId);

        IndividualBusinessEntity individualBusinessEntity = baseMapper.selectOne(queryWrapper);
        if (individualBusinessEntity == null) {
            return R.fail("个体户不存在");
        }

        IndividualBusinessDetailVO individualBusinessDetailVO = BeanUtil.copy(individualBusinessEntity, IndividualBusinessDetailVO.class);
        String bizName = makerService.getName(individualBusinessDetailVO.getMakerId());
        individualBusinessDetailVO.setBizName(bizName);
        return R.data(individualBusinessDetailVO);
    }

    @Override
    public R<SelfHelpInvoiceYearMonthMoneyVO> yearMonthMoney(Long individualBusinessId, MakerType makerType) {
        return orderClient.yearMonthMoney(individualBusinessId, makerType);
    }

}
