package com.lgyun.system.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.order.mapper.MakerTaxRecordMapper;
import com.lgyun.system.order.entity.MakerTaxRecordEntity;
import com.lgyun.system.order.service.IMakerTaxRecordService;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
@Slf4j
@Service
@AllArgsConstructor
public class MakerTaxRecordServiceImpl extends BaseServiceImpl<MakerTaxRecordMapper, MakerTaxRecordEntity> implements IMakerTaxRecordService {

}
