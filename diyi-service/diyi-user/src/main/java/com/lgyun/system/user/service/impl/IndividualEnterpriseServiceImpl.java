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
import com.lgyun.system.order.vo.SelfHelpInvoiceListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceStatisticsVO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseWebAddDto;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.mapper.IndividualEnterpriseMapper;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.vo.EnterpriseReportsVO;
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
public class IndividualEnterpriseServiceImpl extends BaseServiceImpl<IndividualEnterpriseMapper, IndividualEnterpriseEntity> implements IIndividualEnterpriseService {

    private IOrderClient orderClient;
    private IMakerService makerService;
    private IEnterpriseReportService enterpriseReportService;

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
    public R<IPage<IndividualBusinessEnterpriseListByMakerVO>> listByMaker(Query query, Long makerId, Ibstate ibstate) {

        QueryWrapper<IndividualEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualEnterpriseEntity::getMakerId, makerId)
                .eq(ibstate != null, IndividualEnterpriseEntity::getIbstate, ibstate)
                .orderByDesc(IndividualEnterpriseEntity::getCreateTime);

        IPage<IndividualEnterpriseEntity> pages = this.page(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);

        List<IndividualBusinessEnterpriseListByMakerVO> records = pages.getRecords().stream().map(individualEnterpriseEntity -> BeanUtil.copy(individualEnterpriseEntity, IndividualBusinessEnterpriseListByMakerVO.class)).collect(Collectors.toList());

        IPage<IndividualBusinessEnterpriseListByMakerVO> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
        pageVo.setRecords(records);

        return R.data(pageVo);
    }

    @Override
    public R<IndividualBusinessEnterpriseDetailsVO> findById(Long individualEnterpriseId) {
        return R.data(baseMapper.findById(individualEnterpriseId));
    }

    @Override
    public R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long individualEnterpriseId, InvoicePeopleType invoicePeopleType) {
        return orderClient.yearMonthMoney(individualEnterpriseId, invoicePeopleType);
    }

    @Override
    public R<IPage<IndividualBusinessEnterpriseDetailsVO>> getByDtoEnterprise(IPage<IndividualBusinessEnterpriseDetailsVO> page, Long enterpriseId, Ibstate ibstate, IndividualBusinessEnterpriseDto individualBusinessEnterpriseDto) {
        return R.data(page.setRecords(baseMapper.getByDtoEnterprise(enterpriseId, ibstate, individualBusinessEnterpriseDto, page)));
    }

    @Override
    public R<IndividualBusinessEnterpriseDetailsVO> findByIdEnterprise(Long individualEnterpriseId) {
        return R.data(baseMapper.findByIdEnterprise(individualEnterpriseId));
    }

    @Override
    public R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long individualEnterpriseId, InvoicePeopleType invoicePeopleType) {
        return orderClient.selfHelpInvoiceStatistics(individualEnterpriseId, invoicePeopleType);
    }

    @Override
    public R<IPage<SelfHelpInvoiceListVO>> selfHelpInvoiceList(Query query, Long individualEnterpriseId, InvoicePeopleType invoicePeopleType) {
        return orderClient.selfHelpInvoiceList(query.getCurrent(), query.getSize(), individualEnterpriseId, invoicePeopleType);
    }

    @Override
    public R<String> saveByEnterprise(IndividualBusinessEnterpriseWebAddDto individualBusinessEnterpriseWebAddDto, Long enterpriseId) {
        //新建创客
        MakerEntity makerEntity = makerService.makerSave(individualBusinessEnterpriseWebAddDto.getPhone(), individualBusinessEnterpriseWebAddDto.getName(),
                individualBusinessEnterpriseWebAddDto.getIdcardNo(), individualBusinessEnterpriseWebAddDto.getIdcardPic(), individualBusinessEnterpriseWebAddDto.getIdcardPicBack(),
                individualBusinessEnterpriseWebAddDto.getIdcardHand(), individualBusinessEnterpriseWebAddDto.getIdcardBackHand(), enterpriseId);

        //判断税种
        if (BizType.TAXPAYER.equals(individualBusinessEnterpriseWebAddDto.getBizType())) {
            return R.fail("个体户税种不存在一般纳税人");
        }

        //新建个体户
        IndividualEnterpriseEntity individualEnterpriseEntity = new IndividualEnterpriseEntity();
        individualEnterpriseEntity.setCandidatedNames(individualBusinessEnterpriseWebAddDto.getCandidatedNames());
        individualEnterpriseEntity.setMainIndustry(individualBusinessEnterpriseWebAddDto.getMainIndustry());
        individualEnterpriseEntity.setBizScope(individualBusinessEnterpriseWebAddDto.getBizScope());
        individualEnterpriseEntity.setBizType(individualBusinessEnterpriseWebAddDto.getBizType());
        individualEnterpriseEntity.setRegisteredMoney(individualBusinessEnterpriseWebAddDto.getRegisteredMoney());
        individualEnterpriseEntity.setMakerId(makerEntity.getId());
        individualEnterpriseEntity.setContactName(individualBusinessEnterpriseWebAddDto.getContactName());
        individualEnterpriseEntity.setContactPhone(individualBusinessEnterpriseWebAddDto.getContactPhone());
        save(individualEnterpriseEntity);

        return R.success("个独新增成功");
    }

    @Override
    public IndividualEnterpriseEntity findByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo) {
        QueryWrapper<IndividualEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualEnterpriseEntity::getMakerId, makerId)
                .eq(IndividualEnterpriseEntity::getIbtaxNo, ibtaxNo);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IndividualEnterpriseEntity findByIbtaxNo(String ibtaxNo) {
        QueryWrapper<IndividualEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualEnterpriseEntity::getIbtaxNo, ibtaxNo);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<IPage<EnterpriseReportsVO>> queryEnterpriseReports(Query query, Long individualBusinessId) {
        return enterpriseReportService.findByBodyTypeAndBodyId(query, BodyType.INDIVIDUALBUSINESS, individualBusinessId);
    }

}
