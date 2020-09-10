package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDto;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.vo.IndividualBusinessEnterpriseDetailsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@Mapper
public interface IndividualBusinessMapper extends BaseMapper<IndividualBusinessEntity> {

    IndividualBusinessEnterpriseDetailsVO findById(Long individualBusinessId);

    List<IndividualBusinessEnterpriseDetailsVO> getIndividualBusinessList(Long enterpriseId, Long serviceProviderId, IndividualBusinessEnterpriseDto individualBusinessEnterpriseDto, IPage<IndividualBusinessEnterpriseDetailsVO> page);

}

