package com.lgyun.system.order.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.system.order.service.IPayMakerService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * UserImportListener
 *
 * @author liangfeihu
 * @since 2020/6/6 22:12
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PayEnterpriseImportListener extends AnalysisEventListener<PayEnterpriseExcel> {

    /**
     * 默认每隔3000条存储数据库
     */
    private int batchCount = 3000;

    /**
     * 缓存的数据列表
     */
    private List<PayEnterpriseExcel> list = new ArrayList<>();

    /**
     * 创客支付明细service
     */
    private final IPayMakerService iPayMakerService;

    /**
     * 总包支付清单ID
     */
   private final Long payEnterpriseId;

    /**
     * 创客类型
     */
    private final MakerType makerType;

    @Override
    public void invoke(PayEnterpriseExcel data, AnalysisContext context) {
        list.add(data);
        //设置商户ID
        // 达到BATCH_COUNT，则调用importer方法入库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= batchCount) {
            // 调用importer方法
            iPayMakerService.importPayMakerList(list, payEnterpriseId, makerType);
            // 存储完成清理list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 调用importer方法
        iPayMakerService.importPayMakerList(list, payEnterpriseId, makerType);
        // 存储完成清理list
        list.clear();
    }

}
