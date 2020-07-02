package com.lgyun.system.user.mapper;

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
    List<RunCompanyEntity> findCompanyName(String companyName);
}

