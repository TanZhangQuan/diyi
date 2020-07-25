package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.PlatformVoiceEntity;
import com.lgyun.system.order.vo.PlatformVoiceVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
public class PlatformVoiceWrapper extends BaseEntityWrapper<PlatformVoiceEntity, PlatformVoiceVO> {

    public static PlatformVoiceWrapper build() {
        return new PlatformVoiceWrapper();
    }

    @Override
    public PlatformVoiceVO entityVO(PlatformVoiceEntity platformVoice) {

        if (platformVoice == null){
            return null;
        }

        return BeanUtil.copy(platformVoice, PlatformVoiceVO.class);
    }

}
