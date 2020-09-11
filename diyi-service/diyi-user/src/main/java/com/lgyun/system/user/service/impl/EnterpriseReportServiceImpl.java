package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.EnterpriseReportEntity;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.mapper.EnterpriseReportMapper;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.vo.EnterpriseReportsVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public R<IPage<EnterpriseReportsVO>> findByBodyTypeAndBodyId(Query query, BodyType mainBodyType, Long mainBodyId) {

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
    public String findReportResultFiles(BodyType mainBodyType, Long mainBodyId) {
        return baseMapper.findReportResultFiles(mainBodyType, mainBodyId);
    }
}
