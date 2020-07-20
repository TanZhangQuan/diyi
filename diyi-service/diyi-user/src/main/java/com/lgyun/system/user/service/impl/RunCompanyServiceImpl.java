package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.RunCompanyDto;
import com.lgyun.system.user.entity.RunCompanyEntity;
import com.lgyun.system.user.mapper.RunCompanyMapper;
import com.lgyun.system.user.service.IRunCompanyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Slf4j
@Service
@AllArgsConstructor
public class RunCompanyServiceImpl extends BaseServiceImpl<RunCompanyMapper, RunCompanyEntity> implements IRunCompanyService {

    /**
     * 根据创客Id
     * @param makerId
     * @return
     */
    @Override
    public R<IPage<RunCompanyEntity>> findMakerId(IPage<RunCompanyEntity> page,Long makerId) {
        return R.data(page.setRecords(baseMapper.findCompanyName(makerId, page)));
    }

    @Override
    public R runCompanySave(RunCompanyDto runCompanyDto) {
        RunCompanyEntity runCompanyEntity1 = baseMapper.repeatCompanyName(runCompanyDto.getCompanyName(), runCompanyDto.getMakerId());
        if(null != runCompanyEntity1){
            return R.fail("名字不能重复");
        }
        RunCompanyEntity runCompanyEntity2 = baseMapper.repeatTaxNo(runCompanyDto.getTaxNo(), runCompanyDto.getMakerId());
        if(null != runCompanyEntity2){
            return R.fail("纳税号码不能重复");
        }
        RunCompanyEntity runCompanyEntity3 = baseMapper.repeatBankAccount(runCompanyDto.getBankAccount(), runCompanyDto.getMakerId());
        if(null != runCompanyEntity3){
            return R.fail("银行卡号码不能重复");
        }
        RunCompanyEntity runCompanyEntity = new RunCompanyEntity();
        BeanUtils.copyProperties(runCompanyDto, runCompanyEntity);
        runCompanyEntity.setMakerId(runCompanyDto.getMakerId());
        save(runCompanyEntity);
        return R.success("成功");
    }
}
