package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
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
 * Service 实现
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
     * @return
     */
    @Override
    public R findMakerId(Integer current, Integer size, Long objectId,ObjectType objectType) {
        QueryWrapper<RunCompanyEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RunCompanyEntity::getObjectId, objectId)
                .eq(RunCompanyEntity::getObjectType, objectType)
                .orderByDesc(RunCompanyEntity::getCreateTime);

        IPage<RunCompanyEntity> page = this.page(new Page<>(current, size), queryWrapper);
        return R.data(page);
    }

    @Override
    public R<String> runCompanySave(RunCompanyDto runCompanyDto) {
        QueryWrapper<RunCompanyEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RunCompanyEntity::getCompanyName, runCompanyDto.getCompanyName())
                .eq(RunCompanyEntity::getObjectId, runCompanyDto.getObjectId())
                .eq(RunCompanyEntity::getObjectType, runCompanyDto.getObjectType());

        RunCompanyEntity runCompanyEntity1 = baseMapper.selectOne(queryWrapper);
        if (null != runCompanyEntity1) {
            return R.fail("名字不能重复");
        }

        queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RunCompanyEntity::getTaxNo, runCompanyDto.getTaxNo())
                .eq(RunCompanyEntity::getObjectId, runCompanyDto.getObjectId())
                .eq(RunCompanyEntity::getObjectType, runCompanyDto.getObjectType());

        RunCompanyEntity runCompanyEntity2 = baseMapper.selectOne(queryWrapper);
        if (null != runCompanyEntity2) {
            return R.fail("纳税号码不能重复");
        }

        queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RunCompanyEntity::getBankAccount, runCompanyDto.getBankAccount())
                .eq(RunCompanyEntity::getObjectId, runCompanyDto.getObjectId())
                .eq(RunCompanyEntity::getObjectType, runCompanyDto.getObjectType());

        RunCompanyEntity runCompanyEntity3 = baseMapper.selectOne(queryWrapper);
        if (null != runCompanyEntity3) {
            return R.fail("银行卡号码不能重复");
        }

        RunCompanyEntity runCompanyEntity = new RunCompanyEntity();
        BeanUtils.copyProperties(runCompanyDto, runCompanyEntity);
        runCompanyEntity.setObjectId(runCompanyDto.getObjectId());

        save(runCompanyEntity);
        return R.success("成功");
    }
}
