package com.lgyun.system.user.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.PartnerEntity;
import com.lgyun.system.user.mapper.PartnerMapper;
import com.lgyun.system.user.service.IPartnerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 合伙人信息表 Service 实现
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
@Slf4j
@Service
@AllArgsConstructor
public class PartnerServiceImpl extends BaseServiceImpl<PartnerMapper, PartnerEntity> implements IPartnerService {

}