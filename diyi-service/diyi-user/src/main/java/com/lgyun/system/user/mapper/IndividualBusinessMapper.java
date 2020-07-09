package com.lgyun.system.user.mapper;

import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@Mapper
public interface IndividualBusinessMapper extends BaseMapper<IndividualBusinessEntity> {

    /**
     * 通过创客id查询
     */
    //通过创客id查询个体户
    IndividualBusinessEntity findMakerId(Long makerId);
}

