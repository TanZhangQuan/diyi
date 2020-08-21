package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.vo.EnterprisesIdNameListVO;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.RelMakerListVO;
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
    List<MakerEnterpriseRelationVO> selectMakerEnterprisePage(Long makerId, RelationshipType relationshipType, IPage<MakerEnterpriseRelationVO> page);

    /**
     * 根据商户ID，关系，关键字获取当前商户的所有创客
     *
     * @param enterpriseId
     * @param relationshipType
     * @param keyword
     * @param page
     * @return
     */
    List<RelMakerListVO> getEnterpriseMakers(Long enterpriseId, RelationshipType relationshipType, String keyword, IPage<RelMakerListVO> page);

    /**
     * 根据创客ID查询关联商户
     *
     * @param makerId
     * @param page
     * @return
     */
    List<EnterprisesIdNameListVO> findEnterpriseIdNameByMakerId(Long makerId, IPage<EnterprisesIdNameListVO> page);
}

