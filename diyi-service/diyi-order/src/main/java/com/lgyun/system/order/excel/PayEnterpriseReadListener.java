package com.lgyun.system.order.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PayEnterpriseReadListener extends AnalysisEventListener<PayEnterpriseExcel> {

    /**
     * 自定义用于暂时存储
     * 可以通过实例获取该值
     */
    private List<PayEnterpriseExcel> list = new ArrayList<>();

    @Override
    public void invoke(PayEnterpriseExcel payEnterpriseExcel, AnalysisContext context) {
        list.add(payEnterpriseExcel);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //解析结束销毁不用的资源
        //注意不要调用datas.clear(),否则getDatas为null
    }

}
