package com.lgyun.system.user.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lgyun.system.user.service.IMakerService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * UserImportListener
 *
 * @author tzq
 * @since 2020/6/6 22:12
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MakerImportListener extends AnalysisEventListener<MakerExcel> {

    /**
     * 默认每隔3000条存储数据库
     */
    private int batchCount = 3000;

    /**
     * 缓存的数据列表
     */
    private List<MakerExcel> list = new ArrayList<>();

    /**
     * 创客service
     */
    private final IMakerService iMakerService;

    /**
     * 商户ID
     */
    private final Long enterpriseId;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invoke(MakerExcel data, AnalysisContext context) {
        list.add(data);
        // 达到BATCH_COUNT，则调用importer方法入库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= batchCount) {
            // 调用importer方法
            iMakerService.importMaker(list, enterpriseId);
            // 存储完成清理list
            list.clear();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 调用importer方法
        iMakerService.importMaker(list, enterpriseId);
        // 存储完成清理list
        list.clear();
    }

}
