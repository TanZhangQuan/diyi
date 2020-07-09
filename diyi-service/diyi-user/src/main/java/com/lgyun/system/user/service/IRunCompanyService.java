package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.common.api.R;
import com.lgyun.system.user.dto.RunCompanyDto;
import com.lgyun.system.user.entity.RunCompanyEntity;

import java.util.List;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public interface IRunCompanyService extends IService<RunCompanyEntity> {

    /**
     *根据创客Id
     */
    R<IPage<RunCompanyEntity>> findMakerId(IPage<RunCompanyEntity> page, Long makerId);

    /**
     * 新增
     */
    R runCompanySave(RunCompanyDto runCompanyDto,Long makerId);
}

