package com.lgyun.system.order.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.AppConstant;
import com.lgyun.common.enumeration.InvoicePeopleType;
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
    R selfHelpInvoiceList(@RequestParam("current") Integer current, @RequestParam("size") Integer size, @RequestParam("allKindEnterpriseId") Long allKindEnterpriseId, @RequestParam("invoicePeopleType") InvoicePeopleType invoicePeopleType);

    /**
     * 根据商户id查询众包的合同
     *
     * @return
     */
    @GetMapping(API_PREFIX + "/self_help_invoice/select_ent_mak_sourc")
    R selectEntMakSourc(@RequestParam("current") Integer current, @RequestParam("size") Integer size, @RequestParam("enterpriseId") Long enterpriseId);


    /**
     * 根据自助开票id查询众包的详情
     */
    @GetMapping(API_PREFIX + "/self_help_invoice/find_detail_crowd_sourc")
    R findDetailCrowdSourcing( @RequestParam("selfHelpInvoiceId") Long selfHelpInvoiceId);
}
