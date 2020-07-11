package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.user.entity.WorksheetAttentionEntity;
import com.lgyun.system.user.mapper.WorksheetAttentionMapper;
import com.lgyun.system.user.service.IWorksheetAttentionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Service
@AllArgsConstructor
public class WorksheetAttentionServiceImpl extends ServiceImpl<WorksheetAttentionMapper, WorksheetAttentionEntity> implements IWorksheetAttentionService {
    private static Logger logger = LoggerFactory.getLogger(WorksheetAttentionServiceImpl.class);

}
