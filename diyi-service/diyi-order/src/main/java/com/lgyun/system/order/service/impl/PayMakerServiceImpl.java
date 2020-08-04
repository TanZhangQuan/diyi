package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.PayListDto;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.lgyun.system.order.mapper.PayMakerMapper;
import com.lgyun.system.order.service.IPayMakerService;
import com.lgyun.system.order.vo.PayListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayMakerServiceImpl extends BaseServiceImpl<PayMakerMapper, PayMakerEntity> implements IPayMakerService {

    @Override
    public R<IPage<PayListVO>> getByDtoEnterprise(Long enterpriseId, PayListDto payListDto, IPage<PayListVO> page) {

        if (payListDto.getBeginDate() != null && payListDto.getEndDate() != null) {
            if (payListDto.getBeginDate().after(payListDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getByDtoEnterprise(enterpriseId, payListDto, page)));
    }
}
