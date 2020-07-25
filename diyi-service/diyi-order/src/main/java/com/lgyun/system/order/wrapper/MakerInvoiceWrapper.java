package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.MakerInvoiceEntity;
import com.lgyun.system.order.vo.MakerInvoiceVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
public class MakerInvoiceWrapper extends BaseEntityWrapper<MakerInvoiceEntity, MakerInvoiceVO> {

    public static MakerInvoiceWrapper build() {
        return new MakerInvoiceWrapper();
    }

    @Override
    public MakerInvoiceVO entityVO(MakerInvoiceEntity makerInvoice) {

        if (makerInvoice == null){
            return null;
        }

        return BeanUtil.copy(makerInvoice, MakerInvoiceVO.class);
    }

}
