package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.RunCompanyDto;
import com.lgyun.system.user.entity.RunCompanyEntity;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public interface IRunCompanyService extends BaseService<RunCompanyEntity> {

    /**
     * 根据创客Id
     *
     * @param current
     * @param size
     * @param makerId
     * @return
     */
    R<IPage<RunCompanyEntity>> findMakerId(Integer current, Integer size, Long makerId);

    /**
     * 新建购买方
     *
     * @param runCompanyDto
     * @return
     */
    R runCompanySave(RunCompanyDto runCompanyDto);
}

