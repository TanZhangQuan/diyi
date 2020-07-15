package com.lgyun.system.order.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.order.vo.SelfHelpInvoiceYearMonthMoneyVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单服务Feign实现类
 *
 * @author liangfeihu
 * @since 2020/6/6 22:11
 */
@RestController
@AllArgsConstructor
public class OrderClient implements IOrderClient {

    private ISelfHelpInvoiceService iSelfHelpInvoiceService;

    @Override
    public R<SelfHelpInvoiceYearMonthMoneyVO> yearMonthMoney(Long individualBusinessId, MakerType makerType) {
        return iSelfHelpInvoiceService.yearMonthMoney(individualBusinessId, makerType);
    }

}
