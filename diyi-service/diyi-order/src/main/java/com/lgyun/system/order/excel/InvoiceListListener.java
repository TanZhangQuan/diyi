package com.lgyun.system.order.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * UserImportListener
 *
 * @author tzq
 * @since 2020/6/6 22:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InvoiceListListener extends AnalysisEventListener<InvoiceListExcel> {

    /**
     * 自定义用于暂时存储
     * 可以通过实例获取该值
     */
    private List<InvoiceListExcel> list = new ArrayList<>();

    @Override
    public void invoke(InvoiceListExcel InvoiceListExcel, AnalysisContext context) {
        list.add(InvoiceListExcel);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //解析结束销毁不用的资源
        //注意不要调用datas.clear(),否则getDatas为null
    }
}
