package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.system.order.dto.SelfHelpInvoicePersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lgyun.system.order.mapper.SelfHelpInvoicePersonMapper;
import com.lgyun.system.order.entity.SelfHelpInvoicePersonEntity;
import com.lgyun.system.order.service.ISelfHelpInvoicePersonService;

import java.util.Date;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SelfHelpInvoicePersonServiceImpl extends ServiceImpl<SelfHelpInvoicePersonMapper, SelfHelpInvoicePersonEntity> implements ISelfHelpInvoicePersonService {

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
