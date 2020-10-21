package com.lgyun.system.user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.user.mapper.RelBureauFilesReadMapper;
import com.lgyun.system.user.entity.RelBureauFilesReadEntity;
import com.lgyun.system.user.service.IRelBureauFilesReadService;

/**
 * 监管文件阅读记录：相关局监管文件阅读管理表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-10-20 18:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelBureauFilesReadServiceImpl extends BaseServiceImpl<RelBureauFilesReadMapper, RelBureauFilesReadEntity> implements IRelBureauFilesReadService {

}
