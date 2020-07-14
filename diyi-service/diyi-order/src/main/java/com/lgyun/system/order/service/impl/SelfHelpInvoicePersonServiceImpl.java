package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.SelfHelpInvoicePersonDto;
import com.lgyun.system.order.entity.SelfHelpInvoicePersonEntity;
import com.lgyun.system.order.mapper.SelfHelpInvoicePersonMapper;
import com.lgyun.system.order.service.ISelfHelpInvoicePersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Service
@AllArgsConstructor
public class SelfHelpInvoicePersonServiceImpl extends BaseServiceImpl<SelfHelpInvoicePersonMapper, SelfHelpInvoicePersonEntity> implements ISelfHelpInvoicePersonService {

    @Override
    public R<IPage<SelfHelpInvoicePersonEntity>> findPersonMakerId(IPage<SelfHelpInvoicePersonEntity> page, Long makerId) {
        return R.data(page.setRecords(baseMapper.findPersonMakerId(page, makerId)));
    }

    @Override
    public R saveSelfHelpInvoicePerson(SelfHelpInvoicePersonDto selfHelpInvoicePersonDto, Long makerId) {
        SelfHelpInvoicePersonEntity personEntity = new SelfHelpInvoicePersonEntity();
        BeanUtils.copyProperties(selfHelpInvoicePersonDto, personEntity);
        personEntity.setMakerId(makerId);
        personEntity.setCreateTime(new Date());
        personEntity.setCreateUser(makerId);
        personEntity.setUpdateTime(new Date());
        personEntity.setUpdateUser(makerId);
        personEntity.setIsDeleted(0);
        personEntity.setStatus(1);
        save(personEntity);
        return R.success("成功");
    }
}
