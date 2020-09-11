package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.SetupEntity;
import com.lgyun.system.user.vo.SetupVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author tzq
 * @since 2020-06-26 17:21:05
 */
public class SetupWrapper extends BaseEntityWrapper<SetupEntity, SetupVO> {

    public static SetupWrapper build() {
        return new SetupWrapper();
    }

    @Override
    public SetupVO entityVO(SetupEntity setup) {

        if (setup == null){
            return null;
        }

        return BeanUtil.copy(setup, SetupVO.class);
    }

}
