package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AcceptPaysheetType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDto;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import com.lgyun.system.order.mapper.AcceptPaysheetMapper;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.vo.AcceptPaysheetByEnterpriseListVO;
import com.lgyun.system.order.vo.AcceptPaysheetWorksheetVO;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.vo.EnterprisesByWorksheetListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-17 14:38:25
 */
@Slf4j
@Service
@AllArgsConstructor
public class AcceptPaysheetServiceImpl extends BaseServiceImpl<AcceptPaysheetMapper, AcceptPaysheetEntity> implements IAcceptPaysheetService {

    @Override
    public R<IPage<AcceptPaysheetByEnterpriseListVO>> getAcceptPaysheetsByEnterprise(IPage<AcceptPaysheetByEnterpriseListVO> page, Long enterpriseId, Long makerId) {
        return R.data(page.setRecords(baseMapper.getAcceptPaysheetsByEnterprise(enterpriseId, makerId, page)));
    }

    @Override
    public R<AcceptPaysheetWorksheetVO> getAcceptPaysheetWorksheet(Long makerId, Long acceptPaysheetId) {
        return R.data(baseMapper.getAcceptPaysheetWorksheet(makerId, acceptPaysheetId));
    }

    @Override
    public R<IPage<EnterprisesByWorksheetListVO>> getEnterprisesByWorksheet(IPage<EnterprisesByWorksheetListVO> page, Long makerId) {
        return R.data(page.setRecords(baseMapper.getEnterprisesByWorksheet(makerId, page)));
    }

    @Override
    public R<String> upload(AcceptPaysheetSaveDto acceptPaysheetSaveDto, EnterpriseWorkerEntity enterpriseWorkerEntity) {

        //保存交付支付验收单
        AcceptPaysheetEntity acceptPaysheetEntity = new AcceptPaysheetEntity();
        if (AcceptPaysheetType.SINGLE.equals(acceptPaysheetSaveDto.getAcceptPaysheetType())) {
            if (acceptPaysheetSaveDto.getMakerId() == null) {
                return R.fail("请选择创客");
            }

            acceptPaysheetEntity.setMakerId(acceptPaysheetSaveDto.getMakerId());
        }

        acceptPaysheetEntity.setPayEnterpriseId(acceptPaysheetSaveDto.getPayEnterpriseId());
        acceptPaysheetEntity.setAcceptPaysheetUrl(acceptPaysheetSaveDto.getAcceptPaysheetUrl());
        acceptPaysheetEntity.setAcceptPaysheetType(acceptPaysheetSaveDto.getAcceptPaysheetType());
        acceptPaysheetEntity.setUploadDateSource("商户上传");
        acceptPaysheetEntity.setUploadDatePerson(enterpriseWorkerEntity.getWorkerName());

        save(acceptPaysheetEntity);

        return R.success("上传成功");
    }

}
