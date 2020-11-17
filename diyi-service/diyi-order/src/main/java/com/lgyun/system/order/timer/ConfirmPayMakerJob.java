package com.lgyun.system.order.timer;

import com.lgyun.system.order.service.IPayMakerService;
import com.lgyun.system.order.vo.ExceedPayMakerListVO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author .
 * @date 2020/11/16.
 * @time 16:41.
 */
@Slf4j
@Component
public class ConfirmPayMakerJob implements Job, ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        IPayMakerService payMakerServiceImpl = (IPayMakerService)context.getBean("payMakerServiceImpl");
        log.info("确认收款定时器开始------------start");
        List<ExceedPayMakerListVO> exceedPayMakerListVOS = payMakerServiceImpl.queryExceedPayMakerList(7);
        for (ExceedPayMakerListVO exceedPayMakerListVO : exceedPayMakerListVOS){
            try{
                payMakerServiceImpl.confirmPayMaker(exceedPayMakerListVO.getMakerId(),exceedPayMakerListVO.getPayMakerId());
            }catch (Exception e){
                log.info("错误如下："+e.getMessage());
            }
        }
        log.info("确认收款定时器开始------------end");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}
