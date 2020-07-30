package com.lgyun.system.order.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.system.order.vo.SelfHelpInvoiceListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceStatisticsVO;
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
    public R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long businessEnterpriseId, MakerType makerType) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long businessEnterpriseId, MakerType makerType) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public R<IPage<SelfHelpInvoiceListVO>> selfHelpInvoiceList(Integer current, Integer size,  Long businessEnterpriseId, MakerType makerType) {
        return R.fail("网络繁忙，请稍后尝试");
    }

}
