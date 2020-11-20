package com.lgyun.system.user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.user.mapper.RelBureauNoticeReadMapper;
import com.lgyun.system.user.entity.RelBureauNoticeReadEntity;
import com.lgyun.system.user.service.IRelBureauNoticeReadService;

/**
 * 相关局通知阅读管理表 Service 实现
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelBureauNoticeReadServiceImpl extends BaseServiceImpl<RelBureauNoticeReadMapper, RelBureauNoticeReadEntity> implements IRelBureauNoticeReadService {

}
