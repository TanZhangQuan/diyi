package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
     *根据名字查询
     */
    List<RunCompanyEntity> findCompanyName(String companyName);
}

