package com.lgyun.system.order.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.order.vo.SelfHelpInvoiceListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceStatisticsVO;
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
    public R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long allKindEnterpriseId, InvoicePeopleType invoicePeopleType) {
        return iSelfHelpInvoiceService.yearMonthMoney(allKindEnterpriseId, invoicePeopleType);
    }

    @Override
    public R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long allKindEnterpriseId, InvoicePeopleType invoicePeopleType) {
        return iSelfHelpInvoiceService.selfHelpInvoiceStatistics(allKindEnterpriseId, invoicePeopleType);
    }

    @Override
    public R<IPage<SelfHelpInvoiceListVO>> selfHelpInvoiceList(Query query, Long allKindEnterpriseId, InvoicePeopleType invoicePeopleType) {
        return iSelfHelpInvoiceService.selfHelpInvoiceList(Condition.getPage(query.setDescs("create_time")), allKindEnterpriseId, invoicePeopleType);
    }

}
