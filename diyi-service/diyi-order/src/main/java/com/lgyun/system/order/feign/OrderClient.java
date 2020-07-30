package com.lgyun.system.order.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
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
    public R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long businessEnterpriseId, MakerType makerType) {
        return iSelfHelpInvoiceService.yearMonthMoney(businessEnterpriseId, makerType);
    }

    @Override
    public R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long businessEnterpriseId, MakerType makerType) {
        return iSelfHelpInvoiceService.selfHelpInvoiceStatistics(businessEnterpriseId, makerType);
    }

    @Override
    public R<IPage<SelfHelpInvoiceListVO>> selfHelpInvoiceList(Integer current, Integer size, Long businessEnterpriseId, MakerType makerType) {
        Query query = new Query();
        query.setCurrent(current);
        query.setSize(size);
        return iSelfHelpInvoiceService.selfHelpInvoiceList(Condition.getPage(query.setDescs("create_time")), businessEnterpriseId, makerType);
    }

}
