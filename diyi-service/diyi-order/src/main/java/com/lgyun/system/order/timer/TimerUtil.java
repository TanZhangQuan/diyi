package com.lgyun.system.order.timer;

import org.quartz.CronTrigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author jun.
 * @date 2020/11/16.
 * @time 15:24.
 */
@Configuration
public class TimerUtil {

    //Job  编辑任务
    @Bean
    JobDetailFactoryBean job() {
        JobDetailFactoryBean bean = new JobDetailFactoryBean();
        bean.setJobClass(ConfirmPayMakerJob.class);//类要继承QuartzJobBean
        bean.setDurability(true);
        return bean;
    }


    //Trigger  创建定时器
    @Bean
    CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(job().getObject());//可以对应不同的Job，一个job可以被多个trigger关联，但是一个trigger只能关联一个job
        //bean.setCronExpression("59 59 23 * * ?");//corn表达式,每5秒执行一次
        bean.setCronExpression("* * * * * ?");//corn表达式,每5秒执行一次
        return bean;
    }

    //Scheduler  加载定时器
    @Bean
    SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        CronTrigger cronTrigger = cronTriggerFactoryBean().getObject();
        bean.setTriggers(cronTrigger);
        return bean;
    }
}
