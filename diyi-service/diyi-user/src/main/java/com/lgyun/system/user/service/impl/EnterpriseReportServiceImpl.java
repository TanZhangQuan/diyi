package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.enumeration.ReportState;
import com.lgyun.common.enumeration.ReportTheme;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.AdminEnterpriseReportDTO;
import com.lgyun.system.user.entity.EnterpriseReportEntity;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.mapper.EnterpriseReportMapper;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.vo.EnterpriseReportsVO;
import com.lgyun.system.user.vo.AdminEnterpriseReportAllVO;
import com.lgyun.system.user.vo.AdminEnterpriseReportVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 年度申报管理表 Service 实现
 *
 * @author tzq
 * @since 2020-08-12 14:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class EnterpriseReportServiceImpl extends BaseServiceImpl<EnterpriseReportMapper, EnterpriseReportEntity> implements IEnterpriseReportService {

    private IServiceProviderService serviceProviderService;

    @Override
    public R<IPage<EnterpriseReportsVO>> findByBodyTypeAndBodyId(BodyType mainBodyType, Long mainBodyId, Query query) {

        QueryWrapper<EnterpriseReportEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseReportEntity::getMainBodyType, mainBodyType)
                .eq(EnterpriseReportEntity::getMainBodyId, mainBodyId)
                .orderByDesc(EnterpriseReportEntity::getCreateTime);

        IPage<EnterpriseReportEntity> pages = this.page(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);

        List<EnterpriseReportsVO> records = pages.getRecords().stream().map(enterpriseReportEntity -> {

                    EnterpriseReportsVO enterpriseReportsVO = BeanUtil.copy(enterpriseReportEntity, EnterpriseReportsVO.class);
                    ServiceProviderEntity serviceProviderEntity = serviceProviderService.getById(enterpriseReportEntity.getServiceProviderId());
                    if (serviceProviderEntity != null) {
                        enterpriseReportsVO.setServiceProviderName(serviceProviderEntity.getServiceProviderName());
                    }
                    return enterpriseReportsVO;

                }
        ).collect(Collectors.toList());

        IPage<EnterpriseReportsVO> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
        pageVo.setRecords(records);

        return R.data(pageVo);
    }

    @Override
    public R findAdminEnterpriseReportAll(String serviceProviderName, ReportTheme reportTheme, String startTime, String endTime, IPage<AdminEnterpriseReportAllVO> page) {
        return R.data(page.setRecords(baseMapper.findAdminEnterpriseReportAll(serviceProviderName, reportTheme, startTime, endTime, page)));
    }

    @Override
    public R findAdminEnterpriseReport(Long serviceProviderId, ReportTheme reportTheme, IPage<AdminEnterpriseReportVO> page) {
        return R.data(page.setRecords(baseMapper.findAdminEnterpriseReport(serviceProviderId, reportTheme, page)));
    }

    @Override
    public R findAdminEnterpriseReportDetail(Long enterpriseReportId) {
        return R.data(baseMapper.findAdminEnterpriseReportDetail(enterpriseReportId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R saveAdminEnterpriseReport(AdminEnterpriseReportDTO adminEnterpriseReportDTO) {
        EnterpriseReportEntity enterpriseReportEntity;
        if (null == adminEnterpriseReportDTO.getEnterpriseReportId()) {
            enterpriseReportEntity = BeanUtil.copy(adminEnterpriseReportDTO, EnterpriseReportEntity.class);
            enterpriseReportEntity.setReportState(ReportState.DECLARESUCCESS);
            save(enterpriseReportEntity);
        } else {
            enterpriseReportEntity = getById(adminEnterpriseReportDTO.getEnterpriseReportId());
            enterpriseReportEntity.setServiceProviderId(adminEnterpriseReportDTO.getServiceProviderId());
            enterpriseReportEntity.setMainBodyType(adminEnterpriseReportDTO.getMainBodyType());
            enterpriseReportEntity.setMainBodyId(adminEnterpriseReportDTO.getMainBodyId());
            enterpriseReportEntity.setReportTheme(adminEnterpriseReportDTO.getReportTheme());
            enterpriseReportEntity.setReportYear(adminEnterpriseReportDTO.getReportYear());
            enterpriseReportEntity.setReportMonth(adminEnterpriseReportDTO.getReportMonth());
            enterpriseReportEntity.setReportQuater(adminEnterpriseReportDTO.getReportQuater());
            enterpriseReportEntity.setReportDeadDate(adminEnterpriseReportDTO.getReportDeadDate());
            enterpriseReportEntity.setReportGuardName(adminEnterpriseReportDTO.getReportGuardName());
            enterpriseReportEntity.setReportResultFiles(adminEnterpriseReportDTO.getReportResultFiles());
            updateById(enterpriseReportEntity);
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R toExamineAdminEnterpriseReport(Long enterpriseReportId, Integer toExamine) {
        EnterpriseReportEntity byId = getById(enterpriseReportId);
        if (null == toExamine || (toExamine != 1 && toExamine != 2)) {
            return R.fail("审核失败");
        }
        if (toExamine == 1) {
            byId.setReportState(ReportState.DECLARESUCCESS);
        }
        if (toExamine == 2) {
            byId.setReportState(ReportState.DECLAREFAIL);
        }
        saveOrUpdate(byId);
        return R.success("审核成功");
    }

    @Override
    public R findServiceEnterpriseReport(Long serviceProviderId, ReportTheme reportTheme, String startTime, String endTime, IPage<AdminEnterpriseReportVO> page) {
        return R.data(page.setRecords(baseMapper.findServiceEnterpriseReport(serviceProviderId, reportTheme, startTime, endTime, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R saveServiceEnterpriseReport(AdminEnterpriseReportDTO adminEnterpriseReportDTO) {
        EnterpriseReportEntity enterpriseReportEntity;
        if (null == adminEnterpriseReportDTO.getEnterpriseReportId()) {
            enterpriseReportEntity = BeanUtil.copy(adminEnterpriseReportDTO, EnterpriseReportEntity.class);
            enterpriseReportEntity.setReportState(ReportState.DECLAREING);
            save(enterpriseReportEntity);
        } else {
            enterpriseReportEntity = getById(adminEnterpriseReportDTO.getEnterpriseReportId());
            enterpriseReportEntity.setServiceProviderId(adminEnterpriseReportDTO.getServiceProviderId());
            enterpriseReportEntity.setMainBodyType(adminEnterpriseReportDTO.getMainBodyType());
            enterpriseReportEntity.setMainBodyId(adminEnterpriseReportDTO.getMainBodyId());
            enterpriseReportEntity.setReportTheme(adminEnterpriseReportDTO.getReportTheme());
            enterpriseReportEntity.setReportYear(adminEnterpriseReportDTO.getReportYear());
            enterpriseReportEntity.setReportMonth(adminEnterpriseReportDTO.getReportMonth());
            enterpriseReportEntity.setReportQuater(adminEnterpriseReportDTO.getReportQuater());
            enterpriseReportEntity.setReportDeadDate(adminEnterpriseReportDTO.getReportDeadDate());
            enterpriseReportEntity.setReportGuardName(adminEnterpriseReportDTO.getReportGuardName());
            enterpriseReportEntity.setReportResultFiles(adminEnterpriseReportDTO.getReportResultFiles());
            saveOrUpdate(enterpriseReportEntity);
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }
}
