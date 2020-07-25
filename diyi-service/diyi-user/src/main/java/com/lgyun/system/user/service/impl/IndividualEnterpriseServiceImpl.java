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
import com.lgyun.system.user.dto.IndividualEnterpriseAddDto;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.mapper.IndividualEnterpriseMapper;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.vo.IndividualEnterpriseDetailVO;
import com.lgyun.system.user.vo.IndividualEnterpriseListByMakerVO;
import com.lgyun.system.user.wrapper.IndividualEnterpriseWrapper;
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
public class IndividualEnterpriseServiceImpl extends BaseServiceImpl<IndividualEnterpriseMapper, IndividualEnterpriseEntity> implements IIndividualEnterpriseService {

    private IMakerService makerService;
    private IOrderClient orderClient;

    @Override
    public R<String> save(IndividualEnterpriseAddDto individualEnterpriseAddDto, MakerEntity makerEntity) {

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
    public List<IndividualEnterpriseEntity> findMakerId(Long makerId) {
        QueryWrapper<IndividualEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualEnterpriseEntity::getMakerId, makerId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public R<IPage<IndividualEnterpriseListByMakerVO>> listByMaker(Integer current, Integer size, Long makerId, Ibstate ibstate) {

        QueryWrapper<IndividualEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualEnterpriseEntity::getMakerId, makerId)
                .eq(ibstate != null, IndividualEnterpriseEntity::getIbstate, ibstate)
                .orderByDesc(IndividualEnterpriseEntity::getCreateTime);

        IPage<IndividualEnterpriseEntity> pages = this.page(new Page<>(current, size), queryWrapper);

        List<IndividualEnterpriseListByMakerVO> records = pages.getRecords().stream().map(individualEnterpriseEntity -> BeanUtil.copy(individualEnterpriseEntity, IndividualEnterpriseListByMakerVO.class)).collect(Collectors.toList());

        IPage<IndividualEnterpriseListByMakerVO> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
        pageVo.setRecords(records);

        return R.data(pageVo);
    }

    @Override
    public R<IndividualEnterpriseDetailVO> findById(Long individualEnterpriseId) {

        QueryWrapper<IndividualEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualEnterpriseEntity::getId, individualEnterpriseId);

        IndividualEnterpriseEntity individualEnterpriseEntity = baseMapper.selectOne(queryWrapper);
        if (individualEnterpriseEntity == null) {
            return R.fail("个独不存在");
        }

        IndividualEnterpriseDetailVO individualEnterpriseDetailVO = IndividualEnterpriseWrapper.build().individualEnterpriseDetailVO(individualEnterpriseEntity);
        String bizName = makerService.getName(individualEnterpriseDetailVO.getMakerId());
        individualEnterpriseDetailVO.setBizName(bizName);
        return R.data(individualEnterpriseDetailVO);
    }

    @Override
    public R<SelfHelpInvoiceYearMonthMoneyVO> yearMonthMoney(Long individualEnterpriseId, MakerType makerType) {
        return orderClient.yearMonthMoney(individualEnterpriseId, makerType);
    }

}
