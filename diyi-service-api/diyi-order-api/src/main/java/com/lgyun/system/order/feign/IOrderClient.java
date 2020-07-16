package com.lgyun.system.order.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.AppConstant;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.system.order.vo.SelfHelpInvoiceYearMonthMoneyVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Order Feign接口类
 *
 * @author tzq
 */
@FeignClient(
        value = AppConstant.APPLICATION_ORDER_NAME,
        fallback = IOrderClientFallback.class
)
public interface IOrderClient {

    String API_PREFIX = "/order";

    /**
     * 获取开票月度年度金额
     *
     * @return
     */
    @GetMapping(API_PREFIX + "/self-help-invoice/year-month-money")
    R<SelfHelpInvoiceYearMonthMoneyVO> yearMonthMoney(@RequestParam("businessEnterpriseId") Long businessEnterpriseId, @RequestParam("makerType") MakerType makerType);

}
