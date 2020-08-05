package com.lgyun.system.order.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import com.lgyun.system.order.mapper.SelfHelpInvoiceDetailMapper;
import com.lgyun.system.order.service.ISelfHelpInvoiceDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Slf4j
@Service
@AllArgsConstructor
public class SelfHelpInvoiceDetailServiceImpl extends BaseServiceImpl<SelfHelpInvoiceDetailMapper, SelfHelpInvoiceDetailEntity> implements ISelfHelpInvoiceDetailService {

    @Override
    public R uploadDeliverSheetUrl(Long selfHelpInvoiceDetailId, String deliverSheetUrl) {
        SelfHelpInvoiceDetailEntity byId = getById(selfHelpInvoiceDetailId);
        if(null == byId){
            return R.fail("自助开票详情id输入错误");
        }
        byId.setDeliverSheetUrl(deliverSheetUrl);
        saveOrUpdate(byId);
        return R.success("上传成功");
    }
}
