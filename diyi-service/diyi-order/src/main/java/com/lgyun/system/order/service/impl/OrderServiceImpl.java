package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.system.order.entity.OrderEntity;
import com.lgyun.system.order.entity.PayEntity;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.mapper.OrderMapper;
import com.lgyun.system.order.service.IOrderService;
import com.lgyun.system.order.service.IPayService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.order.vo.OrderVO;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IIndividualBusinessService;
import com.lgyun.system.user.service.IMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-06-26 16:57:54
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements IOrderService {

    @Autowired
    IWorksheetService iWorksheetService;

    @Autowired
    IMakerService iMakerService;

    @Autowired
    IIndividualBusinessService iIndividualBusinessService;

    @Autowired
    IPayService iPayService;

    @Override
    public IPage<OrderVO> selectOrderPage(IPage page, String orderState) {
        return page.setRecords(baseMapper.selectOrderPage(page, orderState));
    }

    @Override
    public R robOrder(Long orderId, Long makerId) {
        if(null == orderId || null == makerId){
            return R.fail("抢单失败");
        }
        return robOrderDealis(orderId, makerId);
    }


    /**
     *抢单
     */
    public synchronized R robOrderDealis(Long orderId, Long makerId){
        OrderEntity orderEntity = baseMapper.selectById(orderId);
        if(null == orderEntity){
            return R.fail("工单不存在");
        }
        Integer integer = iPayService.queryRobOrderCount(orderId);
        if(orderEntity.getWorksheetNum() == integer){
            return R.fail("抱歉，工单已经抢完");
        }
        MakerEntity makerEntity = iMakerService.getById(makerId);
        if(null == makerEntity){
            return R.fail("抱歉，创客不存在");
        }
        WorksheetEntity worksheetEntity =new WorksheetEntity();
        worksheetEntity.setOrderId(orderId);
        worksheetEntity.setMakerId(makerId);
        worksheetEntity.setWorksheetNo(UUID.randomUUID().toString());
        worksheetEntity.setGetOrderDate(new Date());
        worksheetEntity.setGetOrderDesc("创客抢单");
        worksheetEntity.setWorksheetState(WorksheetState.NORMAL.getValue());
        iWorksheetService.save(worksheetEntity);
        return R.success("恭喜，抢单成功");
    }
}
