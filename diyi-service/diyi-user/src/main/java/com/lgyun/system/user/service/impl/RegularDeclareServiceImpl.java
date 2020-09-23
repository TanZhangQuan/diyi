package com.lgyun.system.user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.user.mapper.RegularDeclareMapper;
import com.lgyun.system.user.entity.RegularDeclareEntity;
import com.lgyun.system.user.service.IRegularDeclareService;

/**
 * 申报表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-09-22 15:46:31
 */
@Slf4j
@Service
@AllArgsConstructor
public class RegularDeclareServiceImpl extends BaseServiceImpl<RegularDeclareMapper, RegularDeclareEntity> implements IRegularDeclareService {

}
