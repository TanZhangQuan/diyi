package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDTO;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.vo.enterprise.IndividualEnterpriseDetailEnterpriseVO;
import com.lgyun.system.user.vo.maker.IndividualEnterpriseDetailMakerVO;
import com.lgyun.system.user.vo.maker.IndividualEnterpriseListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author tzq
 * @since 2020-07-02 17:44:02
 */
@Mapper
public interface IndividualEnterpriseMapper extends BaseMapper<IndividualEnterpriseEntity> {

    List<IndividualEnterpriseListVO> listByMaker(Long makerId, Ibstate ibstate, IPage<IndividualEnterpriseListVO> page);

    IndividualEnterpriseDetailMakerVO findById(Long individualEnterpriseId);

    List<IndividualEnterpriseDetailEnterpriseVO> getIndividualEnterpriseList(Long enterpriseId, Long serviceProviderId, IndividualBusinessEnterpriseDTO individualBusinessEnterpriseDto, IPage<IndividualEnterpriseDetailEnterpriseVO> page);
}

