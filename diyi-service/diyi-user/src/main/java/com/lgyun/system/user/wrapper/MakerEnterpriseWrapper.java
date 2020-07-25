package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.vo.MakerEnterpriseVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
public class MakerEnterpriseWrapper extends BaseEntityWrapper<MakerEnterpriseEntity, MakerEnterpriseVO> {

    public static MakerEnterpriseWrapper build() {
        return new MakerEnterpriseWrapper();
    }

    @Override
    public MakerEnterpriseVO entityVO(MakerEnterpriseEntity makerEnterprise) {
        return BeanUtil.copy(makerEnterprise, MakerEnterpriseVO.class);
    }

}
