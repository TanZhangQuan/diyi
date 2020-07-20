package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.MakerTaxRecordEntity;
import com.lgyun.system.order.vo.MakerTaxRecordVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
public class MakerTaxRecordWrapper extends BaseEntityWrapper<MakerTaxRecordEntity, MakerTaxRecordVO> {

    public static MakerTaxRecordWrapper build() {
        return new MakerTaxRecordWrapper();
    }

    @Override
    public MakerTaxRecordVO entityVO(MakerTaxRecordEntity makerTaxRecord) {
        MakerTaxRecordVO makerTaxRecordVO = BeanUtil.copy(makerTaxRecord, MakerTaxRecordVO.class);
        return makerTaxRecordVO;
    }

}
