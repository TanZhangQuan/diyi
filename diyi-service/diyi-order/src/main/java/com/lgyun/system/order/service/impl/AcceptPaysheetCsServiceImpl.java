package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.AcceptSheetAndCsListDTO;
import com.lgyun.system.order.entity.AcceptPaysheetCsEntity;
import com.lgyun.system.order.mapper.AcceptPaysheetCsMapper;
import com.lgyun.system.order.service.IAcceptPaysheetCsService;
import com.lgyun.system.order.vo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 众包交付支付验收单表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-05 10:43:29
 */
@Slf4j
@Service
@AllArgsConstructor
public class AcceptPaysheetCsServiceImpl extends BaseServiceImpl<AcceptPaysheetCsMapper, AcceptPaysheetCsEntity> implements IAcceptPaysheetCsService {

    @Override
    public R<IPage<AcceptPaysheetAndCsListMakerVO>> queryCrowdAcceptPaysheetListMaker(Long enterpriseId, Long makerId, IPage<AcceptPaysheetAndCsListMakerVO> page) {
        return R.data(page.setRecords(baseMapper.queryCrowdAcceptPaysheetListMaker(enterpriseId, makerId, page)));
    }

    @Override
    public R<AcceptPaysheetCsDetailMakerVO> queryCrowdAcceptPaysheetDetailMaker(Long makerId, Long acceptPaysheetId) {
        return R.data(baseMapper.queryCrowdAcceptPaysheetDetailMaker(makerId, acceptPaysheetId));
    }

    @Override
    public R<IPage<AcceptPaysheetCsListEnterpriseVO>> queryCrowdAcceptPaysheetListEnterprise(Long enterpriseId, AcceptSheetAndCsListDTO acceptSheetAndCsListDto, IPage<AcceptPaysheetCsListEnterpriseVO> page) {

        if (acceptSheetAndCsListDto.getBeginDate() != null && acceptSheetAndCsListDto.getEndDate() != null) {
            if (acceptSheetAndCsListDto.getBeginDate().after(acceptSheetAndCsListDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.queryCrowdAcceptPaysheetListEnterprise(enterpriseId, acceptSheetAndCsListDto, page)));
    }

    @Override
    public R<AcceptPaysheetCsDetailEnterpriseVO> queryCrowdAcceptPaysheetDetailEnterprise(Long acceptPaysheetCsId) {
        return R.data(baseMapper.queryCrowdAcceptPaysheetDetailEnterprise(acceptPaysheetCsId));
    }
    
}
