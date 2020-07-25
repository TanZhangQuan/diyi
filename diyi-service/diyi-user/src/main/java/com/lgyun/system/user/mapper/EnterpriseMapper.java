package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Mapper
public interface EnterpriseMapper extends BaseMapper<EnterpriseEntity> {

    /**
     * 通过商户id查询
     */
    MakerEnterpriseRelationVO getEnterpriseId(Long enterpriseId);

}

