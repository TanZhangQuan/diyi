package com.lgyun.system.order.util;

import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.exception.ServiceException;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 获取当前创客相关工具
 *
 * @author liangfeihu
 * @since 2020/6/6 00:21
 */
@Slf4j
@Component
@AllArgsConstructor
public class MakerCurrentUtil {

    private IUserClient iUserClient;

    /**
     * 获取当前创客
     */
    public MakerEntity current(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null){
            throw new ServiceException("创客未登录");
        }

        MakerEntity makerEntity = iUserClient.makerFindByUserId(bladeUser.getUserId());
        if (makerEntity == null) {
            throw new ServiceException("创客未注册");
        }

        if (!(AccountState.NORMAL.equals(makerEntity.getMakerState()))) {
            throw new ServiceException("创客账户状态非正常，请联系客服");
        }

        return makerEntity;
    }

}
