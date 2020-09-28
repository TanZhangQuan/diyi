package com.lgyun.system.order.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lgyun.system.order.dto.SelfHelpInvoiceDTO;
import com.lgyun.system.order.service.ISelfHelpInvoiceDetailService;
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
public class InvoiceListListener extends AnalysisEventListener<InvoiceListExcel> {

    /**
     * 默认每隔3000条存储数据库
     */
    private int batchCount = 3000;

    /**
     * 缓存的数据列表
     */
    private List<InvoiceListExcel> list = new ArrayList<>();

    private final SelfHelpInvoiceDTO selfHelpInvoiceDto;

    private final ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;

    @Override
    public void invoke(InvoiceListExcel data, AnalysisContext analysisContext) {
        list.add(data);
        //参数
        list.forEach(makerExcel -> {
            makerExcel.setSelfHelpInvoiceDto(selfHelpInvoiceDto);
        });
        // 达到BATCH_COUNT，则调用importer方法入库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= batchCount) {
            // 调用importer方法
            selfHelpInvoiceDetailService.importSelfHelpInvoiceDetail(list);
            // 存储完成清理list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 调用importer方法
        selfHelpInvoiceDetailService.importSelfHelpInvoiceDetail(list);
        // 存储完成清理list
        list.clear();
    }
}
