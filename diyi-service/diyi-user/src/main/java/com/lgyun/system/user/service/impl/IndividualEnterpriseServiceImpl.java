package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.common.enumeration.VerifyStatus;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddMakerDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddOrUpdateDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseListDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseUpdateServiceProviderDTO;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.mapper.IndividualEnterpriseMapper;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.vo.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-07-02 17:44:02
 */
@Service
@AllArgsConstructor
public class IndividualEnterpriseServiceImpl extends BaseServiceImpl<IndividualEnterpriseMapper, IndividualEnterpriseEntity> implements IIndividualEnterpriseService {

    private IMakerService makerService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IServiceProviderService serviceProviderService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createIndividualEnterpriseMaker(IndividualBusinessEnterpriseAddMakerDTO individualBusinessEnterpriseAddMakerDto, MakerEntity makerEntity) {

        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("创客未身份证认证");
        }

        IndividualEnterpriseEntity individualEnterpriseEntity = new IndividualEnterpriseEntity();
        BeanUtils.copyProperties(individualBusinessEnterpriseAddMakerDto, individualEnterpriseEntity);
        individualEnterpriseEntity.setMakerId(makerEntity.getId());
        save(individualEnterpriseEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public int queryIndividualEnterpriseNumByMakerId(Long makerId) {
        return count(Wrappers.<IndividualEnterpriseEntity>query().lambda().eq(IndividualEnterpriseEntity::getMakerId, makerId));
    }

    @Override
    public R<IPage<IndividualBusinessEnterpriseListMakerVO>> queryIndividualEnterpriseListMaker(Long partnerId, Ibstate ibstate, Long makerId, IPage<IndividualBusinessEnterpriseListMakerVO> page) {
        return R.data(page.setRecords(baseMapper.queryIndividualEnterpriseListMaker(partnerId, ibstate, makerId, page)));
    }

    @Override
    public R<IndividualBusinessEnterpriseDetailMakerVO> queryIndividualEnterpriseDetailMaker(Long individualEnterpriseId) {
        return R.data(baseMapper.queryIndividualEnterpriseDetailMaker(individualEnterpriseId));
    }

    @Override
    public R<IPage<IndividualBusinessEnterpriseListVO>> queryIndividualEnterpriseList(IPage<IndividualBusinessEnterpriseListVO> page, Long enterpriseId, Long serviceProviderId, IndividualBusinessEnterpriseListDTO individualBusinessEnterpriseListDto) {

        if (individualBusinessEnterpriseListDto.getBeginDate() != null && individualBusinessEnterpriseListDto.getEndDate() != null) {
            if (individualBusinessEnterpriseListDto.getBeginDate().after(individualBusinessEnterpriseListDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.queryIndividualEnterpriseList(enterpriseId, serviceProviderId, individualBusinessEnterpriseListDto, page)));
    }

    @Override
    public R<IndividualBusinessEnterpriseDetailVO> queryIndividualEnterpriseDetail(Long individualEnterpriseId) {
        return R.data(baseMapper.queryIndividualEnterpriseDetail(individualEnterpriseId));
    }

    @Override
    public R<IndividualBusinessEnterpriseUpdateDetailVO> queryUpdateIndividualEnterpriseDetail(Long individualEnterpriseId) {
        return R.data(baseMapper.queryUpdateIndividualEnterpriseDetail(individualEnterpriseId));
    }

    @Override
    public R<IndividualBusinessEnterpriseUpdateDetailServiceProviderVO> queryUpdateIndividualEnterpriseDetailServiceProvider(Long individualEnterpriseId) {
        return R.data(baseMapper.queryUpdateIndividualEnterpriseDetailServiceProvider(individualEnterpriseId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> addOrUpdateIndividualEnterprise(IndividualBusinessEnterpriseAddOrUpdateDTO individualBusinessEnterpriseAddOrUpdateDto, Long enterpriseId) {

        MakerEntity makerEntity = makerService.getById(individualBusinessEnterpriseAddOrUpdateDto.getMakerId());
        if (makerEntity == null) {
            return R.fail("创客不存在");
        }

        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("创客未身份证认证");
        }

        if (enterpriseId != null) {
            int relevanceNum = makerEnterpriseService.queryMakerEnterpriseNum(enterpriseId, individualBusinessEnterpriseAddOrUpdateDto.getMakerId(), RelationshipType.RELEVANCE);
            if (relevanceNum <= 0) {
                return R.fail("创客商户不存在关联关系");
            }
        }

        IndividualEnterpriseEntity individualEnterpriseEntity;
        if (individualBusinessEnterpriseAddOrUpdateDto.getIndividualId() == null) {
            //新建个独
            individualEnterpriseEntity = new IndividualEnterpriseEntity();

        } else {
            individualEnterpriseEntity = getById(individualBusinessEnterpriseAddOrUpdateDto.getIndividualId());
            if (individualEnterpriseEntity == null) {
                return R.fail("个独不存在");
            }

            if (Ibstate.OPERATING.equals(individualEnterpriseEntity.getIbstate())) {
                return R.fail("运营中个独不可编辑");
            }

        }

        BeanUtils.copyProperties(individualBusinessEnterpriseAddOrUpdateDto, individualEnterpriseEntity);
        saveOrUpdate(individualEnterpriseEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public IndividualEnterpriseEntity findByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo) {
        QueryWrapper<IndividualEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualEnterpriseEntity::getMakerId, makerId)
                .eq(IndividualEnterpriseEntity::getIbtaxNo, ibtaxNo);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IndividualEnterpriseEntity queryIndividualEnterpriseByIbtaxNo(String ibtaxNo) {
        QueryWrapper<IndividualEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualEnterpriseEntity::getIbtaxNo, ibtaxNo);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> cancelIndividualEnterprise(Long serviceProviderId, Long individualEnterpriseId) {

        IndividualEnterpriseEntity individualEnterpriseEntity = getById(individualEnterpriseId);
        if (individualEnterpriseEntity == null) {
            return R.fail("个独不存在");
        }

        if (!(serviceProviderId.equals(individualEnterpriseEntity.getServiceProviderId()))) {
            return R.fail("个独不属于当前服务商");
        }

        individualEnterpriseEntity.setIbstate(Ibstate.CANCELLED);
        individualEnterpriseEntity.setLogoutDateTime(new Date());
        updateById(individualEnterpriseEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateIndividualEnterpriseServiceProvider(IndividualBusinessEnterpriseUpdateServiceProviderDTO individualBusinessEnterpriseUpdateServiceProviderDTO, Long serviceProviderId) {

        IndividualEnterpriseEntity individualEnterpriseEntity = getById(individualBusinessEnterpriseUpdateServiceProviderDTO.getIndividualId());
        if (individualEnterpriseEntity == null) {
            return R.fail("个独不存在");
        }

        if (!(serviceProviderId.equals(individualEnterpriseEntity.getServiceProviderId()))) {
            return R.fail("个独不属于当前服务商");
        }

        if (Ibstate.OPERATING.equals(individualEnterpriseEntity.getIbstate())) {
            return R.fail("运营中个独不可编辑");
        }

        if (StringUtils.isNotBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getIbname())) {
            int ibnameNum = count(Wrappers.<IndividualEnterpriseEntity>query().lambda()
                    .eq(IndividualEnterpriseEntity::getIbname, individualBusinessEnterpriseUpdateServiceProviderDTO.getIbname())
                    .ne(IndividualEnterpriseEntity::getId, individualBusinessEnterpriseUpdateServiceProviderDTO.getIndividualId()));
            if (ibnameNum > 0) {
                return R.fail("个独名称已存在");
            }
        }

        if (StringUtils.isNotBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getIbtaxNo())) {
            int ibtaxNoNum = count(Wrappers.<IndividualEnterpriseEntity>query().lambda()
                    .eq(IndividualEnterpriseEntity::getIbtaxNo, individualBusinessEnterpriseUpdateServiceProviderDTO.getIbtaxNo())
                    .ne(IndividualEnterpriseEntity::getId, individualBusinessEnterpriseUpdateServiceProviderDTO.getIndividualId()));
            if (ibtaxNoNum > 0) {
                return R.fail("个独统一社会信用代码已存在");
            }
        }

        if (Ibstate.OPERATING.equals(individualBusinessEnterpriseUpdateServiceProviderDTO.getIbstate())) {
            if (StringUtils.isBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getIbname())) {
                return R.fail("请输入个体户名称");
            }

            if (StringUtils.isBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getIbtaxNo())) {
                return R.fail("请输入统一社会信用代码");
            }

            if (individualBusinessEnterpriseUpdateServiceProviderDTO.getBuildDateTime() == null) {
                return R.fail("请选择营业执照的注册日期");
            }

            if (StringUtils.isBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getBizPark())) {
                return R.fail("请输入园区");
            }

            if (StringUtils.isBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getProvince())) {
                return R.fail("请选择省/直辖市");
            }

            if (StringUtils.isBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getCity())) {
                return R.fail("请选择市");
            }

            if (StringUtils.isBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getArea())) {
                return R.fail("请选择区/县");
            }

            if (StringUtils.isBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getBusinessAddress())) {
                return R.fail("请输入经营场所");
            }

            if (StringUtils.isBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getMainIndustry())) {
                return R.fail("请选择行业类型");
            }

            if (StringUtils.isBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getBizScope())) {
                return R.fail("请选择经营范围");
            }

            if (StringUtils.isBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getNetBusinessAddress())) {
                return R.fail("请输入网络经营场所");
            }

            if (StringUtils.isBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getBusinessLicenceUrl())) {
                return R.fail("请上传营业执照正本");
            }

            if (StringUtils.isBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getBusinessLicenceCopyUrl())) {
                return R.fail("请上传营业执照副本");
            }

            if (individualBusinessEnterpriseUpdateServiceProviderDTO.getSubmitDateTime() == null) {
                return R.fail("请选择提交日期");
            }

            if (individualBusinessEnterpriseUpdateServiceProviderDTO.getRegisteredDate() == null) {
                return R.fail("请选择注册日期");
            }

            if (individualBusinessEnterpriseUpdateServiceProviderDTO.getTaxRegisterDateTime() == null) {
                return R.fail("请选择税务登记日期");
            }

            if (individualBusinessEnterpriseUpdateServiceProviderDTO.getBuildDateTime() == null) {
                return R.fail("请选择营业执照的注册日期");
            }

            BigDecimal serviceRate = individualBusinessEnterpriseUpdateServiceProviderDTO.getServiceRate();
            if (serviceRate == null) {
                return R.fail("请输入服务费率");
            } else if (serviceRate.compareTo(BigDecimal.ZERO) < 0) {
                return R.fail("服务费率不能小于0");
            } else if (serviceRate.compareTo(BigDecimal.valueOf(100)) > 0) {
                return R.fail("服务费率不能大于100");
            }

        }

        BeanUtils.copyProperties(individualBusinessEnterpriseUpdateServiceProviderDTO, individualEnterpriseEntity);
        updateById(individualEnterpriseEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> mateServiceProvider(Long serviceProviderId, Long individualEnterpriseId) {

        int serviceProviderNum = serviceProviderService.queryCountById(serviceProviderId);
        if (serviceProviderNum <= 0) {
            return R.fail("服务商不存在");
        }

        IndividualEnterpriseEntity individualEnterpriseEntity = getById(individualEnterpriseId);
        if (individualEnterpriseEntity == null) {
            return R.fail("个独不存在");
        }

        if (!(Ibstate.EDITING.equals(individualEnterpriseEntity.getIbstate()))) {
            return R.fail("非编辑状态个独，不可匹配服务商");
        }

        individualEnterpriseEntity.setServiceProviderId(serviceProviderId);
        updateById(individualEnterpriseEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

}
