package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDTO;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.vo.IndividualBusinessEnterpriseDetailsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author tzq
 * @since 2020-07-02 17:44:02
 */
@Mapper
public interface IndividualEnterpriseMapper extends BaseMapper<IndividualEnterpriseEntity> {

    IndividualBusinessEnterpriseDetailsVO findById(Long individualEnterpriseId);

    List<IndividualBusinessEnterpriseDetailsVO> getIndividualEnterpriseList(Long enterpriseId, Long serviceProviderId, IndividualBusinessEnterpriseDTO individualBusinessEnterpriseDto, IPage<IndividualBusinessEnterpriseDetailsVO> page);

}

