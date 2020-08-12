package com.lgyun.system.user.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.EnterpriseReportEntity;
import com.lgyun.system.user.mapper.EnterpriseReportMapper;
import com.lgyun.system.user.service.IEnterpriseReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 年度申报管理表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-12 14:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class EnterpriseReportServiceImpl extends BaseServiceImpl<EnterpriseReportMapper, EnterpriseReportEntity> implements IEnterpriseReportService {

}
