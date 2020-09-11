package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.EnterpriseReportEntity;
import com.lgyun.system.user.vo.EnterpriseReportVO;

/**
 * 年度申报管理表包装类,返回视图层所需的字段
 *
 * @author tzq
 * @since 2020-08-12 14:47:56
 */
public class EnterpriseReportWrapper extends BaseEntityWrapper<EnterpriseReportEntity, EnterpriseReportVO> {

    public static EnterpriseReportWrapper build() {
        return new EnterpriseReportWrapper();
    }

    @Override
    public EnterpriseReportVO entityVO(EnterpriseReportEntity enterpriseReport) {
        return BeanUtil.copy(enterpriseReport, EnterpriseReportVO.class);
    }

}
