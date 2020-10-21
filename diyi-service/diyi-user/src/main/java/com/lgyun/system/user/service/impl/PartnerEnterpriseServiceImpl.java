package com.lgyun.system.user.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.PartnerEnterpriseEntity;
import com.lgyun.system.user.mapper.PartnerEnterpriseMapper;
import com.lgyun.system.user.service.IPartnerEnterpriseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 合伙人-商户关联表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-10-20 18:25:03
 */
@Slf4j
@Service
@AllArgsConstructor
public class PartnerEnterpriseServiceImpl extends BaseServiceImpl<PartnerEnterpriseMapper, PartnerEnterpriseEntity> implements IPartnerEnterpriseService {

}
