package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.order.entity.PaysheetEntity;
import com.lgyun.system.order.mapper.PaysheetMapper;
import com.lgyun.system.order.service.IPaysheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-06 14:14:06
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PaysheetServiceImpl extends ServiceImpl<PaysheetMapper, PaysheetEntity> implements IPaysheetService {

}
