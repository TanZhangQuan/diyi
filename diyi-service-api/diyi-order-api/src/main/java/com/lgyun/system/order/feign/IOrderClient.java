package com.lgyun.system.order.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.AppConstant;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.vo.SelfHelpInvoiceListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceStatisticsVO;
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
     * @param allKindEnterpriseId
     * @param invoicePeopleType
     * @return
     */
    @GetMapping(API_PREFIX + "/self_help_invoice/year_month_money")
    R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(@RequestParam("allKindEnterpriseId") Long allKindEnterpriseId, @RequestParam("invoicePeopleType") InvoicePeopleType invoicePeopleType);

    /**
     * 获取开票统计
     *
     * @param allKindEnterpriseId
     * @param invoicePeopleType
     * @return
     */
    @GetMapping(API_PREFIX + "/self_help_invoice/self_help_invoice_statistics")
    R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(@RequestParam("allKindEnterpriseId") Long allKindEnterpriseId, @RequestParam("invoicePeopleType") InvoicePeopleType invoicePeopleType);

    /**
     * 根据个独或个体户ID查询自助开票记录
     *
     * @param allKindEnterpriseId
     * @param invoicePeopleType
     * @return
     */
    @GetMapping(API_PREFIX + "/self_help_invoice/self_help_invoice_list")
    R<IPage<SelfHelpInvoiceListVO>> selfHelpInvoiceList(@RequestParam("query") Query query, @RequestParam("allKindEnterpriseId") Long allKindEnterpriseId, @RequestParam("invoicePeopleType") InvoicePeopleType invoicePeopleType);

}
