package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.EnterpriseIndividualBusinessDto;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.vo.IndividualBusinessDetailVO;
import com.lgyun.system.user.vo.EnterpriseIndividualBusinessVO;
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

    IndividualBusinessDetailVO findById(Long individualBusinessId);

    List<EnterpriseIndividualBusinessVO> getByDtoEnterprise(Long enterpriseId, EnterpriseIndividualBusinessDto enterpriseIndividualBusinessDto, IPage<EnterpriseIndividualBusinessVO> page);

    EnterpriseIndividualBusinessVO findByIdEnterprise(Long individualBusinessId);
}

