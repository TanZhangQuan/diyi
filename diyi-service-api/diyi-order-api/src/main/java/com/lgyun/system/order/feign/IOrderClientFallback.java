package com.lgyun.system.order.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.system.order.vo.SelfHelpInvoiceYearMonthMoneyVO;
import org.springframework.stereotype.Component;

/**
 * Feign失败配置
 *
 * @author tzq
 * @since 2020/6/6 00:29
 */
@Component
public class IOrderClientFallback implements IOrderClient {

    @Override
    public R<SelfHelpInvoiceYearMonthMoneyVO> yearMonthMoney(Long individualBusinessId, MakerType makerType) {
        return R.fail("未获取到账号信息");
    }

}
