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
import com.lgyun.system.user.dto.EnterpriseIndividualBusinessEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDto;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.mapper.IndividualBusinessMapper;
import com.lgyun.system.user.service.IIndividualBusinessService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.vo.IndividualBusinessEnterpriseDetailsVO;
import com.lgyun.system.user.vo.IndividualBusinessEnterpriseListByMakerVO;
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

        //判断税种
        if (BizType.TAXPAYER.equals(individualBusinessEnterpriseAddDto.getBizType())) {
            return R.fail("个体户税种不存在一般纳税人");
        }

        IndividualBusinessEntity individualBusinessEntity = new IndividualBusinessEntity();
        BeanUtils.copyProperties(individualBusinessEnterpriseAddDto, individualBusinessEntity);
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
    public IndividualBusinessEntity findIBName(String ibname) {
        QueryWrapper<IndividualBusinessEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualBusinessEntity::getIbname, ibname);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IndividualBusinessEntity findIBTaxNo(String ibtaxNo) {
        QueryWrapper<IndividualBusinessEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualBusinessEntity::getIbtaxNo, ibtaxNo);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<IPage<IndividualBusinessEnterpriseListByMakerVO>> listByMaker(Integer current, Integer size, Long makerId, Ibstate ibstate) {

        QueryWrapper<IndividualBusinessEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualBusinessEntity::getMakerId, makerId)
                .eq(ibstate != null, IndividualBusinessEntity::getIbstate, ibstate)
                .orderByDesc(IndividualBusinessEntity::getCreateTime);

        IPage<IndividualBusinessEntity> pages = this.page(new Page<>(current, size), queryWrapper);

        List<IndividualBusinessEnterpriseListByMakerVO> records = pages.getRecords().stream().map(individualBusinessEntity -> BeanUtil.copy(individualBusinessEntity, IndividualBusinessEnterpriseListByMakerVO.class)).collect(Collectors.toList());

        IPage<IndividualBusinessEnterpriseListByMakerVO> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
        pageVo.setRecords(records);

        return R.data(pageVo);
    }

    @Override
    public R<IndividualBusinessEnterpriseDetailsVO> findById(Long individualBusinessId) {
        return R.data(baseMapper.findById(individualBusinessId));
    }

    @Override
    public R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long individualBusinessId, MakerType makerType) {
        return orderClient.yearMonthMoney(individualBusinessId, makerType);
    }

    @Override
    public R<IPage<IndividualBusinessEnterpriseDetailsVO>> getByDtoEnterprise(IPage<IndividualBusinessEnterpriseDetailsVO> page, Long enterpriseId, Ibstate ibstate, EnterpriseIndividualBusinessEnterpriseDto enterpriseIndividualBusinessEnterpriseDto) {

        if (enterpriseIndividualBusinessEnterpriseDto.getBeginDate() != null && enterpriseIndividualBusinessEnterpriseDto.getEndDate() != null) {
            if (enterpriseIndividualBusinessEnterpriseDto.getBeginDate().after(enterpriseIndividualBusinessEnterpriseDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getByDtoEnterprise(enterpriseId, ibstate, enterpriseIndividualBusinessEnterpriseDto, page)));
    }

    @Override
    public R<IndividualBusinessEnterpriseDetailsVO> findByIdEnterprise(Long individualBusinessId) {
        return R.data(baseMapper.findByIdEnterprise(individualBusinessId));
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
        IndividualBusinessEntity individualBusinessEntity = new IndividualBusinessEntity();
        individualBusinessEntity.setCandidatedNames(individualBusinessEnterpriseAddEnterpriseDto.getCandidatedNames());
        individualBusinessEntity.setMainIndustry(individualBusinessEnterpriseAddEnterpriseDto.getMainIndustry());
        individualBusinessEntity.setBizScope(individualBusinessEnterpriseAddEnterpriseDto.getBizScope());
        individualBusinessEntity.setBizType(individualBusinessEnterpriseAddEnterpriseDto.getBizType());
        individualBusinessEntity.setRegisteredMoney(individualBusinessEnterpriseAddEnterpriseDto.getRegisteredMoney());
        individualBusinessEntity.setMakerId(makerEntity.getId());
        individualBusinessEntity.setContactName(individualBusinessEnterpriseAddEnterpriseDto.getContactName());
        individualBusinessEntity.setContactPhone(individualBusinessEnterpriseAddEnterpriseDto.getContactPhone());
        individualBusinessEntity.setIbstate(Ibstate.REGISTERING);
        save(individualBusinessEntity);

        return R.success("个体户新增成功");
    }

}
