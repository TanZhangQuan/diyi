package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.RegularDeclareEntity;
import com.lgyun.system.user.vo.RegularDeclareVO;

/**
 * 申报表包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-09-22 15:46:31
 */
public class RegularDeclareWrapper extends BaseEntityWrapper<RegularDeclareEntity, RegularDeclareVO> {

    public static RegularDeclareWrapper build() {
        return new RegularDeclareWrapper();
    }

    @Override
    public RegularDeclareVO entityVO(RegularDeclareEntity regularDeclare) {
        return BeanUtil.copy(regularDeclare, RegularDeclareVO.class);
    }

}
