package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.RunCompanyEntity;
import com.lgyun.system.user.vo.RunCompanyVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public class RunCompanyWrapper extends BaseEntityWrapper<RunCompanyEntity, RunCompanyVO> {

    public static RunCompanyWrapper build() {
        return new RunCompanyWrapper();
    }

    @Override
    public RunCompanyVO entityVO(RunCompanyEntity runCompany) {
        return BeanUtil.copy(runCompany, RunCompanyVO.class);
    }

}
