package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.RunCompanyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Mapper
public interface RunCompanyMapper extends BaseMapper<RunCompanyEntity> {
    /**
     *根据名字查询
     */
    List<RunCompanyEntity> findCompanyName(Long makerId, IPage<RunCompanyEntity> page);

    /**
     * 根据名字查询是否有重复
     */
    RunCompanyEntity repeatCompanyName(String companyName,Long makerId);

    /**
     * 根据纳税号码查询是否有重复
     */
    RunCompanyEntity repeatTaxNo(String taxNo,Long makerId);


    /**
     * 根据银行卡查询是否有重复
     */
    RunCompanyEntity repeatBankAccount(String bankAccount,Long makerId);
}

