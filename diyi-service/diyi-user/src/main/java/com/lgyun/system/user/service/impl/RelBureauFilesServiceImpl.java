package com.lgyun.system.user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.user.mapper.RelBureauFilesMapper;
import com.lgyun.system.user.entity.RelBureauFilesEntity;
import com.lgyun.system.user.service.IRelBureauFilesService;

/**
 * 相关局监管文件：相关局监管文件管理表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-10-20 18:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelBureauFilesServiceImpl extends BaseServiceImpl<RelBureauFilesMapper, RelBureauFilesEntity> implements IRelBureauFilesService {

}
