package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.MakerTotalInvoiceEntity;
import com.lgyun.system.order.vo.MakerTotalInvoiceVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-18 20:49:12
 */
public class MakerTotalInvoiceWrapper extends BaseEntityWrapper<MakerTotalInvoiceEntity, MakerTotalInvoiceVO> {

    public static MakerTotalInvoiceWrapper build() {
        return new MakerTotalInvoiceWrapper();
    }

    @Override
    public MakerTotalInvoiceVO entityVO(MakerTotalInvoiceEntity makerTotalInvoice) {

        if (makerTotalInvoice == null){
            return null;
        }

        return BeanUtil.copy(makerTotalInvoice, MakerTotalInvoiceVO.class);
    }

}
