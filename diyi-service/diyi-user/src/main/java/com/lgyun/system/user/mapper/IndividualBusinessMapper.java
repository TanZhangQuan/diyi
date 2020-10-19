package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDTO;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.vo.enterprise.IndividualBusinessDetailEnterpriseVO;
import com.lgyun.system.user.vo.maker.IndividualBusinessDetailMakerVO;
import com.lgyun.system.user.vo.maker.IndividualBusinessListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author tzq
 * @since 2020-07-02 17:44:02
 */
@Mapper
public interface IndividualBusinessMapper extends BaseMapper<IndividualBusinessEntity> {

    List<IndividualBusinessListVO> listByMaker(Long makerId, Ibstate ibstate, IPage<IndividualBusinessListVO> page);

    IndividualBusinessDetailMakerVO findById(Long individualBusinessId);

    List<IndividualBusinessDetailEnterpriseVO> getIndividualBusinessList(Long enterpriseId, Long serviceProviderId, IndividualBusinessEnterpriseDTO individualBusinessEnterpriseDto, IPage<IndividualBusinessDetailEnterpriseVO> page);

}

