package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.RelEnterpriseMakerVO;
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
     * 查询合作商户
     *
     * @param makerId
     * @param relationshipType
     * @param page
     * @return
     */
    List<MakerEnterpriseRelationVO> selectMakerEnterprisePage(Long makerId, Integer relationshipType, IPage<MakerEnterpriseRelationVO> page);

    /**
     * 获取关注当前商户的所有创客
     *
     * @param enterpriseId
     * @param page
     * @return
     */
    List<RelEnterpriseMakerVO> getRelEnterpriseMaker(Long enterpriseId, IPage<RelEnterpriseMakerVO> page);

}

