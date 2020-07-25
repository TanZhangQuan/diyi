package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.OnlineSignPicEntity;
import com.lgyun.system.user.vo.OnlineSignPicVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-18 15:59:14
 */
public class OnlineSignPicWrapper extends BaseEntityWrapper<OnlineSignPicEntity, OnlineSignPicVO> {

    public static OnlineSignPicWrapper build() {
        return new OnlineSignPicWrapper();
    }

    @Override
    public OnlineSignPicVO entityVO(OnlineSignPicEntity onlineSignPic) {
        return BeanUtil.copy(onlineSignPic, OnlineSignPicVO.class);
    }

}
