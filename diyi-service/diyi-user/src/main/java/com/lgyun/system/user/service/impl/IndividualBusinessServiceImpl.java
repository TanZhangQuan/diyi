package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BizType;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.common.enumeration.VerifyStatus;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddMakerDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddOrUpdateDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseListDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseUpdateServiceProviderDTO;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.mapper.IndividualBusinessMapper;
import com.lgyun.system.user.service.IIndividualBusinessService;
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
public class IndividualBusinessServiceImpl extends BaseServiceImpl<IndividualBusinessMapper, IndividualBusinessEntity> implements IIndividualBusinessService {

    private IMakerService makerService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IServiceProviderService serviceProviderService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createIndividualBusinessMaker(IndividualBusinessEnterpriseAddMakerDTO individualBusinessEnterpriseAddMakerDto, MakerEntity makerEntity) {

        //判断税种
        if (BizType.TAXPAYER.equals(individualBusinessEnterpriseAddMakerDto.getBizType())) {
            return R.fail("个体户税种不存在一般纳税人");
        }

        //查看创客是否认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("创客未身份证认证");
        }

        IndividualBusinessEntity individualBusinessEntity = new IndividualBusinessEntity();
        BeanUtils.copyProperties(individualBusinessEnterpriseAddMakerDto, individualBusinessEntity);
        individualBusinessEntity.setMakerId(makerEntity.getId());
        save(individualBusinessEntity);

        return R.success("创建个体户成功");
    }

    @Override
    public int queryIndividualBusinessNumByMakerId(Long makerId) {
        return count(Wrappers.<IndividualBusinessEntity>query().lambda().eq(IndividualBusinessEntity::getMakerId, makerId));
    }

    @Override
    public R<IPage<IndividualBusinessEnterpriseListMakerVO>> queryIndividualBusinessListMaker(Long makerId, Long partnerId, Ibstate ibstate, IPage<IndividualBusinessEnterpriseListMakerVO> page) {
        return R.data(page.setRecords(baseMapper.queryIndividualBusinessListMaker(makerId, partnerId, ibstate, page)));
    }

    @Override
    public R<IndividualBusinessEnterpriseDetailMakerVO> queryIndividualBusinessDetailMaker(Long individualBusinessId) {
        return R.data(baseMapper.queryIndividualBusinessDetailMaker(individualBusinessId));
    }

    @Override
    public R<IPage<IndividualBusinessEnterpriseListVO>> queryIndividualBusinessList(Long enterpriseId, Long serviceProviderId, IndividualBusinessEnterpriseListDTO individualBusinessEnterpriseListDto, IPage<IndividualBusinessEnterpriseListVO> page) {

        if (individualBusinessEnterpriseListDto.getBeginDate() != null && individualBusinessEnterpriseListDto.getEndDate() != null) {
            if (individualBusinessEnterpriseListDto.getBeginDate().after(individualBusinessEnterpriseListDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.queryIndividualBusinessList(enterpriseId, serviceProviderId, individualBusinessEnterpriseListDto, page)));
    }

    @Override
    public R<IndividualBusinessEnterpriseDetailVO> queryIndividualBusinessDetail(Long individualBusinessId) {
        return R.data(baseMapper.queryIndividualBusinessDetail(individualBusinessId));
    }

    @Override
    public R<IndividualBusinessEnterpriseUpdateDetailVO> queryUpdateIndividualBusinessDetail(Long individualBusinessId) {
        return R.data(baseMapper.queryUpdateIndividualBusinessDetail(individualBusinessId));
    }

    @Override
    public R<IndividualBusinessEnterpriseUpdateDetailServiceProviderVO> queryUpdateIndividualBusinessDetailServiceProvider(Long individualBusinessId) {
        return R.data(baseMapper.queryUpdateIndividualBusinessDetailServiceProvider(individualBusinessId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> addOrUpdateIndividualBusiness(IndividualBusinessEnterpriseAddOrUpdateDTO individualBusinessEnterpriseAddOrUpdateDto, Long enterpriseId) {

        //判断税种
        if (BizType.TAXPAYER.equals(individualBusinessEnterpriseAddOrUpdateDto.getBizType())) {
            return R.fail("个体户税种不存在一般纳税人");
        }

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

        IndividualBusinessEntity individualBusinessEntity;
        if (individualBusinessEnterpriseAddOrUpdateDto.getIndividualId() == null) {
            //新建个体户
            individualBusinessEntity = new IndividualBusinessEntity();

        } else {
            individualBusinessEntity = getById(individualBusinessEnterpriseAddOrUpdateDto.getIndividualId());
            if (individualBusinessEntity == null) {
                return R.fail("个体户不存在");
            }

            if (Ibstate.OPERATING.equals(individualBusinessEntity.getIbstate())) {
                return R.fail("运营中个体户不可编辑");
            }

        }

        BeanUtils.copyProperties(individualBusinessEnterpriseAddOrUpdateDto, individualBusinessEntity);
        saveOrUpdate(individualBusinessEntity);

        return R.success("操作成功");

    }

    @Override
    public IndividualBusinessEntity findByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo) {
        QueryWrapper<IndividualBusinessEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualBusinessEntity::getMakerId, makerId)
                .eq(IndividualBusinessEntity::getIbtaxNo, ibtaxNo);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IndividualBusinessEntity queryIndividualBusinessByIbtaxNo(String ibtaxNo) {
        QueryWrapper<IndividualBusinessEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualBusinessEntity::getIbtaxNo, ibtaxNo);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public int queryCountById(Long individualBusinessId) {
        QueryWrapper<IndividualBusinessEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IndividualBusinessEntity::getId, individualBusinessId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> cancelIndividualBusiness(Long serviceProviderId, Long individualBusinessId) {

        IndividualBusinessEntity individualBusinessEntity = getById(individualBusinessId);
        if (individualBusinessEntity == null) {
            return R.fail("个体户不存在");
        }

        if (!(serviceProviderId.equals(individualBusinessEntity.getServiceProviderId()))) {
            return R.fail("个体户不属于当前服务商");
        }

        individualBusinessEntity.setIbstate(Ibstate.CANCELLED);
        individualBusinessEntity.setLogoutDateTime(new Date());
        updateById(individualBusinessEntity);

        return R.success("注销成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateIndividualBusinessServiceProvider(IndividualBusinessEnterpriseUpdateServiceProviderDTO individualBusinessEnterpriseUpdateServiceProviderDTO, Long serviceProviderId) {

        //判断税种
        if (BizType.TAXPAYER.equals(individualBusinessEnterpriseUpdateServiceProviderDTO.getBizType())) {
            return R.fail("个体户税种不存在一般纳税人");
        }

        IndividualBusinessEntity individualBusinessEntity = getById(individualBusinessEnterpriseUpdateServiceProviderDTO.getIndividualId());
        if (individualBusinessEntity == null) {
            return R.fail("个体户不存在");
        }

        if (!(serviceProviderId.equals(individualBusinessEntity.getServiceProviderId()))) {
            return R.fail("个体户不属于当前服务商");
        }

        if (Ibstate.OPERATING.equals(individualBusinessEntity.getIbstate())) {
            return R.fail("运营中个体户不可编辑");
        }

        if (StringUtils.isNotBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getIbname())) {
            int ibnameNum = count(Wrappers.<IndividualBusinessEntity>query().lambda()
                    .eq(IndividualBusinessEntity::getIbname, individualBusinessEnterpriseUpdateServiceProviderDTO.getIbname())
                    .ne(IndividualBusinessEntity::getId, individualBusinessEnterpriseUpdateServiceProviderDTO.getIndividualId()));
            if (ibnameNum > 0) {
                return R.fail("个体户名称已存在");
            }
        }

        if (StringUtils.isNotBlank(individualBusinessEnterpriseUpdateServiceProviderDTO.getIbtaxNo())) {
            int ibtaxNoNum = count(Wrappers.<IndividualBusinessEntity>query().lambda()
                    .eq(IndividualBusinessEntity::getIbtaxNo, individualBusinessEnterpriseUpdateServiceProviderDTO.getIbtaxNo())
                    .ne(IndividualBusinessEntity::getId, individualBusinessEnterpriseUpdateServiceProviderDTO.getIndividualId()));
            if (ibtaxNoNum > 0) {
                return R.fail("个体户统一社会信用代码已存在");
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

        BeanUtils.copyProperties(individualBusinessEnterpriseUpdateServiceProviderDTO, individualBusinessEntity);
        updateById(individualBusinessEntity);

        return R.success("编辑成功");

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> mateServiceProvider(Long serviceProviderId, Long individualBusinessId) {

        int serviceProviderNum = serviceProviderService.queryCountById(serviceProviderId);
        if (serviceProviderNum <= 0) {
            return R.fail("服务商不存在");
        }

        IndividualBusinessEntity individualBusinessEntity = getById(individualBusinessId);
        if (individualBusinessEntity == null) {
            return R.fail("个体户不存在");
        }

        if (!(Ibstate.EDITING.equals(individualBusinessEntity.getIbstate()))) {
            return R.fail("非编辑状态个体户，不可匹配服务商");
        }

        individualBusinessEntity.setServiceProviderId(serviceProviderId);
        updateById(individualBusinessEntity);

        return R.success("匹配服务商成功");
    }

}
