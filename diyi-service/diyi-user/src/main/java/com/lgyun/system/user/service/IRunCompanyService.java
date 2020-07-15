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
     *根据创客Id
     */
    R findMakerId(IPage<RunCompanyEntity> page, Long makerId);

    /**
     * 新增
     */
    R runCompanySave(RunCompanyDto runCompanyDto);
}

