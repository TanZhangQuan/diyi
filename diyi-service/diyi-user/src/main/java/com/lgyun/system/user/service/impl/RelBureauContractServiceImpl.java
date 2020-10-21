package com.lgyun.system.user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.user.mapper.RelBureauContractMapper;
import com.lgyun.system.user.entity.RelBureauContractEntity;
import com.lgyun.system.user.service.IRelBureauContractService;

/**
 * 相关局合作协议表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-10-20 18:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelBureauContractServiceImpl extends BaseServiceImpl<RelBureauContractMapper, RelBureauContractEntity> implements IRelBureauContractService {

}
