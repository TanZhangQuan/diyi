package com.lgyun.system.order.quratz;

import com.lgyun.system.order.service.IPayMakerService;
import com.lgyun.system.order.vo.TimeoutPayMakerListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author .
 * @date 2020/11/16.
 * @time 16:41.
 */
@Slf4j
@Component(value = "quratzJob")
@AllArgsConstructor
public class QuratzJob {

    private IPayMakerService payMakerService;

    /**
     * 处理创客超过7天未确认分包支付明细
     */
    public void confirmPayMaker() {
        log.info("处理创客超过7天未确认分包支付明细开始------------start");
        List<TimeoutPayMakerListVO> timeoutPayMakerListVOList = payMakerService.queryTimeoutPayMakerList();
        for (TimeoutPayMakerListVO timeoutPayMakerListVO : timeoutPayMakerListVOList) {
            try {
                payMakerService.confirmPayMaker(timeoutPayMakerListVO.getMakerId(), timeoutPayMakerListVO.getPayMakerId());
            } catch (Exception e) {
                log.info("处理创客超过7天未确认分包支付明细异常：" + e.getMessage());
            }
        }
        log.info("处理创客超过7天未确认分包支付明细结束------------end");
    }

}
