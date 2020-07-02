package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.mapper.WorksheetMapper;
import com.lgyun.system.order.service.IWorksheetService;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-06-29 10:39:06
 */
@Service("worksheetService")
public class WorksheetServiceImpl extends ServiceImpl<WorksheetMapper, WorksheetEntity> implements IWorksheetService {

}
