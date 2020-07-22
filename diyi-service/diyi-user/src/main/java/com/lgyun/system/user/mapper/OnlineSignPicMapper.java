package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.entity.OnlineSignPicEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 *
 * @author jun
 * @since 2020-07-18 15:59:14
 */
@Mapper
public interface OnlineSignPicMapper extends BaseMapper<OnlineSignPicEntity> {

    /**
     * 通创客id查询
     */
    OnlineSignPicEntity getMakerId(Long makerId);
}

