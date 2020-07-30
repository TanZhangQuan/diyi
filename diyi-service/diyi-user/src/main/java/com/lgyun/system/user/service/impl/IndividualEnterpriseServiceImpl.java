package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BizType;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.VerifyStatus;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.feign.IOrderClient;
import com.lgyun.system.order.vo.SelfHelpInvoiceListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceStatisticsVO;
import com.lgyun.system.user.dto.EnterpriseIndividualEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddEnterpriseDto;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.mapper.IndividualEnterpriseMapper;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.vo.EnterpriseIndividualEnterpriseVO;
import com.lgyun.system.user.vo.IndividualEnterpriseDetailVO;
import com.lgyun.system.user.vo.IndividualEnterpriseListByMakerVO;
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

    private IOrderClient orderClient;
    private IMakerService makerService;

    @Override
    public R<String> save(IndividualBusinessEnterpriseAddDto individualBusinessEnterpriseAddDto, MakerEntity makerEntity) {

        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经身份实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus()))) {
            return R.fail("请先进行身份实名认证");
        }

        IndividualEnterpriseEntity individualEnterpriseEntity = new IndividualEnterpriseEntity();
        BeanUtils.copyProperties(individualBusinessEnterpriseAddDto, individualEnterpriseEntity);
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
        return R.data(baseMapper.findById(individualEnterpriseId));
    }

    @Override
    public R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long individualEnterpriseId, MakerType makerType) {
        return orderClient.yearMonthMoney(individualEnterpriseId, makerType);
    }

    @Override
    public R<IPage<EnterpriseIndividualEnterpriseVO>> getByDtoEnterprise(IPage<EnterpriseIndividualEnterpriseVO> page, EnterpriseIndividualEnterpriseDto enterpriseIndividualEnterpriseDto, Long enterpriseId) {
        return R.data(page.setRecords(baseMapper.getByDtoEnterprise(enterpriseId, enterpriseIndividualEnterpriseDto, page)));
    }

    @Override
    public R<EnterpriseIndividualEnterpriseVO> findByIdEnterprise(Long individualEnterpriseId) {
        return R.data(baseMapper.findByIdEnterprise(individualEnterpriseId));
    }

    @Override
    public R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long individualBusinessId, MakerType makerType) {
        return orderClient.selfHelpInvoiceStatistics(individualBusinessId, makerType);
    }

    @Override
    public R<IPage<SelfHelpInvoiceListVO>> selfHelpInvoiceList(Integer current, Integer size, Long individualBusinessId, MakerType makerType) {
        return orderClient.selfHelpInvoiceList(current, size, individualBusinessId, makerType);
    }

    @Override
    public R<String> saveByEnterprise(IndividualBusinessEnterpriseAddEnterpriseDto individualBusinessEnterpriseAddEnterpriseDto, Long enterpriseId) {
        //新建创客
        MakerEntity makerEntity = makerService.makerSave(individualBusinessEnterpriseAddEnterpriseDto.getPhone(), individualBusinessEnterpriseAddEnterpriseDto.getName(),
                individualBusinessEnterpriseAddEnterpriseDto.getIdcardNo(), individualBusinessEnterpriseAddEnterpriseDto.getIdcardPic(), individualBusinessEnterpriseAddEnterpriseDto.getIdcardPicBack(),
                individualBusinessEnterpriseAddEnterpriseDto.getIdcardHand(), individualBusinessEnterpriseAddEnterpriseDto.getIdcardBackHand(), enterpriseId);

        //判断税种
        if (BizType.TAXPAYER.equals(individualBusinessEnterpriseAddEnterpriseDto.getBizType())) {
            return R.fail("个体户税种不存在一般纳税人");
        }

        //新建个体户
        IndividualEnterpriseEntity individualEnterpriseEntity = new IndividualEnterpriseEntity();
        individualEnterpriseEntity.setCandidatedNames(individualBusinessEnterpriseAddEnterpriseDto.getCandidatedNames());
        individualEnterpriseEntity.setMainIndustry(individualBusinessEnterpriseAddEnterpriseDto.getMainIndustry());
        individualEnterpriseEntity.setBizScope(individualBusinessEnterpriseAddEnterpriseDto.getBizScope());
        individualEnterpriseEntity.setBizType(individualBusinessEnterpriseAddEnterpriseDto.getBizType());
        individualEnterpriseEntity.setRegisteredMoney(individualBusinessEnterpriseAddEnterpriseDto.getRegisteredMoney());
        individualEnterpriseEntity.setMakerId(makerEntity.getId());
        individualEnterpriseEntity.setContactName(individualBusinessEnterpriseAddEnterpriseDto.getContactName());
        individualEnterpriseEntity.setContactPhone(individualBusinessEnterpriseAddEnterpriseDto.getContactPhone());
        individualEnterpriseEntity.setIbstate(Ibstate.REGISTERING);
        save(individualEnterpriseEntity);

        return R.success("个独新增成功");
    }

}
