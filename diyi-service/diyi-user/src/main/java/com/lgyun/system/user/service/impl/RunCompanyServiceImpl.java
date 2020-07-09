package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.WorksheetState;
import com.lgyun.system.user.dto.RunCompanyDto;
import com.lgyun.system.user.entity.RunCompanyEntity;
import com.lgyun.system.user.mapper.RunCompanyMapper;
import com.lgyun.system.user.service.IRunCompanyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RunCompanyServiceImpl extends ServiceImpl<RunCompanyMapper, RunCompanyEntity> implements IRunCompanyService {
    private Logger logger = LoggerFactory.getLogger(RunCompanyServiceImpl.class);

    /**
     * 根据创客Id
     * @param makerId
     * @return
     */
    @Override
    public R<IPage<RunCompanyEntity>> findMakerId(IPage<RunCompanyEntity> page,Long makerId) {
        return R.data(page.setRecords(baseMapper.findCompanyName(page, makerId)));
    }

    @Override
    public R runCompanySave(RunCompanyDto runCompanyDto,Long makerId) {
        RunCompanyEntity runCompanyEntity = new RunCompanyEntity();
        runCompanyEntity.setMakerId(makerId);
        runCompanyEntity.setCompanyName(runCompanyDto.getCompanyName());
        runCompanyEntity.setTaxNo(runCompanyDto.getTaxNo());
        runCompanyEntity.setPhoneNo(runCompanyDto.getPhoneNo());
        runCompanyEntity.setEmployeeName(runCompanyDto.getEmployeeName());
        runCompanyEntity.setBankName(runCompanyDto.getBankName());
        runCompanyEntity.setBankAccount(runCompanyDto.getBankAccount());
        runCompanyEntity.setCreateTime(new Date());
        runCompanyEntity.setUpdateTime(new Date());
        runCompanyEntity.setStatus(1);
        runCompanyEntity.setIsDeleted(0);
        runCompanyEntity.setCreateUser(makerId);
        runCompanyEntity.setUpdateUser(makerId);
        save(runCompanyEntity);
        return R.success("成功");
    }
}
