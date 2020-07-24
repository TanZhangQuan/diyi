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
     * 自定义分页
     */
    List<MakerEnterpriseRelationVO> selectMakerEnterprisePage(Long makerId, Integer relationshipType, IPage<MakerEnterpriseRelationVO> page);

}

