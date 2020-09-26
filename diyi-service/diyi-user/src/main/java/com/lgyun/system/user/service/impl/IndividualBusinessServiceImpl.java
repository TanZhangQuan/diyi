package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.feign.IOrderClient;
import com.lgyun.system.order.vo.SelfHelpInvoiceStatisticsVO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseWebAddDto;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.mapper.IndividualBusinessMapper;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.service.IIndividualBusinessService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.vo.EnterpriseReportsVO;
import com.lgyun.system.user.vo.IndividualBusinessEnterpriseDetailsVO;
import com.lgyun.system.user.vo.IndividualBusinessEnterpriseListByMakerVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-07-02 17:44:02
 */
@Service
@AllArgsConstructor
public class IndividualBusinessServiceImpl extends BaseServiceImpl<IndividualBusinessMapper, IndividualBusinessEntity> implements IIndividualBusinessService {

    private IOrderClient orderClient;
    private IMakerService makerService;
    private IEnterpriseReportService enterpriseReportService;

    @Override
    public R<String> save(IndividualBusinessEnterpriseAddDto individualBusinessEnterpriseAddDto, MakerEntity makerEntity) {

        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经活体认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus()))) {
            return R.fail("请先进行活体认证");
        }

        //判断税种
        if (BizType.TAXPAYER.equals(individualBusinessEnterpriseAddDto.getBizType())) {
            return R.fail("个体户税种不存在一般纳税人");
        }

        IndividualBusinessEntity individualBusinessEntity = new IndividualBusinessEntity();
        BeanUtils.copyProperties(individualBusinessEnterpriseAddDto, individualBusinessEntity);
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
    public R<IPage<IndividualBusinessEnterpriseListByMakerVO>> listByMaker(Query query, Long makerId, Ibstate ibstate) {

        QueryWrapper<IndividualBusinessEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualBusinessEntity::getMakerId, makerId)
                .eq(ibstate != null, IndividualBusinessEntity::getIbstate, ibstate)
                .orderByDesc(IndividualBusinessEntity::getCreateTime);

        IPage<IndividualBusinessEntity> pages = this.page(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);

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
    public R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long individualBusinessId, InvoicePeopleType invoicePeopleType) {
        return orderClient.yearMonthMoney(individualBusinessId, invoicePeopleType);
    }

    @Override
    public R<IPage<IndividualBusinessEnterpriseDetailsVO>> getIndividualBusinessList(IPage<IndividualBusinessEnterpriseDetailsVO> page, Long enterpriseId, Long serviceProviderId, IndividualBusinessEnterpriseDto individualBusinessEnterpriseDto) {

        if (individualBusinessEnterpriseDto.getBeginDate() != null && individualBusinessEnterpriseDto.getEndDate() != null) {
            if (individualBusinessEnterpriseDto.getBeginDate().after(individualBusinessEnterpriseDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getIndividualBusinessList(enterpriseId, serviceProviderId, individualBusinessEnterpriseDto, page)));
    }

    @Override
    public R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long individualBusinessId, InvoicePeopleType invoicePeopleType) {
        return orderClient.selfHelpInvoiceStatistics(individualBusinessId, invoicePeopleType);
    }

    @Override
    public R selfHelpInvoiceList(Query query, Long individualBusinessId, InvoicePeopleType invoicePeopleType) {
        return orderClient.selfHelpInvoiceList(query.getCurrent(), query.getSize(), individualBusinessId, invoicePeopleType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> save(IndividualBusinessEnterpriseWebAddDto individualBusinessEnterpriseWebAddDto, Long enterpriseId) {
        //新建创客
        MakerEntity makerEntity = makerService.makerSave(individualBusinessEnterpriseWebAddDto.getPhone(), individualBusinessEnterpriseWebAddDto.getName(),
                individualBusinessEnterpriseWebAddDto.getIdcardNo(), individualBusinessEnterpriseWebAddDto.getIdcardPic(), individualBusinessEnterpriseWebAddDto.getIdcardPicBack(),
                individualBusinessEnterpriseWebAddDto.getIdcardHand(), individualBusinessEnterpriseWebAddDto.getIdcardBackHand(), enterpriseId);

        //判断税种
        if (BizType.TAXPAYER.equals(individualBusinessEnterpriseWebAddDto.getBizType())) {
            return R.fail("个体户税种不存在一般纳税人");
        }

        //新建个体户
        IndividualBusinessEntity individualBusinessEntity = new IndividualBusinessEntity();
        individualBusinessEntity.setCandidatedNames(individualBusinessEnterpriseWebAddDto.getCandidatedNames());
        individualBusinessEntity.setMainIndustry(individualBusinessEnterpriseWebAddDto.getMainIndustry());
        individualBusinessEntity.setBizScope(individualBusinessEnterpriseWebAddDto.getBizScope());
        individualBusinessEntity.setBizType(individualBusinessEnterpriseWebAddDto.getBizType());
        individualBusinessEntity.setRegisteredMoney(individualBusinessEnterpriseWebAddDto.getRegisteredMoney());
        individualBusinessEntity.setMakerId(makerEntity.getId());
        individualBusinessEntity.setContactName(individualBusinessEnterpriseWebAddDto.getContactName());
        individualBusinessEntity.setContactPhone(individualBusinessEnterpriseWebAddDto.getContactPhone());
        save(individualBusinessEntity);

        return R.success("个体户新增成功");
    }

    @Override
    public IndividualBusinessEntity findByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo) {
        QueryWrapper<IndividualBusinessEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualBusinessEntity::getMakerId, makerId)
                .eq(IndividualBusinessEntity::getIbtaxNo, ibtaxNo);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IndividualBusinessEntity findByIbtaxNo(String ibtaxNo) {
        QueryWrapper<IndividualBusinessEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualBusinessEntity::getIbtaxNo, ibtaxNo);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<IPage<EnterpriseReportsVO>> queryEnterpriseReports(Query query, Long individualBusinessId) {
        return enterpriseReportService.findByBodyTypeAndBodyId(query, BodyType.INDIVIDUALBUSINESS, individualBusinessId);
    }

    @Override
    public R<String> updateIbstate(Long serviceProviderId, Long individualBusinessId, Ibstate ibstate) {

        IndividualBusinessEntity individualBusinessEntity = getById(individualBusinessId);
        if (individualBusinessEntity == null) {
            return R.fail("个体户不存在");
        }

        if (!(serviceProviderId.equals(individualBusinessEntity.getServiceProviderId()))) {
            return R.fail("个体户不属于当前服务商");
        }

        if (!(Ibstate.CANCELLED.equals(individualBusinessEntity.getIbstate()))) {
            individualBusinessEntity.setIbstate(Ibstate.CANCELLED);
            updateById(individualBusinessEntity);
        }

        return R.success("操作成功");
    }

}
