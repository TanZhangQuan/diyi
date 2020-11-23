package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.PartnerEnterpriseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.CooperationEnterprisesListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 合伙人-商户关联表 Mapper
 *
 * @author tzq
 * @since 2020-10-20 18:25:03
 */
@Mapper
public interface PartnerEnterpriseMapper extends BaseMapper<PartnerEnterpriseEntity> {

    /**
     * 查询合伙人合作商户
     *
     * @param partnerId
     * @param enterpriseName
     * @param page
     * @return
     */
    List<CooperationEnterprisesListVO> queryCooperationEnterpriseList(Long partnerId, String enterpriseName, IPage<CooperationEnterprisesListVO> page);
}

