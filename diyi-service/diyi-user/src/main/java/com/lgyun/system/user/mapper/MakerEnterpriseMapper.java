package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Mapper
public interface MakerEnterpriseMapper extends BaseMapper<MakerEnterpriseEntity> {
    /**
     * 通过创客id查询
     */
    List<MakerEnterpriseEntity> getMakerId(Long makerId);

    /**
     * 自定义分页
     */
    List<MakerEnterpriseRelationVO> selectMakerEnterprisePage(Long makerId, Integer relationshipType, IPage<MakerEnterpriseRelationVO> page);

    /**
     * 通过
     */
    MakerEnterpriseEntity selectCancelfollow(Long enterpriseId, Long makerId);

    /**
     * 通过商户id和创客id查询
     */
    MakerEnterpriseEntity getEnterpriseIdAndMakerId(Long enterpriseId,Long makerId,Integer difference);
}

