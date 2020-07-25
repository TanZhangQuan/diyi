package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.SelfHelpInvoicePersonDto;
import com.lgyun.system.order.entity.SelfHelpInvoicePersonEntity;
import com.lgyun.system.order.mapper.SelfHelpInvoicePersonMapper;
import com.lgyun.system.order.service.ISelfHelpInvoicePersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Slf4j
@Service
@AllArgsConstructor
public class SelfHelpInvoicePersonServiceImpl extends BaseServiceImpl<SelfHelpInvoicePersonMapper, SelfHelpInvoicePersonEntity> implements ISelfHelpInvoicePersonService {

    @Override
    public R<IPage<SelfHelpInvoicePersonEntity>> findPersonMakerId(Integer current, Integer size, Long makerId, MakerType makerType) {
        QueryWrapper<SelfHelpInvoicePersonEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SelfHelpInvoicePersonEntity::getMakerId, makerId);

        IPage<SelfHelpInvoicePersonEntity> page = this.page(new Page<>(current, size), queryWrapper);
        return R.data(page);
    }

    @Override
    public R<String> saveSelfHelpInvoicePerson(SelfHelpInvoicePersonDto selfHelpInvoicePersonDto, Long makerId) {
        SelfHelpInvoicePersonEntity personEntity = new SelfHelpInvoicePersonEntity();
        BeanUtils.copyProperties(selfHelpInvoicePersonDto, personEntity);
        personEntity.setMakerId(makerId);
        save(personEntity);
        return R.success("成功");
    }
}
